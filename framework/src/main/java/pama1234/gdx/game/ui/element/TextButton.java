package pama1234.gdx.game.ui.element;

import com.badlogic.gdx.graphics.Color;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class TextButton<T extends UtilScreen>extends Button<T>{
  public static Color fillColor=UtilScreen.color(127,191);
  public static Color pressedFillColor=UtilScreen.color(94,203,234,200);
  public boolean textOffset=true;//TODO
  public RectI rect;
  // public TextButtonEvent<T> updateText;
  public EventExecuter updateText;
  /**
   * 
   * @param p          父实例
   * @param textOffset 文字是否进行<code>p.pu/2</code>的偏移 {@link pama1234.gdx.util.app.UtilScreenCore#pu
   *                   UtilScreenCore.pu}
   * @param active     是否启用此按钮，不启用时不进行显示
   * @param press      按钮按下时每帧的方法
   * @param clickStart 按钮按下事件的方法
   * @param clickEnd   按钮松开事件的方法
   * @param updateText 文字更新的方法
   * @param bu         无意义的按钮height，未修正
   * @param x          获取位置的方法
   * @param y          获取位置的方法
   */
  // @Deprecated
  public TextButton(T p,boolean textOffset,GetBoolean active,
    TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd,TextButtonEvent<T> updateText,
    GetInt bu,GetFloat x,GetFloat y) {
    this(p);
    textOffset(textOffset)
      .activeCondition(active)
      .allButtonEvent(press,clickStart,clickEnd)
      .textSupplier(updateText)
      .rectAutoWidth(x,y,bu::get);
  }
  // @Deprecated
  public TextButton(T p,boolean textOffset,GetBoolean active,
    TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd,TextButtonEvent<T> updateText,
    GetInt bu,GetFloat x,GetFloat y,GetFloat h) {
    this(p);
    textOffset(textOffset)
      .activeCondition(active)
      .allButtonEvent(press,clickStart,clickEnd)
      .textSupplier(updateText)
      .rectAutoWidth(x,y,bu::get);
    // .mouseLimit(mouseLimit);
  }
  public TextButton(T p) {
    super(p);
  }
  public TextButton(T p,TextButtonEvent<T> updateText,GetBoolean active,boolean textOffset) {
    super(p);
    textOffset(textOffset)
      .activeCondition(active)
      .textSupplier(updateText)
      .updateText();
  }
  public TextButton(T p,TextButtonEvent<T> updateText) {
    this(p,updateText,()->true,true);
  }
  // @Override
  public TextButton<T> updateText() {
    // updateText.execute(this);
    updateText.execute();
    return this;
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x(),ty=rect.y(),tw=rect.w(),th=rect.h();
    // TODO
    // p.noStroke();
    if(touch!=null) {
      // if(inButton(p.mouse.x,p.mouse.y)) p.fill(0,90,130,200); else 
      p.fill(pressedFillColor);
      p.textColor(255,220);
    }else {
      p.fill(fillColor);
      p.textColor(255,200);
    }
    p.beginBlend();//TODO
    p.rect(tx,ty,tw,th);
    p.text(text,tx+(textOffset?p.pu/2:0),ty+(th-p.pu)/2f-p.pus);
    p.endBlend();
  }
  @Override
  public boolean inButton(float xIn,float yIn) {
    return Tools.inBox(xIn,yIn,rect.x(),rect.y(),rect.w(),rect.h());
  }
  public GetFloat createDefaultWidthSupplier(T p) {
    return ()->p.textWidthNoScale(text)*p.pus+(textOffset?p.pu:0);
  }
  //---------------------------------------------------------------------------
  @FunctionalInterface
  public interface TextButtonEvent<T extends UtilScreen>extends ButtonEventBase<TextButton<T>>{
    public void execute(TextButton<T> button);
  }
  //---------------------------------------------------------------------------
  @Override
  public TextButton<T> activeCondition(GetBoolean active) {
    super.activeCondition(active);
    return this;
  }
  public TextButton<T> allTextButtonEvent(TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd) {
    pressE=()->press.execute(this);
    clickStartE=()->clickStart.execute(this);
    clickEndE=()->clickEnd.execute(this);
    return this;
  }
  public TextButton<T> allButtonEvent(TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd) {
    return allTextButtonEvent(press,clickStart,clickEnd);
  }
  public TextButton<T> textSupplier(TextButtonEvent<T> updateText) {
    this.updateText=()->updateText.execute(this);
    updateText();
    return this;
  }
  public TextButton<T> text(String text) {
    this.text=text;
    return this;
  }
  public TextButton<T> textOffset(boolean textOffset) {
    this.textOffset=textOffset;
    return this;
  }
  public TextButton<T> mouseLimit(boolean mouseLimit) {
    this.mouseLimit=mouseLimit;
    return this;
  }
  public TextButton<T> rectF(GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    this.rect=new RectF(x,y,w,h);
    return this;
  }
  public TextButton<T> rectAutoWidth(GetFloat x,GetFloat y,GetFloat h) {
    this.rect=new RectF(x,y,createDefaultWidthSupplier(p),h);
    return this;
  }
  public TextButton<T> rectAutoHeight(GetFloat x,GetFloat y,GetFloat w) {
    this.rect=new RectF(x,y,w,p::getButtonUnitLength);
    return this;
  }
  // public TextButton<T> rectAutoWidth(float x,float y,GetFloat h) {
  //   this.rect=new RectF(()->x,()->y,createDefaultWidthSupplier(p),h);
  //   return this;
  // }
  public TextButton<T> rectAuto(GetFloat x,GetFloat y) {
    this.rect=new RectF(x,y,createDefaultWidthSupplier(p),p::getButtonUnitLength);
    return this;
  }
  // public TextButton<T> rectAuto(float x,float y) {
  //   this.rect=new RectF(()->x,()->y,createDefaultWidthSupplier(p),p::getButtonUnitLength);
  //   return this;
  // }
  //---------------------------------------------------------------------------
}