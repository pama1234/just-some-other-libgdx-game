package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import pama1234.gdx.util.font.MultiChunkFont;

public class PixelFontButton extends Widget{
  MultiChunkFont font=UiList.font;
  String str;
  float scale;
  Drawable drawable;
  TextButton.TextButtonStyle ts;
  public boolean down=false;
  public PixelFontButton(String str) {
    this.str=str;
    addListener(
      new InputListener() {
        @Override
        public boolean touchDown(InputEvent arg0,float arg1,float arg2,int arg3,int arg4) {
          down=true;
          return true;
        }
        @Override
        public void touchUp(InputEvent arg0,float arg1,float arg2,int arg3,int arg4) {
          down=false;
        }
      });
    ts=UiList.getTextButton();
    drawable=ts.up;
    invalidateHierarchy();
    setSize(getPrefWidth(),getPrefHeight());
  }
  void updateDrawable() {
    drawable=down?ts.down:ts.up;
  }
  @Override
  public void layout() {
    super.layout();
    setSize(getPrefWidth(),getPrefHeight());
    // TODO: Implement this method
  }
  @Override
  public void draw(Batch batch,float arg1) {
    validate();
    updateDrawable();
    float x=getX();
    float y=getY();
    float w=getWidth();
    float h=getHeight();
    if(drawable!=null) drawable.draw(batch,x,y,w,h);
    font.getData().setScale(scale!=0?scale:1);
    font.draw(batch,str,x,y+font.styleFast.size*scale);
    font.getData().setScale(UiList.originFontScale);
  }
  public float getMinWidth() {
    return 0;
  }
  public float getMinHeight() {
    return 0;
  }
  @Override
  public float getPrefWidth() {
    // TODO: Implement this method
    return font.styleFast.size*scale*str.length();
  }
  @Override
  public float getPrefHeight() {
    // TODO: Implement this method
    return font.styleFast.size*scale;
  }
  public float getScale() {
    return this.scale;
  }
  public void setScale(float scale) {
    this.scale=scale;
  }
}
