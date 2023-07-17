package org.eclipse.tm4e.core.internal.rule;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.grammar.raw.IRawGrammar;
import org.eclipse.tm4e.core.internal.grammar.raw.IRawRepository;

interface IGrammarRegistry{
  @Nullable
  IRawGrammar getExternalGrammar(String scopeName,IRawRepository repository);
}
