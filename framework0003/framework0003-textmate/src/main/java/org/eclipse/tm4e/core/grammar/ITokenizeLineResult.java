package org.eclipse.tm4e.core.grammar;

public interface ITokenizeLineResult<T>{
  T getTokens();
  IStateStack getRuleStack();
  boolean isStoppedEarly();
}
