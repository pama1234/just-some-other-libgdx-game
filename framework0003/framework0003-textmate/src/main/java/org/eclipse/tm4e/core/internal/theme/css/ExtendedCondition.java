package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.Condition;

interface ExtendedCondition extends Condition{
  int getSpecificity();
  int nbClass();
  int nbMatch(String... names);
}
