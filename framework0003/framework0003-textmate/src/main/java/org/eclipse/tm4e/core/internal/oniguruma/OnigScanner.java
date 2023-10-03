package org.eclipse.tm4e.core.internal.oniguruma;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class OnigScanner{
  private final OnigSearcher searcher;
  public OnigScanner(final List<String> regexps) {
    searcher=new OnigSearcher(regexps);
  }
  @Nullable
  public OnigScannerMatch findNextMatch(final OnigString source,final int startPosition) {
    final OnigResult bestResult=searcher.search(source,startPosition);
    if(bestResult!=null) {
      return new OnigScannerMatch(bestResult,source);
    }
    return null;
  }
}
