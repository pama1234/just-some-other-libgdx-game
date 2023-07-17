package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.RGBColor;

public class RGBColorImpl extends AbstractCSSValue implements RGBColor{
  private final CSSPrimitiveValue red;
  private final CSSPrimitiveValue green;
  private final CSSPrimitiveValue blue;
  // public RGBColorImpl() {
  //   red=green=blue=null;
  // }
  public RGBColorImpl(final LexicalUnit lexicalUnit) {
    LexicalUnit nextUnit=lexicalUnit.getParameters();
    red=new Measure(nextUnit);
    nextUnit=nextUnit.getNextLexicalUnit().getNextLexicalUnit();
    green=new Measure(nextUnit);
    nextUnit=nextUnit.getNextLexicalUnit().getNextLexicalUnit();
    blue=new Measure(nextUnit);
  }
  @Override
  public CSSPrimitiveValue getRed() {
    return red;
  }
  @Override
  public CSSPrimitiveValue getGreen() {
    return green;
  }
  @Override
  public CSSPrimitiveValue getBlue() {
    return blue;
  }
  @Override
  public RGBColor getRGBColorValue() throws DOMException {
    return this;
  }
  @Override
  public short getPrimitiveType() {
    return CSS_RGBCOLOR;
  }
  @Override
  public String getCssText() {
    return "rgb("+red.getCssText()+", "+green.getCssText()+", "+blue.getCssText()+")";
  }
}
