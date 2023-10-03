package org.eclipse.tm4e.core.internal.theme.css;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.css.sac.ElementSelector;

public abstract class AbstractElementSelector implements ElementSelector,ExtendedSelector{
  @Nullable
  private final String namespaceURI;
  @Nullable
  private final String localName;
  protected AbstractElementSelector(@Nullable final String uri,@Nullable final String name) {
    namespaceURI=uri;
    localName=name;
  }
  @Nullable
  @Override
  public String getNamespaceURI() {
    return namespaceURI;
  }
  @Nullable
  @Override
  public String getLocalName() {
    return localName;
  }
}
