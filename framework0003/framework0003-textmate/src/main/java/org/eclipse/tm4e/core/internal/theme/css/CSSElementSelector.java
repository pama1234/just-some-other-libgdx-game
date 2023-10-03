package org.eclipse.tm4e.core.internal.theme.css;

import org.eclipse.jdt.annotation.Nullable;

final class CSSElementSelector extends AbstractElementSelector{
  CSSElementSelector(@Nullable final String uri,@Nullable final String name) {
    super(uri,name);
  }
  @Override
  public short getSelectorType() {
    return SAC_ELEMENT_NODE_SELECTOR;
  }
  @Override
  public int getSpecificity() {
    return (getLocalName()==null)?0:1;
  }
  @Override
  public int nbMatch(final String... names) {
    return 0;
  }
  @Override
  public int nbClass() {
    return 0;
  }
}
