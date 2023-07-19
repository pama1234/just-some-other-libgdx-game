package pama1234.gdx.game.ui;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.DrawableEntity;
import pama1234.gdx.game.ui.util.TextField.TextFieldStyle;
import pama1234.gdx.util.app.UtilScreen;

public class ColorTextFieldStyle extends TextFieldStyle{
  public Color cursorColor,selectionColor,backgroundColor;
  public UtilScreen p;
  public ColorTextFieldStyle(UtilScreen p,Color cursorColor,Color selectionColor,Color backgroundColor) {
    this.cursorColor=cursorColor==null?UtilScreen.color(0,191):cursorColor;
    this.selectionColor=selectionColor==null?UtilScreen.color(255,204,0):selectionColor;
    this.backgroundColor=backgroundColor==null?UtilScreen.color(221,244,196):backgroundColor;
    this.p=p;
    init();
  }
  public ColorTextFieldStyle(UtilScreen p) {
    cursorColor=UtilScreen.color(0,191);
    selectionColor=UtilScreen.color(255,204,0);
    backgroundColor=UtilScreen.color(221,244,196);
    this.p=p;
    init();
  }
  public void init() {
    font=p.font;
    fontColor=UtilScreen.color(0);
    cursor=new DrawableEntity(p,(batch,x,y,w,h)-> {
      p.beginBlend();
      p.fill(cursorColor);
      p.rect(x,y,p.usedCamera==p.screenCam?p.pus:1,h);
      p.rect(x,y,w,h);
      p.endBlend();
    });
    selection=new DrawableEntity(p,(batch,x,y,w,h)-> {
      p.fill(selectionColor);
      p.rect(x,y,w,h);
    });
    background=new DrawableEntity(p,(batch,x,y,w,h)-> {
      p.fill(backgroundColor);
      p.rect(x,y,w,h);
    });
  }
}
