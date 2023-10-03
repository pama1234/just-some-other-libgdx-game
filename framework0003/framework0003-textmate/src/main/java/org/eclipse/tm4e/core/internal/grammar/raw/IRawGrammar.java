package org.eclipse.tm4e.core.internal.grammar.raw;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

public interface IRawGrammar{
  IRawRepository getRepository();
  String getScopeName();
  @Nullable // TODO non-null in upstream project
  Collection<IRawRule> getPatterns();
  @Nullable
  Map<String,IRawRule> getInjections();
  @Nullable
  String getInjectionSelector();
  Collection<String> getFileTypes();
  @Nullable
  String getName();
  @Nullable
  String getFirstLineMatch();
  void setRepository(IRawRepository repository);
  IRawRule toRawRule();
}
