package pama1234.shift.rosemoe.lang.completion.snippet;

import pama1234.shift.misc.NonNull;

public class PlainTextItem extends SnippetItem{
  private String text;
  public PlainTextItem(@NonNull String text,int index) {
    this(text,index,index+text.length());
  }
  public PlainTextItem(@NonNull String text,int start,int end) {
    setIndex(start,end);
    this.text=text;
  }
  @NonNull
  public String getText() {
    return text;
  }
  public void setText(@NonNull String text) {
    this.text=text;
  }
  @NonNull
  @Override
  public PlainTextItem clone() {
    return new PlainTextItem(text,getStartIndex(),getEndIndex());
  }
}
