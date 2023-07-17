package org.eclipse.tm4e.languageconfiguration.internal.model;

import java.util.List;

public final class AutoClosingPairConditional extends AutoClosingPair{
  public final List<String> notIn;
  public AutoClosingPairConditional(final String open,final String close,final List<String> notIn) {
    super(open,close);
    this.notIn=notIn;
  }
}
