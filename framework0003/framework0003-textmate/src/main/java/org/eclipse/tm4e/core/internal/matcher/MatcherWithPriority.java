package org.eclipse.tm4e.core.internal.matcher;

public final class MatcherWithPriority<T>{
  public final Matcher<T> matcher;
  public final int priority;
  MatcherWithPriority(final Matcher<T> matcher,final int priority) {
    this.matcher=matcher;
    this.priority=priority;
  }
}
