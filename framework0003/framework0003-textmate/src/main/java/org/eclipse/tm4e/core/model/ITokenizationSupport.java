package org.eclipse.tm4e.core.model;

import java.time.Duration;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IStateStack;

public interface ITokenizationSupport{
  IStateStack getInitialState();
  TokenizationResult tokenize(String line,@Nullable IStateStack state);
  TokenizationResult tokenize(String line,@Nullable IStateStack state,@Nullable Integer offsetDelta,@Nullable Duration timeLimit);
}
