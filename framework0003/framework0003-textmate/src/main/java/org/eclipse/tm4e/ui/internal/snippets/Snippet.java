package org.eclipse.tm4e.ui.internal.snippets;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.tm4e.registry.TextMateResource;
import org.eclipse.tm4e.registry.XMLConstants;
import org.eclipse.tm4e.ui.snippets.ISnippet;

final class Snippet extends TextMateResource implements ISnippet{
  private final String scopeName;
  private final String name;
  Snippet() {
    scopeName="<set-by-gson>";
    name="<set-by-gson>";
  }
  Snippet(final String scopeName,final String path,final String name) {
    super(path);
    this.scopeName=scopeName;
    this.name=name;
  }
  Snippet(final IConfigurationElement ce) {
    super(ce);
    this.scopeName=ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
    this.name=ce.getAttribute(XMLConstants.NAME_ATTR);
  }
  @Override
  public String getName() {
    return name;
  }
  @Override
  public String getContent() {
    final var content=getResourceContent();
    return content==null?"":content;
  }
  @Override
  public String getScopeName() {
    return scopeName;
  }
}
