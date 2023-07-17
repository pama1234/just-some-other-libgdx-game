package org.eclipse.tm4e.core.internal.oniguruma;

import java.util.Arrays;

import org.eclipse.jdt.annotation.Nullable;

public final class OnigScannerMatch{
  public final int index;
  private final OnigCaptureIndex[] captureIndices;
  OnigScannerMatch(final OnigResult result,final OnigString source) {
    this.index=result.getIndex();
    this.captureIndices=captureIndicesOfMatch(result,source);
  }
  private OnigCaptureIndex[] captureIndicesOfMatch(final OnigResult result,final OnigString source) {
    final int resultCount=result.count();
    final var captures=new OnigCaptureIndex[resultCount];
    for(int i=0;i<resultCount;i++) {
      final int loc=result.locationAt(i);
      final int captureStart=source.getCharIndexOfByte(loc);
      final int captureEnd=source.getCharIndexOfByte(loc+result.lengthAt(i));
      //   captures[i]=captureStart==0&&captureEnd==0
      //     ?OnigCaptureIndex.EMPTY
      //     :new OnigCaptureIndex(captureStart,captureEnd);
      // }
      // return captures;
      captures[i]=new OnigCaptureIndex(i,captureStart,captureEnd);
    }
    return captures;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final OnigScannerMatch other) return index==other.index
      &&Arrays.equals(captureIndices,other.captureIndices);
    return false;
  }
  public OnigCaptureIndex[] getCaptureIndices() {
    return captureIndices;
  }
  @Override
  public int hashCode() {
    return 31*(31+index)+Arrays.hashCode(captureIndices);
  }
  @Override
  public String toString() {
    final var result=new StringBuilder("{\n");
    result.append("  \"index\": ");
    result.append(index);
    result.append(",\n");
    result.append("  \"captureIndices\": [\n");
    int i=0;
    for(final OnigCaptureIndex captureIndex:captureIndices) {
      if(i>0) {
        result.append(",\n");
      }
      result.append("    ");
      result.append(captureIndex);
      i++;
    }
    result.append("\n");
    result.append("  ]\n");
    result.append("}");
    return result.toString();
  }
}
