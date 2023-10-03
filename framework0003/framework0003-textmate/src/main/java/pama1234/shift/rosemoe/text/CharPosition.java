package pama1234.shift.rosemoe.text;

import java.util.Objects;

import pama1234.shift.misc.NonNull;
import pama1234.shift.rosemoe.util.IntPair;

public final class CharPosition{
  public int index;
  public int line;
  public int column;
  public CharPosition() {}
  public CharPosition(int line,int column) {
    this(line,column,-1);
  }
  public CharPosition(int line,int column,int index) {
    this.index=index;
    this.line=line;
    this.column=column;
  }
  public int getIndex() {
    return index;
  }
  public int getColumn() {
    return column;
  }
  public int getLine() {
    return line;
  }
  public CharPosition toBOF() {
    index=line=column=0;
    return this;
  }
  @Override
  public boolean equals(Object another) {
    if(another instanceof CharPosition) {
      CharPosition a=(CharPosition)another;
      return a.column==column&&
        a.line==line&&
        a.index==index;
    }
    return false;
  }
  @Override
  public int hashCode() {
    return Objects.hash(index,line,column);
  }
  public long toIntPair() {
    return IntPair.pack(line,column);
  }
  @NonNull
  public CharPosition fromThis() {
    var pos=new CharPosition();
    pos.set(this);
    return pos;
  }
  public void set(@NonNull CharPosition another) {
    index=another.index;
    line=another.line;
    column=another.column;
  }
  @NonNull
  @Override
  public String toString() {
    return "CharPosition(line = "+line+",column = "+column+",index = "+index+")";
  }
}
