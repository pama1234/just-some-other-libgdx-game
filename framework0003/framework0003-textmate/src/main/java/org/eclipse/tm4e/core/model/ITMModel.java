package org.eclipse.tm4e.core.model;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IGrammar;

public interface ITMModel extends ModelTokensChangedEvent.Listenable{
  enum BackgroundTokenizationState{
    IN_PROGRESS,
    COMPLETED
  }
  BackgroundTokenizationState getBackgroundTokenizationState();
  @Nullable
  IGrammar getGrammar();
  void setGrammar(IGrammar grammar);
  void dispose();
  int getNumberOfLines();
  String getLineText(int lineIndex) throws Exception;
  @Nullable
  List<TMToken> getLineTokens(int lineIndex);
}
