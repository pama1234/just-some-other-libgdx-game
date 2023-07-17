package org.eclipse.tm4e.core.internal.registry;

import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.grammar.raw.IRawGrammar;

public interface IGrammarRepository{
  @Nullable
  IRawGrammar lookup(String scopeName);
  @Nullable
  Collection<String> injections(String targetScope);
}
