package org.eclipse.tm4e.core.internal.rule;

import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.defaultIfNull;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.oniguruma.OnigCaptureIndex;

public final class BeginEndRule extends Rule{
  private final RegExpSource begin;
  public final List<@Nullable CaptureRule> beginCaptures;
  private final RegExpSource end;
  public final List<@Nullable CaptureRule> endCaptures;
  public final boolean endHasBackReferences;
  private final boolean applyEndPatternLast;
  final boolean hasMissingPatterns;
  final RuleId[] patterns;
  @Nullable
  private RegExpSourceList cachedCompiledPatterns;
  BeginEndRule(final RuleId id,@Nullable final String name,@Nullable final String contentName,final String begin,
    final List<@Nullable CaptureRule> beginCaptures,@Nullable final String end,
    final List<@Nullable CaptureRule> endCaptures,final boolean applyEndPatternLast,
    final CompilePatternsResult patterns) {
    super(id,name,contentName);
    this.begin=new RegExpSource(begin,this.id);
    this.beginCaptures=beginCaptures;
    this.end=new RegExpSource(defaultIfNull(end,"\uFFFF"),RuleId.END_RULE);
    this.endHasBackReferences=this.end.hasBackReferences;
    this.endCaptures=endCaptures;
    this.applyEndPatternLast=applyEndPatternLast;
    this.patterns=patterns.patterns;
    this.hasMissingPatterns=patterns.hasMissingPatterns;
  }
  public String debugBeginRegExp() {
    return this.begin.getSource();
  }
  public String debugEndRegExp() {
    return this.end.getSource();
  }
  public String getEndWithResolvedBackReferences(final CharSequence lineText,final OnigCaptureIndex[] captureIndices) {
    return this.end.resolveBackReferences(lineText,captureIndices);
  }
  @Override
  public void collectPatterns(final IRuleRegistry grammar,final RegExpSourceList out) {
    out.add(this.begin);
  }
  @Override
  public CompiledRule compile(final IRuleRegistry grammar,@Nullable final String endRegexSource) {
    return getCachedCompiledPatterns(grammar,endRegexSource).compile();
  }
  @Override
  public CompiledRule compileAG(final IRuleRegistry grammar,@Nullable final String endRegexSource,final boolean allowA,
    final boolean allowG) {
    return getCachedCompiledPatterns(grammar,endRegexSource).compileAG(allowA,allowG);
  }
  private RegExpSourceList getCachedCompiledPatterns(final IRuleRegistry grammar,@Nullable final String endRegexSource) {
    var cachedCompiledPatterns=this.cachedCompiledPatterns;
    if(cachedCompiledPatterns==null) {
      cachedCompiledPatterns=new RegExpSourceList();
      for(final var pattern:this.patterns) {
        final var rule=grammar.getRule(pattern);
        rule.collectPatterns(grammar,cachedCompiledPatterns);
      }
      if(this.applyEndPatternLast) {
        cachedCompiledPatterns.add(this.endHasBackReferences?this.end.clone():this.end);
      }else {
        cachedCompiledPatterns.remove(this.endHasBackReferences?this.end.clone():this.end);
      }
      this.cachedCompiledPatterns=cachedCompiledPatterns;
    }
    if(this.endHasBackReferences&&endRegexSource!=null) {
      if(this.applyEndPatternLast) {
        cachedCompiledPatterns.setSource(cachedCompiledPatterns.length()-1,endRegexSource);
      }else {
        cachedCompiledPatterns.setSource(0,endRegexSource);
      }
    }
    return cachedCompiledPatterns;
  }
}
