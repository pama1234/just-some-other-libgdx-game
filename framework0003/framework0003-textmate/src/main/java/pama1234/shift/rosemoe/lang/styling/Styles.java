package pama1234.shift.rosemoe.lang.styling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import pama1234.shift.misc.NonNull;
import pama1234.shift.rosemoe.data.ObjectAllocator;
import pama1234.shift.rosemoe.lang.styling.line.LineAnchorStyle;
import pama1234.shift.rosemoe.lang.styling.line.LineStyles;
import pama1234.shift.rosemoe.text.CharPosition;
import pama1234.shift.rosemoe.util.MutableInt;

@SuppressWarnings("unused")
public class Styles{
  public Spans spans;
  public List<LineStyles> lineStyles;
  public Map<Class<?>,MutableInt> styleTypeCount;
  public List<CodeBlock> blocks;
  public int suppressSwitch=Integer.MAX_VALUE;
  public boolean indentCountMode=false;
  public Styles() {
    this(null);
  }
  public Styles(@Nullable Spans spans) {
    this(spans,true);
  }
  public Styles(@Nullable Spans spans,boolean initCodeBlocks) {
    this.spans=spans;
    if(initCodeBlocks) {
      blocks=new ArrayList<>(128);
    }
  }
  @Nullable
  public Spans getSpans() {
    return spans;
  }
  @NonNull
  public CodeBlock obtainNewBlock() {
    return ObjectAllocator.obtainBlockLine();
  }
  public void addCodeBlock(@NonNull CodeBlock block) {
    blocks.add(block);
  }
  public int getSuppressSwitch() {
    return suppressSwitch;
  }
  public void setSuppressSwitch(int suppressSwitch) {
    this.suppressSwitch=suppressSwitch;
  }
  public void adjustOnInsert(@NonNull CharPosition start,@NonNull CharPosition end) {
    spans.adjustOnInsert(start,end);
    var delta=end.line-start.line;
    if(delta==0) {
      return;
    }
    if(blocks!=null) BlocksUpdater.update(blocks,start.line,delta);
    if(lineStyles!=null) {
      for(var styles:lineStyles) {
        if(styles.getLine()>start.line) {
          styles.setLine(styles.getLine()+delta);
          styles.updateElements();
        }
      }
    }
  }
  public void adjustOnDelete(@NonNull CharPosition start,@NonNull CharPosition end) {
    spans.adjustOnDelete(start,end);
    var delta=start.line-end.line;
    if(delta==0) {
      return;
    }
    if(blocks!=null) BlocksUpdater.update(blocks,start.line,delta);
    if(lineStyles!=null) {
      var itr=lineStyles.iterator();
      while(itr.hasNext()) {
        var styles=itr.next();
        var line=styles.getLine();
        if(line>end.line) {
          styles.setLine(line+delta);
          styles.updateElements();
        }else if(line>start.line) {
          itr.remove();
        }
      }
    }
  }
  public void addLineStyle(@NonNull LineAnchorStyle style) {
    if(lineStyles==null) {
      lineStyles=new ArrayList<>();
      styleTypeCount=new ConcurrentHashMap<>();
    }
    var type=style.getClass();
    for(var lineStyle:lineStyles) {
      if(lineStyle.getLine()==style.getLine()) {
        styleCountUpdate(type,lineStyle.addStyle(style));
        return;
      }
    }
    var lineStyle=new LineStyles(style.getLine());
    lineStyles.add(lineStyle);
    styleCountUpdate(type,lineStyle.addStyle(style));
  }
  private void styleCountUpdate(@NonNull Class<?> type,int delta) {
    var res=styleTypeCount.get(type);
    if(res==null) {
      res=new MutableInt(0);
      styleTypeCount.put(type,res);
    }
    res.value+=delta;
  }
  public void eraseLineStyle(int line,@NonNull Class<? extends LineAnchorStyle> type) {
    if(lineStyles==null) {
      return;
    }
    for(var lineStyle:lineStyles) {
      if(lineStyle.getLine()==line) {
        styleCountUpdate(type,-lineStyle.eraseStyle(type));
        break;
      }
    }
  }
  public void eraseAllLineStyles() {
    if(lineStyles==null) {
      return;
    }
    lineStyles.clear();
    styleTypeCount.clear();
  }
  public void setIndentCountMode(boolean indentCountMode) {
    this.indentCountMode=indentCountMode;
  }
  public boolean isIndentCountMode() {
    return indentCountMode;
  }
  public void finishBuilding() {
    if(blocks!=null) {
      int pre=-1;
      var sort=false;
      for(int i=0;i<blocks.size()-1;i++) {
        var cur=blocks.get(i+1).endLine;
        if(pre>cur) {
          sort=true;
          break;
        }
        pre=cur;
      }
      if(sort) {
        Collections.sort(blocks,CodeBlock.COMPARATOR_END);
      }
    }
    if(lineStyles!=null) {
      Collections.sort(lineStyles);
    }
  }
}
