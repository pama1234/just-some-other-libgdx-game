package pama1234.gdx.game.ui.element;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.UtilMath;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class Slider<T extends UtilScreen>extends TextButtonCam<T>{
  public float min=0,max=1;
  public float pos;
  public Slider(T p) {
    super(p);
  }
  public Slider(T p,SliderEvent<T> updateText) {
    this(p,updateText,()->true,true,0);
  }
  public Slider(T p,SliderEvent<T> updateText,GetBoolean active,boolean textOffset,float pos) {
    this(p);
    textOffset(textOffset)
      .activeCondition(active)
      .textSupplierSlider(updateText)
      .pos(pos)
      .updateText();
  }
  // @Deprecated
  public Slider(T p,boolean textOffset,GetBoolean active,
    SliderEvent<T> press,SliderEvent<T> clickStart,SliderEvent<T> clickEnd,
    SliderEvent<T> updateText,GetInt bu,GetFloat x,GetFloat y,
    float pos) {
    super(p);
    // super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y);
    textOffset(textOffset)
      .activeCondition(active)
      .allSliderEvent(press,clickStart,clickEnd)
      .textSupplierSlider(updateText)
      .pos(pos)
      .rectAutoWidth(x,y,bu::get);
  }
  // @Deprecated
  public Slider(T p,boolean textOffset,GetBoolean active,
    SliderEvent<T> press,SliderEvent<T> clickStart,SliderEvent<T> clickEnd,
    SliderEvent<T> updateText,GetInt bu,GetFloat x,GetFloat y,
    float pos,float min,float max) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,pos);
    range(min,max);
  }
  @Override
  public void press() {
    pos=UtilMath.constrain(Tools.map(nx.get(),rect.x(),rect.xnw(),min,max),min,max);//TODO 有些时候可能希望pos在范围外
    super.press();
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x(),ty=rect.y(),tw=rect.w(),th=rect.h();
    p.beginBlend();
    if(touch!=null) {
      p.fill(94,203,234,200);
      p.textColor(255,220);
    }else {
      p.fill(127,191);
      p.textColor(255,200);
    }
    // float tw2=tw+(textOffset?-1:1);
    p.rect(tx,ty,tw,th);
    // p.rect(tx+1,ty,tw2,th);
    p.fill(144,222,196,191);
    p.rect(tx,ty,tw*(pos-min)/(max-min),th);
    p.text(text,tx+(textOffset?8:0),ty+(th-p.textSize()*p.textScale())/2f-1*p.textScale());
    p.endBlend();
  }
  //---------------------------------------------------------------------------
  @FunctionalInterface
  public interface SliderEvent<T extends UtilScreen>extends ButtonEventBase<Slider<T>>{
    public void execute(Slider<T> button);
  }
  public Slider<T> range(float min,float max) {
    this.min=min;
    this.max=max;
    return this;
  }
  public Slider<T> pos(float pos) {
    this.pos=pos;
    return this;
  }
  // TODO TextButtonEvent 改为 SliderEvent
  public Slider<T> allSliderEvent(SliderEvent<T> press,SliderEvent<T> clickStart,SliderEvent<T> clickEnd) {
    pressE=()->press.execute(this);
    clickStartE=()->clickStart.execute(this);
    clickEndE=()->clickEnd.execute(this);
    return this;
  }
  //---------------------------------------------------------------------------
  @Override
  public Slider<T> activeCondition(GetBoolean active) {
    super.activeCondition(active);
    return this;
  }
  @Override
  public Slider<T> allTextButtonEvent(TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd) {
    super.allTextButtonEvent(press,clickStart,clickEnd);
    return this;
  }
  @Override
  public Slider<T> mouseLimit(boolean mouseLimit) {
    super.mouseLimit(mouseLimit);
    return this;
  }
  @Override
  public Slider<T> rectAuto(GetFloat x,GetFloat y) {
    super.rectAutoWidth(x,y,()->18);
    return this;
  }
  @Override
  public Slider<T> rectAutoWidth(GetFloat x,GetFloat y,GetFloat h) {
    super.rectAutoWidth(x,y,h);
    return this;
  }
  @Override
  public Slider<T> rectAutoHeight(GetFloat x,GetFloat y,GetFloat w) {
    super.rectF(x,y,w,()->18);
    return this;
  }
  @Override
  public Slider<T> rectF(GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super.rectF(x,y,w,h);
    return this;
  }
  @Override
  public Slider<T> text(String text) {
    super.text(text);
    return this;
  }
  @Override
  public Slider<T> textOffset(boolean textOffset) {
    super.textOffset(textOffset);
    return this;
  }
  @Override
  public Slider<T> textSupplier(TextButtonEvent<T> updateText) {
    super.textSupplier(updateText);
    return this;
  }
  // @Override
  public Slider<T> textSupplierSlider(SliderEvent<T> updateText) {
    this.updateText=()->updateText.execute(this);
    updateText();
    return this;
  }
  @Override
  public Slider<T> updateText() {
    super.updateText();
    return this;
  }
}