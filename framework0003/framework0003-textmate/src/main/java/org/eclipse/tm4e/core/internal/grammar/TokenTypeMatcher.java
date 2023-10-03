package org.eclipse.tm4e.core.internal.grammar;

import java.util.List;

import org.eclipse.tm4e.core.internal.matcher.Matcher;

final class TokenTypeMatcher{
  final Matcher<List<String>> matcher;
  final int type;
  TokenTypeMatcher(final Matcher<List<String>> matcher,final int type) {
    this.matcher=matcher;
    this.type=type;
  }
}
