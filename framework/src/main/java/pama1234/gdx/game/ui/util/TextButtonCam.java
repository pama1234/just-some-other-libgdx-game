package pama1234.gdx.game.ui.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class TextButtonCam<T extends UtilScreen>extends TextButton<T>{
  {
    nx=()->touch.x;
    ny=()->touch.y;
  }
  public TextButtonCam(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,float x,float y) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,()->x,()->y);
  }
  public TextButtonCam(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y) {
    super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y);
    this.rect.w(()->p.textWidthNoScale(this.text)+(this.textOffset?16:0));//TODO
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
    p.rect(tx,ty,tw,th);
    // p.rect(tx+1,ty,tw+(textOffset?-1:1),th);
    p.text(text,tx+(textOffset?8:0),ty+(th-16)/2f-1);
    p.endBlend();
  }
}