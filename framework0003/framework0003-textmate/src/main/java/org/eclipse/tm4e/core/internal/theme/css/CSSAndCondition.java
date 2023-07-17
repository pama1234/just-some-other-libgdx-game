package org.eclipse.tm4e.core.internal.theme.css;

final class CSSAndCondition extends AbstractCombinatorCondition{
  CSSAndCondition(final ExtendedCondition c1,final ExtendedCondition c2) {
    super(c1,c2);
  }
  @Override
  public short getConditionType() {
    return SAC_AND_CONDITION;
  }
  @Override
  public int nbMatch(final String... names) {
    return firstCondition.nbMatch(names)+secondCondition.nbMatch(names);
  }
  @Override
  public int nbClass() {
    return firstCondition.nbClass()+secondCondition.nbClass();
  }
}
