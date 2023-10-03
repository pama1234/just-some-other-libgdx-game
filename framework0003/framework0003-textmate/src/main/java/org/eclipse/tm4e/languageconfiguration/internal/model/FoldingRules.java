package org.eclipse.tm4e.languageconfiguration.internal.model;

import java.util.regex.Pattern;

public final class FoldingRules{
  public final boolean offSide;
  public final Pattern markersStart;
  public final Pattern markersEnd;
  public FoldingRules(final boolean offSide,final Pattern markersStart,final Pattern markersEnd) {
    this.offSide=offSide;
    this.markersStart=markersStart;
    this.markersEnd=markersEnd;
  }
}
