package org.eclipse.tm4e.core.internal.rule;

import org.eclipse.jdt.annotation.Nullable;

final class IncludeOnlyRule extends Rule{
  final boolean hasMissingPatterns;
  final RuleId[] patterns;
  @Nullable
  private RegExpSourceList cachedCompiledPatterns;
  IncludeOnlyRule(final RuleId id,@Nullable final String name,@Nullable final String contentName,
    final CompilePatternsResult patterns) {
    super(id,name,contentName);
    this.patterns=patterns.patterns;
    this.hasMissingPatterns=patterns.hasMissingPatterns;
  }
  @Override
  public void collectPatterns(final IRuleRegistry grammar,final RegExpSourceList out) {
    for(final RuleId pattern:this.patterns) {
      final Rule rule=grammar.getRule(pattern);
      rule.collectPatterns(grammar,out);
    }
  }
  @Override
  public CompiledRule compile(final IRuleRegistry grammar,@Nullable final String endRegexSource) {
    return getCachedCompiledPatterns(grammar).compile();
  }
  @Override
  public CompiledRule compileAG(final IRuleRegistry grammar,@Nullable final String endRegexSource,
    final boolean allowA,final boolean allowG) {
    return getCachedCompiledPatterns(grammar).compileAG(allowA,allowG);
  }
  private RegExpSourceList getCachedCompiledPatterns(final IRuleRegistry grammar) {
    var cachedCompiledPatterns=this.cachedCompiledPatterns;
    if(cachedCompiledPatterns==null) {
      cachedCompiledPatterns=new RegExpSourceList();
      this.collectPatterns(grammar,cachedCompiledPatterns);
      this.cachedCompiledPatterns=cachedCompiledPatterns;
    }
    return cachedCompiledPatterns;
  }
}
