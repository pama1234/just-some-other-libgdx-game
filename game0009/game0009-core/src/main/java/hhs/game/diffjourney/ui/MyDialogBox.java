package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.ui.DialogBox;

public class MyDialogBox extends DialogBox{
  Animation<TextureRegion> anim;
  float time=0;
  public MyDialogBox(Texture back) {
    super(back);
    anim=AnimationBuilder.createAnim(Resourse.asset.get("anim/blink.png"),.2f,1,6);
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
    super.draw(arg0,arg1);
    // TODO: Implement this method
  }
}
