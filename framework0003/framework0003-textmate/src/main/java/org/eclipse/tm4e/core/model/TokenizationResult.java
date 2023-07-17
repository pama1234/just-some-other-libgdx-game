package org.eclipse.tm4e.core.model;

import java.util.List;

import org.eclipse.tm4e.core.grammar.IStateStack;

public final class TokenizationResult{
  final List<TMToken> tokens;
  int actualStopOffset;
  IStateStack endState;
  final boolean stoppedEarly;
  public TokenizationResult(final List<TMToken> tokens,final int actualStopOffset,final IStateStack endState,
    final boolean stoppedEarly) {
    this.tokens=tokens;
    this.actualStopOffset=actualStopOffset;
    this.endState=endState;
    this.stoppedEarly=stoppedEarly;
  }
  public IStateStack getEndState() {
    return endState;
  }
  public List<TMToken> getTokens() {
    return tokens;
  }
  public boolean isStoppedEarly() {
    return stoppedEarly;
  }
}
