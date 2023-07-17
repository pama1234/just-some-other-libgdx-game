package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.AttributeCondition;

public abstract class AbstractAttributeCondition implements AttributeCondition,ExtendedCondition{
  private final String value;
  protected AbstractAttributeCondition(final String value) {
    this.value=value;
  }
  @Override
  public String getValue() {
    return value;
  }
  @Override
  public int getSpecificity() {
    return 1<<8;
  }
}
