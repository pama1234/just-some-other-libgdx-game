package pama1234.gdx.game.ui.util;

import pama1234.gdx.game.util.RectF;
import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.game.util.function.GetInt;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.Tools;

public class TextButton<T extends UtilScreen>extends Button<T>{
  public boolean textOffset=true;//TODO
  public GetInt bu;
  public RectF rect;
  public ButtonEvent updateText;
  public TextButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,null,bu::get);//()->bu.get()*2);
    this.rect.w=()->p.textWidthCam(this.text)*p.pus+(this.textOffset?p.pu:0);//TODO
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y,GetFloat h,boolean mouseLimit) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,null,h);//()->bu.get()*2);
    this.rect.w=()->p.textWidthCam(this.text)*p.pus+(this.textOffset?p.pu:0);//TODO
    this.mouseLimit=mouseLimit;
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,String textIn,GetInt bu,GetFloat x,GetFloat y,GetFloat h,boolean mouseLimit) {
    super(p,active,press,clickStart,clickEnd);
    this.updateText=updateText;
    text=textIn;
    // updateText();
    this.textOffset=textOffset;
    this.bu=bu;
    this.rect=new RectF(x,y,()->p.textWidthCam(this.text)*p.pus+(this.textOffset?p.pu:0),h);
    this.mouseLimit=mouseLimit;
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super(p,active,press,clickStart,clickEnd);
    this.updateText=updateText;
    updateText();
    this.textOffset=textOffset;
    this.bu=bu;
    this.rect=new RectF(x,y,w,h);
  }
  @Override
  public void updateText() {
    updateText.execute(this);
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x.get(),ty=rect.y.get(),tw=rect.w.get(),th=rect.h.get();
    if(touch!=null) {
      // if(inButton(p.mouse.x,p.mouse.y)) p.fill(0,90,130,200); else 
      p.fill(94,203,234,200);
      p.textColor(255,220);
    }else {
      p.fill(127,191);
      p.textColor(255,200);
    }
    p.beginBlend();//TODO
    p.rect(tx,ty,tw,th);
    p.text(text,tx+(textOffset?p.pu/2:0),ty+(th-p.pu)/2f-p.pus);
    p.endBlend();
  }
  public boolean inButton(float xIn,float yIn) {
    return Tools.inBox(xIn,yIn,rect.x.get(),rect.y.get(),rect.w.get(),rect.h.get());
  }
}