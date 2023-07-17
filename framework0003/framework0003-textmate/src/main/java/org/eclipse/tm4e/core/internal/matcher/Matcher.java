package org.eclipse.tm4e.core.internal.matcher;

import java.util.List;

@FunctionalInterface
public interface Matcher<T>{
  static List<MatcherWithPriority<List<String>>> createMatchers(final String selector) {
    return createMatchers(selector,NameMatcher.DEFAULT);
  }
  static List<MatcherWithPriority<List<String>>> createMatchers(final String selector,
    final NameMatcher<List<String>> matchesName) {
    return new MatcherBuilder<>(selector,matchesName).results;
  }
  boolean matches(T t);
}
