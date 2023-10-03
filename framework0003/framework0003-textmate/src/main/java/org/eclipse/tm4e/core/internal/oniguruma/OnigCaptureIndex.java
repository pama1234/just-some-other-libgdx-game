package org.eclipse.tm4e.core.internal.oniguruma;

import org.eclipse.jdt.annotation.Nullable;

public final class OnigCaptureIndex{
  static final OnigCaptureIndex EMPTY=new OnigCaptureIndex(0,0);
  public int index;
  public final int start;
  public final int end;
  public OnigCaptureIndex(int index,final int start,final int end) {
    this.index=index;
    this.start=start>=0?start:0;
    this.end=end>=0?end:0;
  }
  public OnigCaptureIndex(final int start,final int end) {
    this.start=start>=0?start:0;
    this.end=end>=0?end:0;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final OnigCaptureIndex other) return end==other.end
      &&start==other.start;
    return false;
  }
  public int getLength() {
    return end-start;
  }
  @Override
  public int hashCode() {
    return 31*(31+end)+start;
  }
  @Override
  public String toString() {
    return "{"+", \"start\": "+start+", \"end\": "+end+", \"length\": "+getLength()+"}";
  }
}
