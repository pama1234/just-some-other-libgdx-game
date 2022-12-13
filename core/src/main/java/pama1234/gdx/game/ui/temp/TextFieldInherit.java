package pama1234.gdx.game.ui.temp;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pama1234.gdx.game.util.RectF;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.util.listener.EntityListener;

public class TextFieldInherit extends TextField implements EntityListener{
  public RectF rect;
  public GetFloat size;
  public TextFieldInherit(String text,TextFieldStyle style,RectF rect,GetFloat size) {
    super(text,style);
    this.rect=rect;
    this.size=size;
    frameResized(1,1);
  }
  @Override
  public void frameResized(int w,int h) {
    setPosition(rect.x(),rect.y());
    //---
    setSize(rect.w(),rect.h());
    TextFieldStyle tfs=getStyle();
    tfs.font.getData().setScale(size.get());
    setStyle(tfs);//TODO libgdx is shit}
  }
}
