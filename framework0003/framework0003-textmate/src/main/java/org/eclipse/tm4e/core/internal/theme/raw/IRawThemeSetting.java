package org.eclipse.tm4e.core.internal.theme.raw;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.theme.IThemeSetting;

public interface IRawThemeSetting{
  @Nullable
  String getName();
  @Nullable
  Object getScope();
  @Nullable
  IThemeSetting getSetting();
}
