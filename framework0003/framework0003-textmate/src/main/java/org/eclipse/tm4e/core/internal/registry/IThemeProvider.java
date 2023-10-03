package org.eclipse.tm4e.core.internal.registry;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.grammar.ScopeStack;
import org.eclipse.tm4e.core.internal.theme.StyleAttributes;

public interface IThemeProvider{
  @Nullable
  StyleAttributes themeMatch(ScopeStack scopePath);
  StyleAttributes getDefaults();
}
