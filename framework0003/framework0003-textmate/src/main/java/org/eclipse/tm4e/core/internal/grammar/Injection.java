package org.eclipse.tm4e.core.internal.grammar;

import java.util.List;

import org.eclipse.tm4e.core.internal.grammar.raw.IRawGrammar;
import org.eclipse.tm4e.core.internal.matcher.Matcher;
import org.eclipse.tm4e.core.internal.rule.RuleId;

final class Injection{
  final String debugSelector;
  private final Matcher<List<String>> matcher;
  final int priority; // -1 | 0 | 1; // 0 is the default. -1 for 'L' and 1 for 'R'
  final RuleId ruleId;
  final IRawGrammar grammar;
  Injection(final String debugSelector,final Matcher<List<String>> matcher,final RuleId ruleId,final IRawGrammar grammar,
    final int priority) {
    this.debugSelector=debugSelector;
    this.matcher=matcher;
    this.ruleId=ruleId;
    this.grammar=grammar;
    this.priority=priority;
  }
  boolean matches(final List<String> states) {
    return matcher.matches(states);
  }
}
