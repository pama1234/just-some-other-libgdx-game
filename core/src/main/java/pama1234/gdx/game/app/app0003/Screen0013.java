package pama1234.gdx.game.app.app0003;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class Screen0013 extends ScreenCore3D{
  public String text="pama1234";
  Decal decal;
  @Override
  public void setup() {
    backgroundColor(0);
    initDecal();
  }
  public void initDecal() {
    Graphics tg=new Graphics(this,64,64);
    tg.beginShape();
    background(255);
    tg.endShape();
    TextureRegion tr=new TextureRegion(tg.texture);
    decal=Decal.newDecal(tr,true);
    decal.setPosition(textWidth(text)/4f,18,4);
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {
    float step=f_0002(frameCount)/2f;
    decalFlush(decal);
    fontBatch.setBlendFunctionSeparate(
      GL20.GL_SRC_ALPHA,
      GL20.GL_ONE_MINUS_SRC_ALPHA,
      GL20.GL_SRC_ALPHA,
      GL20.GL_ONE_MINUS_SRC_ALPHA);
    textColor(213,53,105,127);
    text(text,step,0);
    textColor(0,89,132,127);
    text(text,-step,0);
    fontBatch.flush();
    setDefaultBlendFunc(fontBatch);
    textColor(0);
    text(Tools.cutToLastDigitString(step),0,20);
  }
  public void setDefaultBlendFunc(SpriteBatch in) {
    in.setBlendFunctionSeparate(
      GL20.GL_SRC_ALPHA,
      GL20.GL_ONE_MINUS_SRC_ALPHA,
      GL20.GL_SRC_ALPHA,
      GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  public float f_0001(float in) {
    in%=16;
    if(in>8) in=16-in;
    return in/16f;
  }
  public float f_0002(float in) {
    return UtilMath.sin(in*UtilMath.degRad)/2f;
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
