package org.eclipse.tm4e.ui.themes;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.registry.ITMDefinition;

public interface IThemeAssociation extends ITMDefinition{
  String getThemeId();
  @Nullable
  String getScopeName();
  boolean isWhenDark();
}
