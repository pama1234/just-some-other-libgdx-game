package pama1234.gdx.game.ui.element;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class TextButtonCam<T extends UtilScreen>extends TextButton<T>{

  public Color pressFillColor=UtilScreenColor.color(94,203,234,200),
    pressTextColor=UtilScreenColor.color(255,220),
    standbyFillColor=UtilScreenColor.color(127,191),
    standbyTextColor=UtilScreenColor.color(255,200);

  {
    nx=()->touch.x;
    ny=()->touch.y;
  }
  public TextButtonCam(T p) {
    super(p);
  }
  public TextButtonCam(T p,TextButtonEvent<T> updateText) {
    this(p,updateText,()->true,true);
  }
  public TextButtonCam(T p,TextButtonEvent<T> updateText,GetBoolean active,boolean textOffset) {
    this(p);
    textOffset(textOffset)
      .activeCondition(active)
      .textSupplier(updateText)
      .updateText();
  }
  // @Deprecated
  public TextButtonCam(T p,boolean textOffset,GetBoolean active,TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd,TextButtonEvent<T> updateText,GetInt bu,GetFloat x,GetFloat y) {
    this(p);
    textOffset(textOffset)
      .activeCondition(active)
      .allButtonEvent(press,clickStart,clickEnd)
      .textSupplier(updateText)
      .rectAutoWidth(x,y,bu::get);
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x(),ty=rect.y(),tw=rect.w(),th=rect.h();
    p.beginBlend();
    if(touch!=null) {
      p.fill(pressFillColor);
      p.textColor(pressTextColor);
    }else {
      p.fill(standbyFillColor);
      p.textColor(standbyTextColor);
    }
    p.rect(tx,ty,tw,th);
    p.text(text,tx+(textOffset?8:0),ty+(th-p.textSize()*p.textScale())/2f-1*p.textScale());
    p.endBlend();
  }
  @Override
  public GetFloat createDefaultWidthSupplier(T p) {
    return ()->p.textWidthNoScale(this.text)+(this.textOffset?16:0);
  }
  //---------------------------------------------------------------------------
  @UniFontDependent
  public TextButtonCam<T> rectAuto(float x,float y) {
    rectAuto(()->x,()->y);
    return this;
  }
  public TextButtonCam<T> rectAutoWidth(float x,float y,float h) {
    super.rectAutoWidth(()->x,()->y,()->h);
    return this;
  }
  //---------------------------------------------------------------------------
  @Override
  public TextButtonCam<T> allTextButtonEvent(TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd) {
    super.allTextButtonEvent(press,clickStart,clickEnd);
    return this;
  }
  @Override
  public TextButtonCam<T> mouseLimit(boolean mouseLimit) {
    super.mouseLimit(mouseLimit);
    return this;
  }
  @Override
  public TextButtonCam<T> rectAuto(GetFloat x,GetFloat y) {
    super.rectAutoWidth(x,y,()->18);
    return this;
  }
  @Override
  public TextButtonCam<T> rectAutoWidth(GetFloat x,GetFloat y,GetFloat h) {
    super.rectAutoWidth(x,y,h);
    return this;
  }
  @Override
  public TextButtonCam<T> rectAutoHeight(GetFloat x,GetFloat y,GetFloat w) {
    super.rectAutoHeight(x,y,w);
    return this;
  }
  @Override
  public TextButtonCam<T> rectF(GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super.rectF(x,y,w,h);
    return this;
  }
  @Override
  public TextButtonCam<T> text(String text) {
    super.text(text);
    return this;
  }
  @Override
  public TextButtonCam<T> textOffset(boolean textOffset) {
    super.textOffset(textOffset);
    return this;
  }
  @Override
  public TextButtonCam<T> textSupplier(TextButtonEvent<T> updateText) {
    super.textSupplier(updateText);
    return this;
  }
  @Override
  public TextButtonCam<T> updateText() {
    super.updateText();
    return this;
  }
}