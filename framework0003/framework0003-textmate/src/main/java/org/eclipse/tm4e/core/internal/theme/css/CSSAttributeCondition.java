package org.eclipse.tm4e.core.internal.theme.css;

import org.eclipse.jdt.annotation.Nullable;

class CSSAttributeCondition extends AbstractAttributeCondition{
  @Nullable
  private final String localName;
  @Nullable
  private final String namespaceURI;
  private final boolean specified;
  protected CSSAttributeCondition(@Nullable final String localName,@Nullable final String namespaceURI,final boolean specified,
    final String value) {
    super(value);
    this.localName=localName;
    this.namespaceURI=namespaceURI;
    this.specified=specified;
  }
  @Nullable
  @Override
  public String getLocalName() {
    return localName;
  }
  @Nullable
  @Override
  public String getNamespaceURI() {
    return namespaceURI;
  }
  @Override
  public boolean getSpecified() {
    return specified;
  }
  @Override
  public short getConditionType() {
    return SAC_ATTRIBUTE_CONDITION;
  }
  @Override
  public int nbMatch(final String... names) {
    // TODO
    // String val = getValue();
    // if (val == null) {
    // return !e.getAttribute(getLocalName()).equals("");
    // }
    // return e.getAttribute(getLocalName()).equals(val);
    return 0;
  }
  @Override
  public int nbClass() {
    return 0;
  }
}
