package org.eclipse.tm4e.core.internal.rule;

import org.eclipse.jdt.annotation.Nullable;

public final class CaptureRule extends Rule{
  public final RuleId retokenizeCapturedWithRuleId;
  CaptureRule(final RuleId id,@Nullable final String name,@Nullable final String contentName,
    final RuleId retokenizeCapturedWithRuleId) {
    super(id,name,contentName);
    this.retokenizeCapturedWithRuleId=retokenizeCapturedWithRuleId;
  }
  @Override
  public void collectPatterns(final IRuleRegistry grammar,final RegExpSourceList out) {
    throw new UnsupportedOperationException();
  }
  @Override
  public CompiledRule compile(final IRuleRegistry grammar,@Nullable final String endRegexSource) {
    throw new UnsupportedOperationException();
  }
  @Override
  public CompiledRule compileAG(final IRuleRegistry grammar,@Nullable final String endRegexSource,
    final boolean allowA,final boolean allowG) {
    throw new UnsupportedOperationException();
  }
}
