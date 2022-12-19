package pama1234.gdx.game.ui.util;

import pama1234.gdx.game.util.RectF;
import pama1234.gdx.game.util.function.ExecuteF;
import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.game.util.function.GetInt;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.Tools;

public class TextButton<T extends UtilScreen>extends Button<T>{
  public String text;
  public boolean textOffset=true;//TODO
  public GetInt bu;
  public RectF rect;
  public TextButton(T p,boolean textOffset,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd,String text,GetInt bu,GetFloat x,GetFloat y) {
    this(p,textOffset,active,press,clickStart,clickEnd,text,bu,x,y,null,bu::get);//()->bu.get()*2);
    this.rect.w=()->p.textWidth(this.text)+(this.textOffset?p.pu:0);//TODO
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd,String text,GetInt bu,GetFloat x,GetFloat y,GetFloat h) {
    this(p,textOffset,active,press,clickStart,clickEnd,text,bu,x,y,null,h);//()->bu.get()*2);
    this.rect.w=()->p.textWidth(this.text)+(this.textOffset?p.pu:0);//TODO
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd,String text,GetInt bu,GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super(p,active,press,clickStart,clickEnd);
    this.text=text;
    this.textOffset=textOffset;
    this.bu=bu;
    this.rect=new RectF(x,y,w,h);
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x.get(),ty=rect.y.get(),tw=rect.w.get(),th=rect.h.get();
    if(touch!=null) {
      // p.fill(127,240);
      p.fill(94,203,234,200);
      p.textColor(255,220);
    }else {
      p.fill(127,191);
      // p.fill(94,203,234,191);
      p.textColor(255,200);
    }
    p.beginBlend();//TODO
    // p.rect(tx+p.pus,ty+p.pus,tw+(textOffset?-p.pus:p.pus),th-p.pus);
    p.rect(tx+p.pus,ty,tw+(textOffset?-p.pus:p.pus),th);
    // float tl=tw-(textOffset?p.pu*3/2:p.pu);//TODO
    // p.text(text,tx+(tw-tl)/2f,ty+(th-p.pu)/2f);
    p.text(text,tx+(textOffset?p.pu/2:p.pus),ty+(th-p.pu)/2f-p.pus);
    // p.text(text,tx+(textOffset?p.pu/2:p.pus),ty+(th-p.pu)/2f);
    // p.text(text,tx+(textOffset?(tw-p.pus*8)/2f:0),ty+(th-p.pu)/2f);
    p.endBlend();
  }
  public boolean inButton(float xIn,float yIn) {
    return Tools.inBox(xIn,yIn,rect.x.get(),rect.y.get(),rect.w.get(),rect.h.get());
  }
}