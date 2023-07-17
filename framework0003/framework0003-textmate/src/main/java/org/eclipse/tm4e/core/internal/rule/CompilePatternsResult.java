package org.eclipse.tm4e.core.internal.rule;

final class CompilePatternsResult{
  final RuleId[] patterns;
  final boolean hasMissingPatterns;
  CompilePatternsResult(final RuleId[] patterns,final boolean hasMissingPatterns) {
    this.hasMissingPatterns=hasMissingPatterns;
    this.patterns=patterns;
  }
}
