package org.eclipse.tm4e.core.internal.theme.raw;

import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;

public interface IRawTheme{
  @Nullable
  String getName();
  @Nullable
  Collection<IRawThemeSetting> getSettings();
}
