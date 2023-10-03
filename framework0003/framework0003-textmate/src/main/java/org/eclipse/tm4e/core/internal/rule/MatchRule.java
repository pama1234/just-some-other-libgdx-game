package org.eclipse.tm4e.core.internal.rule;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class MatchRule extends Rule{
  private final RegExpSource match;
  public final List<@Nullable CaptureRule> captures;
  @Nullable
  private RegExpSourceList cachedCompiledPatterns;
  MatchRule(final RuleId id,@Nullable final String name,final String match,final List<@Nullable CaptureRule> captures) {
    super(id,name,null);
    this.match=new RegExpSource(match,this.id);
    this.captures=captures;
  }
  @Override
  public void collectPatterns(final IRuleRegistry grammar,final RegExpSourceList out) {
    out.add(this.match);
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
