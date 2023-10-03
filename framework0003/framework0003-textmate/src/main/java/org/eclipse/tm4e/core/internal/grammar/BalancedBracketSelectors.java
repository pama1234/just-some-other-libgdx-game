package org.eclipse.tm4e.core.internal.grammar;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.tm4e.core.internal.matcher.Matcher;

public final class BalancedBracketSelectors{
  private final Matcher<List<String>>[] balancedBracketScopes;
  private final Matcher<List<String>>[] unbalancedBracketScopes;
  private boolean allowAny=false;
  public BalancedBracketSelectors(
    final List<String> balancedBracketScopes,
    final List<String> unbalancedBracketScopes) {
    this.balancedBracketScopes=balancedBracketScopes.stream()
      .flatMap(selector-> {
        if("*".equals(selector)) {
          this.allowAny=true;
          return Stream.empty();
        }
        return Matcher.createMatchers(selector).stream().map(m->m.matcher);
      })
      .toArray(Matcher[]::new);
    this.unbalancedBracketScopes=unbalancedBracketScopes.stream()
      .flatMap(selector->Matcher.createMatchers(selector).stream().map(m->m.matcher))
      .toArray(Matcher[]::new);
  }
  boolean matchesAlways() {
    return this.allowAny&&this.unbalancedBracketScopes.length==0;
  }
  boolean matchesNever() {
    return !this.allowAny&&this.balancedBracketScopes.length==0;
  }
  boolean match(final List<String> scopes) {
    for(final Matcher<List<String>> excluder:this.unbalancedBracketScopes) {
      if(excluder.matches(scopes)) {
        return false;
      }
    }
    for(final Matcher<List<String>> includer:this.balancedBracketScopes) {
      if(includer.matches(scopes)) {
        return true;
      }
    }
    return this.allowAny;
  }
}
