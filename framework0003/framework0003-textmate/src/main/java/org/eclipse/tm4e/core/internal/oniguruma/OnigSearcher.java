package org.eclipse.tm4e.core.internal.oniguruma;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.Nullable;

public final class OnigSearcher{
  private final List<OnigRegExp> regExps;
  public OnigSearcher(final List<String> regExps) {
    this.regExps=regExps.stream().map(OnigRegExp::new).collect(Collectors.toList());
  }
  @Nullable
  public OnigResult search(final OnigString source,final int charOffset) {
    final int byteOffset=source.getByteIndexOfChar(charOffset);
    int bestLocation=0;
    OnigResult bestResult=null;
    int index=0;
    for(final OnigRegExp regExp:regExps) {
      final OnigResult result=regExp.search(source,byteOffset);
      if(result!=null&&result.count()>0) {
        final int location=result.locationAt(0);
        if(bestResult==null||location<bestLocation) {
          bestLocation=location;
          bestResult=result;
          bestResult.setIndex(index);
        }
        if(location==byteOffset) {
          break;
        }
      }
      index++;
    }
    return bestResult;
  }
}
