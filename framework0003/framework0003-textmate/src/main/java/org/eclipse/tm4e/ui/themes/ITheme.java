package org.eclipse.tm4e.ui.themes;

import org.eclipse.jdt.annotation.Nullable;

import pama1234.gdx.textmate.StyledText;

public interface ITheme extends ITokenProvider{
  String getId();
  String getName();
  @Nullable
  String getPath();
  @Nullable
  String getPluginId();
  @Nullable
  String toCSSStyleSheet();
  boolean isDark();
  boolean isDefault();
  void initializeViewerColors(StyledText styledText);
}
