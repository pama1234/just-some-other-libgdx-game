package org.eclipse.tm4e.core.internal.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

final class MatcherBuilder<T>{
  // private static final Logger LOGGER=System.getLogger(MatcherBuilder.class.getName());
  final List<MatcherWithPriority<T>> results=new ArrayList<>();
  private final Tokenizer tokenizer;
  private final NameMatcher<T> matchesName;
  @Nullable
  private String token;
  MatcherBuilder(final CharSequence selector,final NameMatcher<T> matchesName) {
    tokenizer=new Tokenizer(selector);
    this.matchesName=matchesName;
    // defining local token variable for annotation-based null analysis
    var token=this.token=tokenizer.next();
    while(token!=null) {
      int priority=0;
      if(token.length()==2&&token.charAt(1)==':') {
        switch(token.charAt(0)) {
          case 'R':
            priority=1;
            break;
          case 'L':
            priority=-1;
            break;
          default:
            System.out.println("Unknown priority %s in scope selector %s"+" "+token+" "+selector);
        }
        this.token=tokenizer.next();
      }
      final Matcher<T> matcher=parseConjunction();
      results.add(new MatcherWithPriority<>(matcher,priority));
      if(!",".equals(this.token)) {
        break;
      }
      token=this.token=tokenizer.next();
    }
  }
  @Nullable
  private Matcher<T> parseOperand() {
    if("-".equals(token)) {
      token=tokenizer.next();
      final var expressionToNegate=parseOperand();
      return matcherInput->expressionToNegate!=null&&!expressionToNegate.matches(matcherInput);
    }
    if("(".equals(token)) {
      token=tokenizer.next();
      final var expressionInParents=parseInnerExpression();
      if(")".equals(token)) {
        token=tokenizer.next();
      }
      return expressionInParents;
    }
    // defining local token variable for annotation-based null analysis
    var token=this.token;
    if(token!=null&&isIdentifier(token)) {
      final var identifiers=new ArrayList<String>();
      do {
        identifiers.add(token);
        token=this.token=tokenizer.next();
      }while(token!=null&&isIdentifier(token));
      return matcherInput->matchesName.matches(identifiers,matcherInput);
    }
    return null;
  }
  private Matcher<T> parseConjunction() {
    final var matchers=new ArrayList<Matcher<T>>();
    Matcher<T> matcher=parseOperand();
    while(matcher!=null) {
      matchers.add(matcher);
      matcher=parseOperand();
    }
    // every (and)
    return matcherInput-> {
      // same as 'matchers.stream().allMatch(m -> m.test(matcherInput))' but more memory friendly
      for(final Matcher<T> matcher1:matchers) {
        if(!matcher1.matches(matcherInput)) {
          return false;
        }
      }
      return true;
    };
  }
  private Matcher<T> parseInnerExpression() {
    final var matchers=new ArrayList<Matcher<T>>();
    Matcher<T> matcher=parseConjunction();
    while(true) {
      matchers.add(matcher);
      if("|".equals(token)||",".equals(token)) {
        do {
          token=tokenizer.next();
        }while("|".equals(token)||",".equals(token)); // ignore subsequent commas
      }else {
        break;
      }
      matcher=parseConjunction();
    }
    // some (or)
    return matcherInput-> {
      // same as 'matchers.stream().anyMatch(m -> m.test(matcherInput))' but more memory friendly
      for(final Matcher<T> matcher1:matchers) {
        if(matcher1.matches(matcherInput)) {
          return true;
        }
      }
      return false;
    };
  }
  private boolean isIdentifier(final String token) {
    if(token.isEmpty()) return false;
    for(int i=0;i<token.length();i++) {
      final char ch=token.charAt(i);
      if(ch=='.'||ch==':'||ch=='_'
        ||ch>='a'&&ch<='z'
        ||ch>='A'&&ch<='Z'
        ||ch>='0'&&ch<='9') continue;
      return false;
    }
    return true;
  }
  private static final class Tokenizer{
    static final Pattern TOKEN_PATTERN=Pattern.compile("([LR]:|[\\w\\.:][\\w\\.:\\-]*|[\\,\\|\\-\\(\\)])");
    final java.util.regex.Matcher regex;
    Tokenizer(final CharSequence input) {
      regex=TOKEN_PATTERN.matcher(input);
    }
    @Nullable
    String next() {
      if(!regex.find()) {
        return null;
      }
      return regex.group();
    }
  }
}
