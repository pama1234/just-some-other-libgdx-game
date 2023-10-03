package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.SimpleSelector;

public interface ExtendedSelector extends SimpleSelector{
  int getSpecificity();
  int nbMatch(String... names);
  int nbClass();
}
