package pama1234.shift.rosemoe.lang.completion.snippet;

import pama1234.shift.misc.NonNull;

public abstract class SnippetItem implements Cloneable{
  private int start;
  private int end;
  public SnippetItem() {
    this(0);
  }
  public SnippetItem(int index) {
    this(index,index);
  }
  public SnippetItem(int start,int end) {
    setIndex(start,end);
  }
  public int getStartIndex() {
    return start;
  }
  public int getEndIndex() {
    return end;
  }
  public void setIndex(int index) {
    setIndex(index,index);
  }
  public void setIndex(int start,int end) {
    this.start=start;
    this.end=end;
  }
  public void shiftIndex(int deltaIndex) {
    start+=deltaIndex;
    end+=deltaIndex;
  }
  @NonNull
  public abstract SnippetItem clone();
}
