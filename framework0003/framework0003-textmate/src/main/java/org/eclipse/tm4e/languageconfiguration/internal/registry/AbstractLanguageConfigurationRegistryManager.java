package org.eclipse.tm4e.languageconfiguration.internal.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.languageconfiguration.internal.model.LanguageConfiguration;

public abstract class AbstractLanguageConfigurationRegistryManager implements ILanguageConfigurationRegistryManager{
  protected final Map<IContentType,ILanguageConfigurationDefinition> pluginDefinitions=new HashMap<>();
  protected final Map<IContentType,ILanguageConfigurationDefinition> userDefinitions=new HashMap<>();
  @Override
  public ILanguageConfigurationDefinition[] getDefinitions() {
    final var definitions=new HashSet<ILanguageConfigurationDefinition>();
    userDefinitions.values().forEach(definitions::add);
    pluginDefinitions.values().forEach(definitions::add);
    return definitions.toArray(ILanguageConfigurationDefinition[]::new);
  }
  @Override
  public void registerLanguageConfigurationDefinition(final ILanguageConfigurationDefinition definition) {
    if(definition.getPluginId()==null) {
      userDefinitions.put(definition.getContentType(),definition);
    }else {
      pluginDefinitions.put(definition.getContentType(),definition);
    }
  }
  @Override
  public void unregisterLanguageConfigurationDefinition(final ILanguageConfigurationDefinition definition) {
    if(definition.getPluginId()==null) {
      userDefinitions.remove(definition.getContentType());
    }else {
      pluginDefinitions.remove(definition.getContentType());
    }
  }
  @Nullable
  @Override
  public LanguageConfiguration getLanguageConfigurationFor(final IContentType... contentTypes) {
    for(final IContentType contentType:contentTypes) {
      if(userDefinitions.containsKey(contentType)) {
        return userDefinitions.get(contentType).getLanguageConfiguration();
      }
      if(pluginDefinitions.containsKey(contentType)) {
        return pluginDefinitions.get(contentType).getLanguageConfiguration();
      }
    }
    return null;
  }
}
