package pama1234.gdx.game.ui.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class Slider<T extends UtilScreen>extends TextButtonCam<T>{
  public float min=0,max=1;
  public float pos;
  public Slider(T p,boolean textOffset,GetBoolean active,
    ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,
    ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y,
    float pos) {
    super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y);
    this.pos=pos;
  }
  public Slider(T p,boolean textOffset,GetBoolean active,
    ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,
    ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y,
    float pos,float min,float max) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,pos);
    this.min=min;
    this.max=max;
  }
  @Override
  public void press() {
    pos=UtilMath.constrain(Tools.map(nx.get(),rect.x(),rect.x()+rect.w(),min,max),min,max);//TODO
    // pos=UtilMath.constrain(Tools.map(nx.get(),rect.x()+1,rect.x()+rect.w(),min,max),min,max);
    super.press();
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x.get(),ty=rect.y.get(),tw=rect.w.get(),th=rect.h.get();
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
    p.text(text,tx+(textOffset?8:0),ty+(th-16)/2f-1);
    p.endBlend();
  }
}