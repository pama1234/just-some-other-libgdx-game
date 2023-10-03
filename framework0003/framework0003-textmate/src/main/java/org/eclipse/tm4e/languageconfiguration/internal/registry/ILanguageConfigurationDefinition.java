package org.eclipse.tm4e.languageconfiguration.internal.registry;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.languageconfiguration.internal.model.LanguageConfiguration;
import org.eclipse.tm4e.registry.ITMResource;

public interface ILanguageConfigurationDefinition extends ITMResource{
  IContentType getContentType();
  @Nullable
  LanguageConfiguration getLanguageConfiguration();
  boolean isOnEnterEnabled();
  void setOnEnterEnabled(boolean onEnterEnabled);
  boolean isBracketAutoClosingEnabled();
  void setBracketAutoClosingEnabled(boolean bracketAutoClosingEnabled);
  boolean isMatchingPairsEnabled();
  void setMatchingPairsEnabled(boolean matchingPairsEnabled);
}
