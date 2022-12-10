package pama1234.gdx.game.ui;

import pama1234.gdx.game.util.GetFloat;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.listener.EntityListener;

public class TextField extends com.badlogic.gdx.scenes.scene2d.ui.TextField implements EntityListener{
  public RectF rect;
  public GetFloat size;
  public TextField(String text,TextFieldStyle style) {
    super(text,style);
  }
  public TextField(String text,TextFieldStyle style,RectF rect,GetFloat size) {
    super(text,style);
    this.rect=rect;
    this.size=size;
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
