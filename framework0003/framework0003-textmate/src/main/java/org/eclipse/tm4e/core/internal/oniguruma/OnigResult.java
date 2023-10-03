package org.eclipse.tm4e.core.internal.oniguruma;

import org.joni.Region;

final class OnigResult{
  private int indexInScanner;
  private final Region region;
  OnigResult(final Region region,final int indexInScanner) {
    this.region=region;
    this.indexInScanner=indexInScanner;
  }
  int getIndex() {
    return indexInScanner;
  }
  void setIndex(final int index) {
    indexInScanner=index;
  }
  int locationAt(final int index) {
    final int bytes=region.getBeg(index);
    if(bytes>0) {
      return bytes;
    }
    return 0;
  }
  int count() {
    return region.getNumRegs();
  }
  int lengthAt(final int index) {
    final int bytes=region.getEnd(index)-region.getBeg(index);
    if(bytes>0) {
      return bytes;
    }
    return 0;
  }
}
