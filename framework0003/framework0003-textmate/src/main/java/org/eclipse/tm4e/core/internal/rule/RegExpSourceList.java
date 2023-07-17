package org.eclipse.tm4e.core.internal.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.Nullable;

public final class RegExpSourceList{
  private final List<RegExpSource> items=new ArrayList<>();
  private boolean hasAnchors;
  @Nullable
  private CompiledRule cached;
  private final CompiledRule[][] anchorCache=new CompiledRule[2][2];
  private void disposeCache() {
    cached=null;
    anchorCache[0][0]=null;
    anchorCache[0][1]=null;
    anchorCache[1][0]=null;
    anchorCache[1][1]=null;
  }
  void add(final RegExpSource item) {
    items.add(item);
    if(!hasAnchors) {
      hasAnchors=item.hasAnchor();
    }
  }
  void remove(final RegExpSource item) {
    items.add(0,item);
    if(!hasAnchors) {
      hasAnchors=item.hasAnchor();
    }
  }
  int length() {
    return items.size();
  }
  void setSource(final int index,final String newSource) {
    final RegExpSource r=items.get(index);
    if(!Objects.equals(r.getSource(),newSource)) {
      disposeCache();
      r.setSource(newSource);
    }
  }
  public CompiledRule compile() {
    var cached=this.cached;
    if(cached==null) {
      final List<String> regexps=items.stream().map(RegExpSource::getSource).collect(Collectors.toList());
      cached=this.cached=new CompiledRule(regexps,items.stream().map(e->e.ruleId).toArray(RuleId[]::new));
    }
    return cached;
  }
  CompiledRule compileAG(final boolean allowA,final boolean allowG) {
    if(!hasAnchors) {
      return compile();
    }
    final var indexA=allowA?1:0;
    final var indexG=allowG?1:0;
    var rule=anchorCache[indexA][indexG];
    if(rule==null) {
      rule=anchorCache[indexA][indexG]=resolveAnchors(allowA,allowG);
    }
    return rule;
  }
  private CompiledRule resolveAnchors(final boolean allowA,final boolean allowG) {
    final List<String> regexps=items.stream().map(e->e.resolveAnchors(allowA,allowG)).collect(Collectors.toList());
    return new CompiledRule(regexps,items.stream().map(e->e.ruleId).toArray(RuleId[]::new));
  }
}
