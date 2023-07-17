package org.eclipse.tm4e.core.internal.grammar;

import org.eclipse.tm4e.core.grammar.ITokenizeLineResult;

final class TokenizeLineResult<T> implements ITokenizeLineResult<T>{
  private final T tokens;
  private final StateStack ruleStack;
  private final boolean stoppedEarly;
  TokenizeLineResult(final T tokens,final StateStack ruleStack,final boolean stoppedEarly) {
    this.tokens=tokens;
    this.ruleStack=ruleStack;
    this.stoppedEarly=stoppedEarly;
  }
  @Override
  public T getTokens() {
    return tokens;
  }
  @Override
  public StateStack getRuleStack() {
    return ruleStack;
  }
  @Override
  public boolean isStoppedEarly() {
    return stoppedEarly;
  }
}
