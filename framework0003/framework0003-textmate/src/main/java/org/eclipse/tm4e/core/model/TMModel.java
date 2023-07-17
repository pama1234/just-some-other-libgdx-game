package org.eclipse.tm4e.core.model;

import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.castNullable;
import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.lazyNonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.grammar.IStateStack;
import org.eclipse.tm4e.core.internal.grammar.StateStack;
import org.eclipse.tm4e.core.internal.utils.MoreCollections;
import org.eclipse.tm4e.core.internal.utils.StringUtils;

public abstract class TMModel implements ITMModel{
  private static final class Edit{
    final int lineIndex;
    final int replacedCount;
    final int replacementCount;
    public Edit(final int lineIndex,final int replacedCount,final int replacementCount) {
      this.lineIndex=lineIndex;
      this.replacedCount=replacedCount;
      this.replacementCount=replacementCount;
    }
    @Override
    public String toString() {
      return "{lineNumber="+(lineIndex+1)+", replacedCount="+replacedCount+", replacementCount="+replacementCount+'}';
    }
  }
  static final class LineTokens{
    volatile IStateStack startState=StateStack.NULL;
    @Nullable
    IStateStack endState;
    @Nullable
    volatile List<TMToken> tokens;
    void reset() {
      startState=StateStack.NULL;
      endState=null;
      tokens=null;
    }
    @Override
    public String toString() {
      return "{startState="+startState+", tokens="+tokens+'}';
    }
  }
  // private static final Logger LOGGER=System.getLogger(TMModel.class.getName());
  private @Nullable IGrammar grammar;
  private final ModelTokensChangedEvent.Listeners listeners=new ModelTokensChangedEvent.Listeners();
  private @Nullable volatile TokenizerThread tokenizerThread;
  private volatile boolean tokenizerThreadHasWork;
  private TMTokenizationSupport tokenizer=lazyNonNull();
  final ArrayList<LineTokens> lines;
  final Object linesWriteLock;
  private final BlockingQueue<Edit> edits=new LinkedBlockingQueue<>();
  protected TMModel(final int initialNumberOfLines) {
    lines=new ArrayList<>(Math.max(10,initialNumberOfLines));
    linesWriteLock=lines;
    onLinesReplaced(0,0,initialNumberOfLines);
  }
  // private static final boolean DEBUG_LOGGING=LOGGER.isLoggable(DEBUG);
  private static final boolean DEBUG_LOGGING=false;
  private void logDebug(final String msg,final Object... args) {
    if(!DEBUG_LOGGING) return;
    final var t=Thread.currentThread();
    final var caller=t.getStackTrace()[2];
    final var threadName=t.getName().endsWith(TokenizerThread.class.getSimpleName())?"tknz":t.getName();
    System.out.println("["+threadName+"] "+caller.getMethodName()+String.format(msg,args));
  }
  private final class TokenizerThread extends Thread{
    private static final Duration MAX_TIME_PER_LINE_TOKENIZATION=Duration.ofSeconds(1);
    private static final int MAX_TIME_PER_MULTI_LINE_VALIDATIONS=200;
    TokenizerThread() {
      super("tm4e."+TokenizerThread.class.getSimpleName());
      setPriority(Thread.MIN_PRIORITY);
      setDaemon(true);
    }
    @Override
    public void run() {
      try {
        // loop as long as the thread is active
        while(tokenizerThread==this) {
          tokenizerThreadHasWork=!(isAllTokensAreValid()&&edits.isEmpty());
          if(!tokenizerThreadHasWork||!edits.isEmpty()) {
            // wait for the first edit
            applyEdit(edits.take());
            // poll all subsequent edits
            for(;;) {
              // wait up to 50ms for the next edit, so that edits made in fast succession (e.g. by a formater) are applied
              // in one go before the token revalidation loop happens
              final var edit=castNullable(edits.poll(50,TimeUnit.MILLISECONDS));
              if(edit==null) break;
              applyEdit(edit);
            }
          }
          revalidateTokens();
        }
      }catch(final InterruptedException ex) {
        interrupt();
      }finally {
        tokenizerThreadHasWork=false;
      }
    }
    private int firstLineToRevalidate=-1;
    private boolean isAllTokensAreValid() {
      return firstLineToRevalidate==-1;
    }
    private void setAllTokensAreValid() {
      firstLineToRevalidate=-1;
    }
    private void revalidateTokens() throws InterruptedException {
      final int startLineIndex=firstLineToRevalidate;
      final int startLineNumber=startLineIndex+1;
      if(DEBUG_LOGGING) logDebug("(%d)",startLineNumber);
      long startTime=System.currentTimeMillis();
      var changedRanges=new ArrayList<Range>();
      Range prevRange=null;
      var prevLineTokens=getLineTokensOrNull(startLineIndex-1);
      final int linesCount=lines.size();
      int currLineIndex=-1;
      // iterate over all lines from startLineIndex to end of file to check if (re)tokenization is required
      for(currLineIndex=startLineIndex;currLineIndex<linesCount;currLineIndex++) {
        // check if TokenizerThread is still running
        if(isInterrupted()) {
          break;
        }
        // check if new edits are queued -> if so, abort current tokenization loop
        if(!edits.isEmpty()) {
          break;
        }
        final var currLineTokens=lines.get(currLineIndex);
        if(currLineIndex==0) {
          currLineTokens.startState=tokenizer.getInitialState();
        }
        final int currLineNumber=currLineIndex+1;
        // check if (re)tokenization is required
        if(prevLineTokens!=null) {
          if(currLineTokens.tokens!=null&&currLineTokens.startState.equals(prevLineTokens.endState)) {
            // has matching start and has tokens ==> is up to date
            if(DEBUG_LOGGING) logDebug("(%d) >> DONE - tokens of line %d are up-to-date",startLineNumber,currLineNumber);
            firstLineToRevalidate=currLineIndex+1;
            prevLineTokens=currLineTokens;
            continue;
          }
          if(prevLineTokens.endState!=null) currLineTokens.startState=prevLineTokens.endState;
        }
        // (re)tokenize the line
        if(DEBUG_LOGGING) logDebug("(%d) >> tokenizing line %d...",startLineNumber,currLineNumber);
        TokenizationResult r;
        try {
          final String lineText=getLineText(currLineIndex);
          r=tokenizer.tokenize(lineText,currLineTokens.startState,0,MAX_TIME_PER_LINE_TOKENIZATION);
        }catch(final Exception ex) {
          // System.err.println(ex.toString());
          ex.printStackTrace();
          r=new TokenizationResult(new ArrayList<>(1),0,currLineTokens.startState,true);
        }
        // check if complete line was tokenized
        if(r.stoppedEarly) {
          // treat the rest of the line as one default token
          r.tokens.add(new TMToken(r.actualStopOffset,""));
          // Use the line's starting state as end state in case of incomplete tokenization
          r.endState=currLineTokens.startState;
        }
        currLineTokens.endState=r.endState;
        currLineTokens.tokens=r.tokens;
        prevLineTokens=currLineTokens;
        firstLineToRevalidate=currLineIndex+1;
        // add the line number to the changed ranges
        if(prevRange!=null&&prevRange.toLineNumber==currLineNumber-1) {
          prevRange.toLineNumber=currLineNumber; // extend range from previous line change
        }else {
          prevRange=new Range(currLineNumber);
          changedRanges.add(prevRange); // insert new range
        }
        // if MAX_TIME_PER_MULTI_LINE_VALIDATIONS reached, notify listeners about line changes
        if(System.currentTimeMillis()-startTime>=MAX_TIME_PER_MULTI_LINE_VALIDATIONS) {
          if(DEBUG_LOGGING) logDebug("(%d) >> changedRanges: %s",startLineNumber,changedRanges);
          listeners.dispatchEvent(changedRanges,TMModel.this);
          changedRanges=new ArrayList<>();
          prevRange=null;
          startTime=System.currentTimeMillis();
        }
      }
      // notify listeners about remaining line changes
      if(DEBUG_LOGGING) logDebug("(%d) >> changedRanges: %s",startLineNumber,changedRanges);
      listeners.dispatchEvent(changedRanges,TMModel.this);
      setAllTokensAreValid();
    }
    private void applyEdit(final Edit edit) {
      if(DEBUG_LOGGING) logDebug("(%s)",edit);
      final var lineIndex=edit.lineIndex;
      if(isAllTokensAreValid()||lineIndex<firstLineToRevalidate) firstLineToRevalidate=lineIndex;
      // check if single line update
      if(edit.replacedCount==1&&edit.replacementCount==1) {
        final var firstLineOfEdit=getLineTokensOrNull(lineIndex);
        if(firstLineOfEdit==null) return;
        // reuse the LineToken instance by resetting it's state
        firstLineOfEdit.reset();
        return;
      }
      final int replacedCount=Math.min(edit.replacedCount,lines.size()-lineIndex);
      final var lineDiff=edit.replacementCount-edit.replacedCount;
      final var editRange=lines.subList(lineIndex,lineIndex+replacedCount);
      // (1) number of lines not changed by edit
      if(lineDiff==0) {
        // reset tokenization state of affected lines
        editRange.forEach(LineTokens::reset);
        return;
      }
      // (2) new lines added by edit
      if(lineDiff>0) {
        // reset tokenization state of affected lines
        editRange.forEach(LineTokens::reset);
        // add extra lines
        final var additionalLines=new ArrayList<LineTokens>(lineDiff);
        for(int i=0;i<lineDiff;i++) {
          additionalLines.add(new LineTokens());
        }
        synchronized(linesWriteLock) {
          editRange.addAll(additionalLines);
        }
        return;
      }
      // (3) lines removed by edit
      {
        synchronized(linesWriteLock) {
          editRange.subList(0,-lineDiff).clear();
        }
        // reset tokenization state of the other affected lines
        editRange.forEach(LineTokens::reset);
      }
    }
  }
  private @Nullable LineTokens getLineTokensOrNull(final int index) {
    return index>-1&&index<lines.size()
      ?lines.get(index)
      :null;
  }
  @Override
  public BackgroundTokenizationState getBackgroundTokenizationState() {
    return tokenizerThreadHasWork?BackgroundTokenizationState.IN_PROGRESS:BackgroundTokenizationState.COMPLETED;
  }
  @Override
  public @Nullable IGrammar getGrammar() {
    return grammar;
  }
  @Override
  public synchronized void setGrammar(final IGrammar grammar) {
    if(!Objects.equals(grammar,this.grammar)) {
      this.grammar=grammar;
      final var tokenizer=this.tokenizer=new TMTokenizationSupport(grammar);
      synchronized(linesWriteLock) {
        if(!lines.isEmpty()) {
          lines.get(0).startState=tokenizer.getInitialState();
        }
        onLinesReplaced(0,1,1);
      }
      startTokenizerThread();
    }
  }
  public void onLinesReplaced(final int lineIndex,final int replacedLinesCount,final int replacementLinesCount) {
    if(replacedLinesCount==0&&replacementLinesCount==0) return;
    if(DEBUG_LOGGING) logDebug("(%d, -%d, +%d)",lineIndex+1,replacedLinesCount,replacementLinesCount);
    edits.add(new Edit(lineIndex,replacedLinesCount,replacementLinesCount));
  }
  @Override
  public synchronized boolean addModelTokensChangedListener(final ModelTokensChangedEvent.Listener listener) {
    if(listeners.add(listener)) {
      startTokenizerThread();
      return true;
    }
    return false;
  }
  @Override
  public synchronized boolean removeModelTokensChangedListener(final ModelTokensChangedEvent.Listener listener) {
    if(listeners.remove(listener)) {
      if(listeners.isEmpty()) {
        stopTokenizerThread(); // no need to keep tokenizing if no-one cares
      }
      return true;
    }
    return false;
  }
  @Override
  public void dispose() {
    stopTokenizerThread();
  }
  private synchronized void startTokenizerThread() {
    if(grammar!=null&&listeners.isNotEmpty()) {
      var thread=this.tokenizerThread;
      if(thread==null||!thread.isAlive()||thread.isInterrupted()) {
        thread=this.tokenizerThread=new TokenizerThread();
        thread.start();
      }
    }
  }
  private synchronized void stopTokenizerThread() {
    final var thread=this.tokenizerThread;
    if(thread==null) return;
    thread.interrupt();
    this.tokenizerThread=null;
  }
  @Override
  public int getNumberOfLines() {
    synchronized(linesWriteLock) {
      return lines.size();
    }
  }
  @Override
  public @Nullable List<TMToken> getLineTokens(final int lineIndex) {
    synchronized(linesWriteLock) {
      final var lineTokens=getLineTokensOrNull(lineIndex);
      return lineTokens==null?null:lineTokens.tokens;
    }
  }
  @Override
  public String toString() {
    return StringUtils.toString(this,sb-> {
      sb.append("grammar=").append(grammar);
      synchronized(linesWriteLock) {
        sb.append(", lines=").append(MoreCollections.toStringWithIndex(lines));
      }
    });
  }
}
