package pama1234.gdx.game.ui.util;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.Tools;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

public class TextButton<T extends UtilScreen>extends Button<T>{
  public static Color fillColor=UtilScreen.color(127,191);
  public boolean textOffset=true;//TODO
  @Deprecated
  public GetInt bu;
  public RectI rect;
  public ButtonEvent updateText;
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
  public TextButton(T p,boolean textOffset,GetBoolean active,
    ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,
    GetInt bu,GetFloat x,GetFloat y) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,null,bu::get);//()->bu.get()*2);
    this.rect.w(()->p.textWidthNoScale(this.text)*p.pus+(this.textOffset?p.pu:0));//TODO
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,
    ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,
    GetInt bu,GetFloat x,GetFloat y,GetFloat h,boolean mouseLimit) {
    this(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,null,h);//()->bu.get()*2);
    this.rect.w(()->p.textWidthNoScale(this.text)*p.pus+(this.textOffset?p.pu:0));//TODO
    this.mouseLimit=mouseLimit;
  }
  public TextButton(T p,boolean textOffset,
    GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,
    String textIn,GetInt bu,GetFloat x,GetFloat y,GetFloat h,boolean mouseLimit) {
    super(p,active,press,clickStart,clickEnd);
    this.updateText=updateText;
    text=textIn;
    // updateText();
    this.textOffset=textOffset;
    this.bu=bu;
    this.rect=new RectF(x,y,()->p.textWidthNoScale(this.text)*p.pus+(this.textOffset?p.pu:0),h);
    this.mouseLimit=mouseLimit;
  }
  public TextButton(T p,boolean textOffset,GetBoolean active,
    ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd,ButtonEvent updateText,
    GetInt bu,GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
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
    final float tx=rect.x(),ty=rect.y(),tw=rect.w(),th=rect.h();
    // TODO
    // p.noStroke();
    if(touch!=null) {
      // if(inButton(p.mouse.x,p.mouse.y)) p.fill(0,90,130,200); else 
      p.fill(94,203,234,200);
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
}