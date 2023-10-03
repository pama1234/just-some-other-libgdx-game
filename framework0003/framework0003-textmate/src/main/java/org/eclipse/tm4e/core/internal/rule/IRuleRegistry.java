package org.eclipse.tm4e.core.internal.rule;

import java.util.function.Function;

public interface IRuleRegistry{
  Rule getRule(RuleId ruleId);
  <T extends Rule> T registerRule(Function<RuleId,T> factory);
}
