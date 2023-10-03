package org.eclipse.tm4e.core.internal.theme.css;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.theme.IStyle;
import org.eclipse.tm4e.core.theme.RGB;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.css.CSSPrimitiveValue;

public final class CSSDocumentHandler implements DocumentHandler{
  private final List<IStyle> list=new ArrayList<>();
  @Nullable
  private CSSStyle currentStyle;
  @Override
  public void comment(@Nullable final String arg0) throws CSSException {}
  @Override
  public void endDocument(@Nullable final InputSource arg0) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void endFontFace() throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void endMedia(@Nullable final SACMediaList arg0) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void endPage(@Nullable final String arg0,@Nullable final String arg1) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void endSelector(@Nullable final SelectorList selector) throws CSSException {
    currentStyle=null;
  }
  @Override
  public void ignorableAtRule(@Nullable final String arg0) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void importStyle(@Nullable final String arg0,@Nullable final SACMediaList arg1,@Nullable final String arg2)
    throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void namespaceDeclaration(@Nullable final String arg0,@Nullable final String arg1) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void property(@Nullable final String name,@Nullable final LexicalUnit value,final boolean arg2)
    throws CSSException {
    final CSSStyle currentStyle=this.currentStyle;
    if(currentStyle!=null&&name!=null&&value!=null) {
      switch(name) {
        case "color":
          currentStyle.setColor(createRGB(value));
          break;
        case "background-color":
          currentStyle.setBackgroundColor(createRGB(value));
          break;
        case "font-weight":
          currentStyle.setBold(value.getStringValue().toUpperCase().contains("BOLD"));
          break;
        case "font-style":
          currentStyle.setItalic(value.getStringValue().toUpperCase().contains("ITALIC"));
          break;
        case "text-decoration":
          final String decoration=value.getStringValue().toUpperCase();
          if(decoration.contains("UNDERLINE")) {
            currentStyle.setUnderline(true);
          }
          if(decoration.contains("LINE-THROUGH")) {
            currentStyle.setStrikeThrough(true);
          }
          break;
      }
    }
  }
  private RGB createRGB(final LexicalUnit value) {
    final RGBColorImpl rgbColor=new RGBColorImpl(value);
    final int green=(int)rgbColor.getGreen().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
    final int red=(int)rgbColor.getRed().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
    final int blue=(int)rgbColor.getBlue().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
    return new RGB(red,green,blue);
  }
  @Override
  public void startDocument(@Nullable final InputSource arg0) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void startFontFace() throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void startMedia(@Nullable final SACMediaList arg0) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void startPage(@Nullable final String arg0,@Nullable final String arg1) throws CSSException {
    // TODO Auto-generated method stub
  }
  @Override
  public void startSelector(@Nullable final SelectorList selector) throws CSSException {
    assert selector!=null;
    currentStyle=new CSSStyle(selector);
    list.add(currentStyle);
  }
  public List<IStyle> getList() {
    return list;
  }
}
