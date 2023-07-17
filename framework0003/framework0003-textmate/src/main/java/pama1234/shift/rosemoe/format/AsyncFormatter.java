package pama1234.shift.rosemoe.format;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import org.eclipse.tm4e.ui.text.Content;

import pama1234.shift.misc.NonNull;
import pama1234.shift.rosemoe.text.TextRange;
import pama1234.shift.rosemoe.util.LoggerUtil;

public abstract class AsyncFormatter implements Formatter{
  private final static String LOG_TAG="AsyncFormatter";
  private static int sThreadId=0;
  private final ReentrantLock lock=new ReentrantLock();
  private final Condition condition=lock.newCondition();
  private WeakReference<FormatResultReceiver> receiver;
  private volatile Content text;
  private volatile TextRange range;
  private volatile TextRange cursorRange;
  private FormattingThread thread;
  private synchronized static int nextThreadId() {
    sThreadId++;
    return sThreadId;
  }
  @Override
  public void setReceiver(FormatResultReceiver receiver) {
    if(receiver==null) {
      this.receiver=null;
      return;
    }
    this.receiver=new WeakReference<>(receiver);
  }
  private void run() {
    if(thread==null||!thread.isAlive()) {
      // Create new thread
      LoggerUtil.vs(LOG_TAG,"Starting a new thread for formatting");
      thread=new FormattingThread();
      thread.setDaemon(true);
      thread.setName("AsyncFormatter-"+nextThreadId());
      thread.start();
    }else {
      // Wake up thread
      LoggerUtil.vs(LOG_TAG,"Waking up thread for formatting");
      lock.lock();
      condition.signal();
      lock.unlock();
    }
  }
  @Override
  public void format(@NonNull Content text,@NonNull TextRange cursorRange) {
    this.text=text;
    range=null;
    this.cursorRange=cursorRange;
    run();
  }
  @Override
  public boolean isRunning() {
    return thread!=null&&thread.isAlive()&&lock.isLocked();
  }
  @Override
  public void formatRegion(@NonNull Content text,@NonNull TextRange rangeToFormat,@NonNull TextRange cursorRange) {
    this.text=text;
    range=rangeToFormat;
    this.cursorRange=cursorRange;
    run();
  }
  // @WorkerThread
  @Nullable
  public abstract TextRange formatAsync(@NonNull Content text,@NonNull TextRange cursorRange);
  // @WorkerThread
  @Nullable
  public abstract TextRange formatRegionAsync(@NonNull Content text,@NonNull TextRange rangeToFormat,@NonNull TextRange cursorRange);
  private void sendUpdate(Content text,TextRange cursorRange) {
    FormatResultReceiver r;
    if(!Thread.currentThread().isInterrupted()&&receiver!=null&&(r=receiver.get())!=null) {
      r.onFormatSucceed(text,cursorRange);
    }
  }
  private void sendFailure(Throwable throwable) {
    FormatResultReceiver r;
    if(!Thread.currentThread().isInterrupted()&&receiver!=null&&(r=receiver.get())!=null) {
      r.onFormatFail(throwable);
    }
  }
  @Override
  public void cancel() {
    if(thread!=null) {
      final var t=thread;
      if(t.isAlive()) {
        t.interrupt();
      }
      thread=null;
    }
  }
  @Override
  public void destroy() {
    if(thread!=null&&thread.isAlive()) {
      thread.interrupt();
    }
    thread=null;
    receiver=null;
    text=null;
    range=null;
  }
  private class FormattingThread extends Thread{
    @Override
    public void run() {
      LoggerUtil.vs(LOG_TAG,"AsyncFormatter thread started");
      try {
        while(!isInterrupted()) {
          lock.lock();
          if(text==null) {
            continue;
          }
          TextRange newRange;
          if(range==null) {
            newRange=formatAsync(text,cursorRange);
          }else {
            newRange=formatRegionAsync(text,range,cursorRange);
          }
          sendUpdate(text,newRange);
          // un-refer immediately
          text=null;
          range=null;
          // Wait for next time
          condition.await();
        }
      }catch(InterruptedException e) {
        LoggerUtil.vs(LOG_TAG,"Thread is interrupted.");
      }catch(Exception e) {
        LoggerUtil.es(LOG_TAG,"Unexpected exception is thrown in the thread.",e);
        sendFailure(e);
      }
    }
  }
}
