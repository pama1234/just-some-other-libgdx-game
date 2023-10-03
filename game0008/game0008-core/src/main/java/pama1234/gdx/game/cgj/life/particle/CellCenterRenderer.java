package pama1234.gdx.game.cgj.life.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.Tools;
import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.vec.Vec2f;

public class CellCenterRenderer extends CellCenter{
  public ShaderProgram fade;
  public Graphics layer;
  public Graphics[] layerArray;
  public int cacheTick;
  public boolean drawScore;
  {
    drawScore=true;
  }
  public CellCenterRenderer(Screen0045 p,int size,MetaCellCenter parent) {
    super(p,size,parent);
    if(boxed) layer=createGraphics(p,w+layer_cell_size*2,h+layer_cell_size*2);
    else layer=createGraphics(p,w+w/2,h+h/2);
    layerArray=new Graphics[2];
    layerArray[0]=layer;
    for(int i=1;i<layerArray.length;i++) {
      layerArray[i]=createGraphics(p,layer.width(),layer.height());
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
  public Graphics createGraphics(Screen0045 p,int w,int h) {
    Graphics out=new Graphics(p,w,h);
    return out;
  }
  @Override
  public void display() {
    render();
    p.beginBlend();
    if(boxed) p.image(layer(1).texture,x1-layer_cell_size,y1-layer_cell_size);
    else p.image(layer(1).texture,x1-w/4f,y1-h/4f);
    if(p.debug) drawDebug();
    p.endBlend();

    cacheTick+=1;
    if(cacheTick>=layerArray.length) cacheTick=0;
  }
  public void render() {
    p.endShape();

    Graphics l_0=layer(0);
    Graphics l_1=layer(1);
    p.beginBlend();
    {
      l_1.beginShape();
      drawFadeBackground(l_0.texture);
      super.display();
      box();
      l_1.endShape();
    }
    {
      Graphics needClear=l_0;
      needClear.begin();
      p.clear();
      needClear.end();
    }
    p.endBlend();

    p.beginShape();
  }
  public void drawDebug() {
    if(drawScore) {
      p.textColor(255);
      p.textScale(1/16f);
      for(Cell i:list) {
        Vec2f pos=i.point.pos;
        p.text(Tools.getFloatString(i.score.pos,4,4),pos.x-40*p.textScale(),pos.y-8*p.textScale());
      }
      p.textScale(1);
    }
  }
  /**
   * layerCache经过fade着色器，透明度降低
   */
  public void drawFadeBackground(Texture texture) {
    p.imageBatch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);
    p.imageBatch.setShader(fade);
    p.image(texture,0,0);
    p.imageBatch.setShader(null);
    p.imageBatch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  public Graphics layer(int in) {
    return layerArray[(cacheTick+in)%layerArray.length];
  }
  public void box() {
    p.noFill();
    p.doStroke();
    p.stroke(255);
    drawHiddenBox();
    p.noStroke();
    p.doFill();
  }
  public void drawHiddenBox() {
    p.pushMatrix();
    p.translate(-x1+layer_cell_size,-y1+layer_cell_size);
    p.strokeWeight(1);
    p.beginBlend();
    for(Box i:boxCenter.list) {
      if(i.displayAlpha>0) {
        p.stroke(255,i.displayAlpha);
        p.rect(i.rect);
      }
    }
    p.endBlend();
    p.popMatrix();
  }
}
