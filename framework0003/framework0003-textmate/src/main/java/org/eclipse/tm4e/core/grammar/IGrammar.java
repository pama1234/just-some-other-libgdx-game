package org.eclipse.tm4e.core.grammar;

import java.time.Duration;
import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;

public interface IGrammar{
  @Nullable
  String getName();
  String getScopeName();
  Collection<String> getFileTypes();
  ITokenizeLineResult<IToken[]> tokenizeLine(String lineText);
  ITokenizeLineResult<IToken[]> tokenizeLine(String lineText,@Nullable IStateStack prevState,@Nullable Duration timeLimit);
  ITokenizeLineResult<int[]> tokenizeLine2(String lineText);
  ITokenizeLineResult<int[]> tokenizeLine2(String lineText,@Nullable IStateStack prevState,@Nullable Duration timeLimit);
}
