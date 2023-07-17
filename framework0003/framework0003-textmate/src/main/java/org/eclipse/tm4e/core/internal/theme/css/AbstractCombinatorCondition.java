package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;

public abstract class AbstractCombinatorCondition implements CombinatorCondition,ExtendedCondition{
  protected final ExtendedCondition firstCondition;
  protected final ExtendedCondition secondCondition;
  protected AbstractCombinatorCondition(final ExtendedCondition c1,final ExtendedCondition c2) {
    firstCondition=c1;
    secondCondition=c2;
  }
  @Override
  public Condition getFirstCondition() {
    return firstCondition;
  }
  @Override
  public Condition getSecondCondition() {
    return secondCondition;
  }
  @Override
  public int getSpecificity() {
    return firstCondition.getSpecificity()+secondCondition.getSpecificity();
  }
}
