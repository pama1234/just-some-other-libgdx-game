package org.eclipse.tm4e.core.internal.rule;

import java.util.List;

import org.eclipse.tm4e.core.internal.oniguruma.OnigScanner;

public final class CompiledRule{
  public final List<String> debugRegExps;
  public final OnigScanner scanner;
  public final RuleId[] rules;
  CompiledRule(final List<String> regExps,final RuleId[] rules) {
    this.debugRegExps=regExps;
    this.rules=rules;
    this.scanner=new OnigScanner(regExps);
  }
}
