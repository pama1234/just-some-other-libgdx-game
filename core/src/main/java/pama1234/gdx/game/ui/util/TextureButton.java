package pama1234.gdx.game.ui.util;

import static pama1234.math.Tools.inBox;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.util.RectF;
import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.game.util.function.GetInt;
import pama1234.gdx.util.app.UtilScreen;

public class TextureButton<T extends UtilScreen>extends Button<T>{
  public GetTextureRegion image;
  public boolean textOffset=true;//TODO
  // int x,y,w,h;
  public GetInt bu;
  // GetFloat x,y,w,h;
  public RectF rect;
  public TextureButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,GetTextureRegion image,GetInt bu,GetFloat x,GetFloat y) {
    this(p,textOffset,active,press,clickStart,clickEnd,image,bu,x,y,null,null);//()->bu.get()*2);
    this.rect.w=()->bu.get()/32f*this.image.get().getRegionWidth();
    this.rect.h=()->bu.get()/32f*this.image.get().getRegionHeight();
  }
  public TextureButton(T p,boolean textOffset,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,GetTextureRegion image,GetInt bu,GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super(p,active,press,clickStart,clickEnd);
    this.image=image;
    this.textOffset=textOffset;
    this.bu=bu;
    this.rect=new RectF(x,y,w,h);
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=rect.x.get(),ty=rect.y.get(),tw=rect.w.get(),th=rect.h.get();
    // if(touch!=null) {
    //   p.fill(94,203,234,200);
    //   p.textColor(255,220);
    // }else {
    //   p.fill(127,191);
    //   p.textColor(255,200);
    // }
    p.beginBlend();//TODO
    p.image(image.get(),tx,ty,tw,th);
    p.endBlend();
  }
  public boolean inButton(float xIn,float yIn) {
    return inBox(xIn,yIn,rect.x.get(),rect.y.get(),rect.w.get(),rect.h.get());
  }
  @FunctionalInterface
  public interface GetTextureRegion{
    public TextureRegion get();
  }
}