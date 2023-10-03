package org.eclipse.tm4e.registry;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.osgi.service.prefs.BackingStoreException;

public interface IGrammarRegistryManager{
  // --------------- TextMate grammar definitions methods
  IGrammarDefinition[] getDefinitions();
  void registerGrammarDefinition(IGrammarDefinition definition);
  void unregisterGrammarDefinition(IGrammarDefinition definition);
  void save() throws BackingStoreException;
  // --------------- TextMate grammar queries methods.
  @Nullable
  IGrammar getGrammarFor(IContentType @Nullable [] contentTypes);
  @Nullable
  IGrammar getGrammarForScope(String scopeName);
  @Nullable
  IGrammar getGrammarForFileType(String fileType);
  @Nullable
  List<IContentType> getContentTypesForScope(String scopeName);
  @Nullable
  Collection<String> getInjections(String scopeName);
}
