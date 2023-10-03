package org.eclipse.tm4e.ui.snippets;

import org.eclipse.tm4e.registry.ITMResource;

public interface ISnippet extends ITMResource{
  String getName();
  String getScopeName();
  String getContent();
}
