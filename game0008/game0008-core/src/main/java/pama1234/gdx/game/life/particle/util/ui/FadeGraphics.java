package pama1234.gdx.game.life.particle.util.ui;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class FadeGraphics{
  public UtilScreen p;

  public Graphics[] layerArray;
  public ShaderProgram fade;
  public int cacheTick;

  public FadeGraphics(UtilScreen p,int width,int height) {
    this.p=p;

    layerArray=new Graphics[2];
    for(int i=0;i<layerArray.length;i++) {
      layerArray[i]=createGraphics(p,width,height);
    }
    fade=new ShaderProgram(
      Gdx.files.internal("shader/main0006/fade.vert").readString(),
      Gdx.files.internal("shader/main0006/fade.frag").readString());
    fade.bind();
    float f=255f;
    fade.setUniformf("fadeStepSlow",8/f);
    fade.setUniformf("fadeStepFast",4/f);
    fade.setUniformf("fadeThreshold",128/f);
    fade.setUniformf("voidThresholdF",16/f);
    fade.setUniformf("voidColor",UtilScreenColor.color(0,0,0,0));
  }
  public Graphics createGraphics(UtilScreen p,int w,int h) {
    Graphics out=new Graphics(p,w,h);
    return out;
  }
  public void tick() {
    cacheTick+=1;
    if(cacheTick>=layerArray.length) cacheTick=0;
  }
  /**
   * layerCache经过fade着色器，透明度降低
   */
  public void drawFadeBackground(Texture texture,float x,float y) {
    p.imageBatch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);
    p.imageBatch.setShader(fade);
    p.image(texture,x,y);
    p.imageBatch.setShader(null);
    p.imageBatch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  public Graphics layer(int in) {
    return layerArray[(cacheTick+in)%layerArray.length];
  }
}
