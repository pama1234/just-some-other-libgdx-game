package org.eclipse.tm4e.core.registry;

import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.theme.raw.IRawTheme;

public interface IRegistryOptions{
  @Nullable
  default IRawTheme getTheme() {
    return null;
  }
  @Nullable
  default List<String> getColorMap() {
    return null;
  }
  @Nullable
  default IGrammarSource getGrammarSource(@SuppressWarnings("unused") final String scopeName) {
    return null;
  }
  @Nullable
  default Collection<String> getInjections(@SuppressWarnings("unused") final String scopeName) {
    return null;
  }
}
