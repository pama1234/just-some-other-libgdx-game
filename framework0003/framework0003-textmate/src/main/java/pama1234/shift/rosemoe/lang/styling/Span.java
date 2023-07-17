package pama1234.shift.rosemoe.lang.styling;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import pama1234.shift.misc.NonNull;

public class Span{
  private static final BlockingQueue<Span> cacheQueue=new ArrayBlockingQueue<>(8192*2);
  public int column;
  public long style;
  public int underlineColor;
  public Object extra;
  public Span(int column,long style) {
    this.column=column;
    this.style=style;
  }
  public static Span obtain(int column,long style) {
    Span span=cacheQueue.poll();
    if(span==null) {
      return new Span(column,style);
    }else {
      span.column=column;
      span.style=style;
      return span;
    }
  }
  public static void recycleAll(Collection<Span> spans) {
    for(Span span:spans) {
      if(!span.recycle()) {
        return;
      }
    }
  }
  public Span setUnderlineColor(int color) {
    underlineColor=color;
    return this;
  }
  public int getColumn() {
    return column;
  }
  public Span setColumn(int column) {
    this.column=column;
    return this;
  }
  public Span copy() {
    var copy=obtain(column,style);
    copy.setUnderlineColor(underlineColor);
    return copy;
  }
  public boolean recycle() {
    column=underlineColor=0;
    style=0;
    return cacheQueue.offer(this);
  }
  public int getForegroundColorId() {
    return TextStyle.getForegroundColorId(style);
  }
  public int getBackgroundColorId() {
    return TextStyle.getBackgroundColorId(style);
  }
  public long getStyleBits() {
    return TextStyle.getStyleBits(style);
  }
  @Override
  public boolean equals(Object o) {
    if(this==o) return true;
    if(o==null||getClass()!=o.getClass()) return false;
    var span=(Span)o;
    return column==span.column&&style==span.style&&underlineColor==span.underlineColor;
  }
  @Override
  public int hashCode() {
    int hash=31*column;
    hash=31*hash+Long.hashCode(style);
    hash=31*hash+underlineColor;
    return hash;
  }
  @NonNull
  @Override
  public String toString() {
    return "Span{"+
      "column="+
      column+
      ", style="+
      style+
      ", underlineColor="+
      underlineColor+
      "}";
  }
}
