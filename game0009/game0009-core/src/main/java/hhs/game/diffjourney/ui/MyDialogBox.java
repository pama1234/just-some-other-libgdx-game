package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.DialogBox;
import pama1234.gdx.util.font.MultiChunkFont;

public class MyDialogBox extends DialogBox{
  Animation<TextureRegion> anim;
  float time=0;
  float scale=1,oscale=1;
  public MyDialogBox(Texture back) {
    super(back);
    anim=AnimationBuilder.createAnim(Resource.asset.get("anim/blink.png"),.2f,1,6);
  }
  @Override
  public void act(float arg0) {
    super.act(arg0);
    if((time+=arg0)>anim.getAnimationDuration()) {
      time=0;
    }
    // TODO: Implement this method
  }
  @Override
  public void draw(Batch arg0,float arg1) {
    arg0.draw(anim.getKeyFrame(time,true),0,getHeight(),getWidth()/5,getWidth()/5);
    font.getData().setScale(scale);
    super.draw(arg0,arg1);
    font.getData().setScale(oscale);
    // TODO: Implement this method
  }
  @Override
  public void setFont(MultiChunkFont font) {
    super.setFont(font);
    oscale=font.getScaleX();
    // TODO: Implement this method
  }
  public float getScale() {
    return this.scale;
  }
  public void setScale(int scale) {
    this.scale=scale;
  }
}
