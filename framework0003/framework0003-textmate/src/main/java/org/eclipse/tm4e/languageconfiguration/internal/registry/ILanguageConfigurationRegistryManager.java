package org.eclipse.tm4e.languageconfiguration.internal.registry;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.languageconfiguration.internal.model.LanguageConfiguration;
import org.osgi.service.prefs.BackingStoreException;

public interface ILanguageConfigurationRegistryManager{
  // --------------- Language configuration definitions methods
  ILanguageConfigurationDefinition[] getDefinitions();
  void registerLanguageConfigurationDefinition(ILanguageConfigurationDefinition definition);
  void unregisterLanguageConfigurationDefinition(ILanguageConfigurationDefinition definition);
  void save() throws BackingStoreException;
  // --------------- Language configuration queries methods.
  @Nullable
  LanguageConfiguration getLanguageConfigurationFor(IContentType... contentTypes);
}
