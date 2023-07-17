package org.eclipse.tm4e.registry.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.registry.IGrammarDefinition;

final class GrammarCache{
  private final Map<String,IGrammarDefinition> definitions=new HashMap<>();
  private final Map<String,Collection<String>> injections=new HashMap<>();
  private final Map<IContentType,String> scopeNameBindings=new HashMap<>();
  void registerGrammarDefinition(final IGrammarDefinition definition) {
    definitions.put(definition.getScopeName(),definition);
  }
  void unregisterGrammarDefinition(final IGrammarDefinition definition) {
    definitions.remove(definition.getScopeName());
  }
  Collection<IGrammarDefinition> getDefinitions() {
    return this.definitions.values();
  }
  @Nullable
  IGrammarDefinition getDefinition(final String scopeName) {
    return definitions.get(scopeName);
  }
  @Nullable
  Collection<String> getInjections(final String scopeName) {
    return injections.get(scopeName);
  }
  void registerInjection(final String scopeName,final String injectTo) {
    var injections=getInjections(injectTo);
    if(injections==null) {
      injections=new ArrayList<>();
      this.injections.put(injectTo,injections);
    }
    injections.add(scopeName);
  }
  @Nullable
  String getScopeNameForContentType(final IContentType contentType) {
    return scopeNameBindings.get(contentType);
  }
  List<IContentType> getContentTypesForScope(@Nullable final String scopeName) {
    if(scopeName==null) {
      return Collections.emptyList();
    }
    return scopeNameBindings.entrySet().stream().filter(map->scopeName.equals(map.getValue()))
      .map(Entry::getKey).collect(Collectors.toList());
  }
  void registerContentTypeBinding(final IContentType contentType,final String scopeName) {
    scopeNameBindings.put(contentType,scopeName);
  }
}
