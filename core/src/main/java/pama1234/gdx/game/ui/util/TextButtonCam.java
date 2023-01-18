package pama1234.gdx.game.ui.util;

import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.game.util.function.GetInt;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.Tools;

public class TextButtonCam<T extends UtilScreen>extends TextButton<T>{
  {
    nx=()->touch.x;
    ny=()->touch.y;
  }
  public TextButtonCam(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,GetInt bu,GetFloat x,GetFloat y) {
    super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y);
    this.rect.w=()->p.textWidthCam(this.text)+(this.textOffset?16:0);//TODO
  }
  @Override
  public boolean inButton(float xIn,float yIn) {
    return Tools.inBox(xIn,yIn,rect.x.get(),rect.y.get(),rect.w.get(),rect.h.get());
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
    p.rect(tx,ty,tw,th);
    // p.rect(tx+1,ty,tw+(textOffset?-1:1),th);
    p.text(text,tx+(textOffset?8:0),ty+(th-16)/2f-1);
    p.endBlend();
  }
}