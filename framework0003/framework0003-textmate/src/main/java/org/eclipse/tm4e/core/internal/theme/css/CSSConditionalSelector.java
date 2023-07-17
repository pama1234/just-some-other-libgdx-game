package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.SimpleSelector;

final class CSSConditionalSelector implements ConditionalSelector,ExtendedSelector{
  private final ExtendedSelector selector;
  private final ExtendedCondition condition;
  CSSConditionalSelector(final ExtendedSelector simpleSelector,final ExtendedCondition condition) {
    this.selector=simpleSelector;
    this.condition=condition;
  }
  @Override
  public short getSelectorType() {
    return SAC_CONDITIONAL_SELECTOR;
  }
  @Override
  public Condition getCondition() {
    return condition;
  }
  @Override
  public SimpleSelector getSimpleSelector() {
    return selector;
  }
  @Override
  public int getSpecificity() {
    return selector.getSpecificity()+condition.getSpecificity();
  }
  @Override
  public int nbMatch(final String... names) {
    return selector.nbMatch(names)+condition.nbMatch(names);
  }
  @Override
  public int nbClass() {
    return selector.nbClass()+condition.nbClass();
  }
}
