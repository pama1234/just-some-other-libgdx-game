package pama1234.gdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import pama1234.gdx.game.DrawableEntity;
import pama1234.gdx.util.app.UtilScreen;

public class CodeTextFieldStyle extends TextFieldStyle{
  public UtilScreen p;
  public CodeTextFieldStyle(UtilScreen p) {//TODO
    super(p.font,p.color(0),
      new DrawableEntity(p,(batch,x,y,w,h)-> {
        p.beginBlend();
        p.fill(0,191);
        // p.rect(x,y,w<0.1f?p.pus:w,h);
        p.rect(x,y,p.pus,h);
        p.rect(x,y,w,h);
        p.endBlend();
      }),
      // {
      //   // {minWidth=p.pus;}
      //   // @Override
      //   // public void frameResized(int w,int h) {
      //   //   minWidth=p.pus;
      //   // }
      // },
      new DrawableEntity(p,(batch,x,y,w,h)-> {
        p.fill(255,204,0);
        p.rect(x,y,w,h);
      }),
      new DrawableEntity(p,(batch,x,y,w,h)-> {
        p.fill(221,244,196);
        p.rect(x,y,w,h);
      }));
    this.p=p;
  }
}
