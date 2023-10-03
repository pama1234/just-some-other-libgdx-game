package org.eclipse.tm4e.core.internal.theme;

import org.eclipse.jdt.annotation.Nullable;

public interface IThemeSetting{
  @Nullable
  Object getFontStyle();
  @Nullable
  String getBackground();
  @Nullable
  String getForeground();
}
