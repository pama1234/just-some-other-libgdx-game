package pama1234.shift.rosemoe.text.bi;

import pama1234.shift.misc.NonNull;
import pama1234.shift.rosemoe.util.IntPair;

public class Directions{
  private long[] runs;
  private int length;
  public Directions(@NonNull long[] runs,int length) {
    this.runs=runs;
    this.length=length;
  }
  public void setData(@NonNull long[] runs,int length) {
    this.runs=runs;
    this.length=length;
  }
  public void setLength(int length) {
    this.length=length;
  }
  public int getLength() {
    return length;
  }
  public int getRunCount() {
    return runs.length;
  }
  public int getRunStart(int i) {
    return IntPair.getFirst(runs[i]);
  }
  public int getRunEnd(int i) {
    return i==runs.length-1?length:getRunStart(i+1);
  }
  public boolean isRunRtl(int i) {
    return IntPair.getSecond(runs[i])==1;
  }
}
