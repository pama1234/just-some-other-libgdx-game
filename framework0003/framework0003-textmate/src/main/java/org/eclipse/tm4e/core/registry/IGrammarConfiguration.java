package org.eclipse.tm4e.core.registry;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

public interface IGrammarConfiguration{
  @Nullable
  default Map<String,Integer> getEmbeddedLanguages() {
    return null;
  }
  @Nullable
  default Map<String,Integer> getTokenTypes() {
    return null;
  }
  @Nullable
  default List<String> getBalancedBracketSelectors() {
    return null;
  }
  @Nullable
  default List<String> getUnbalancedBracketSelectors() {
    return null;
  }
}
