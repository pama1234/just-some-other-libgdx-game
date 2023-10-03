package org.eclipse.tm4e.core.internal.theme.css;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;

public abstract class AbstractCSSValue implements CSSPrimitiveValue{ // FIXME rename
  // W3C CSSValue API methods
  @Override
  public String getCssText() {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    return null;
  }
  @Override
  public short getCssValueType() {
    return CSS_PRIMITIVE_VALUE;
  }
  @Override
  public void setCssText(@Nullable final String cssText) throws DOMException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }
  // W3C CSSPrimitiveValue API methods
  @Override
  public short getPrimitiveType() {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    return 0;
  }
  @Override
  public Counter getCounterValue() throws DOMException {
    // throw new DOMException(DOMException.INVALID_ACCESS_ERR,"COUNTER_ERROR");
    return null;
  }
  @Override
  public RGBColor getRGBColorValue() throws DOMException {
    // throw new DOMException(DOMException.INVALID_ACCESS_ERR,"RGBCOLOR_ERROR");
    return null;
  }
  @Override
  public Rect getRectValue() throws DOMException {
    // throw new DOMException(DOMException.INVALID_ACCESS_ERR,"RECT_ERROR");
    return null;
  }
  @Override
  public String getStringValue() throws DOMException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    return null;
  }
  @Override
  public void setFloatValue(final short arg0,final float arg1) throws DOMException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }
  @Override
  public void setStringValue(final short arg0,@Nullable final String arg1) throws DOMException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }
  // Additional methods
  @Override
  public float getFloatValue(final short valueType) throws DOMException {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    return 0;
  }
}
