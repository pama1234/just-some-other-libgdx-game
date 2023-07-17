package pama1234.gdx.game.ui;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.DrawableEntity;
import pama1234.gdx.game.ui.util.TextField.TextFieldStyle;
import pama1234.gdx.util.app.UtilScreen;

public class ColorTextFieldStyle extends TextFieldStyle{
  public Color cursorColor,selectionColor,backgroundColor;
  public UtilScreen p;
  {
    cursorColor=UtilScreen.color(0,191);
    selectionColor=UtilScreen.color(255,204,0);
    backgroundColor=UtilScreen.color(221,244,196);
  }
  public ColorTextFieldStyle(UtilScreen p) {
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
    this.p=p;
  }
}
