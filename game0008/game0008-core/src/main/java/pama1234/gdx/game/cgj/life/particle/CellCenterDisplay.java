package pama1234.gdx.game.cgj.life.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.element.Graphics;

public class CellCenterDisplay extends CellCenter{
  public ShaderProgram fade;
  public Graphics layer,layer_b;
  public Graphics[] layers;
  public boolean cacheTick;
  public int fadeTick,fadeTickConst=1;
  public float fadeStepSlow,
    fadeStepFast,
    fadeThreshold,
    voidThreshold;
  public CellCenterDisplay(RealGame0002 p,MetaCellCenter parent) {
    super(p,parent);
    if(boxed) layer=new Graphics(p,w+layer_cell_size*2,h+layer_cell_size*2);
    else layer=new Graphics(p,w+w/2,h+h/2);
    layer_b=new Graphics(p,layer.width(),layer.height());
    fade=new ShaderProgram(
      Gdx.files.internal("shader/main0006/fade.vert").readString(),
      Gdx.files.internal("shader/main0006/fade.frag").readString());
    fade.bind();
    fade.setUniformf("fadeStepSlow",fadeStepSlow=32f/255);
    fade.setUniformf("fadeStepFast",fadeStepFast=16f/255);
    fade.setUniformf("fadeThreshold",fadeThreshold=128f/255);
    fade.setUniformf("voidThresholdF",voidThreshold=16f/255);
    // Color c=p.backgroundColor;
    // fade.setUniformf("voidColor",UtilScreenColor.color(c.r,c.g,c.b,0));
    fade.setUniformf("voidColor",p.backgroundColor);
    // fade.setUniformf("voidColor",UtilScreenColor.color(200));
  }
  @Override
  public void display() {
    p.endShape();
    //---
    Graphics l=layer();
    Graphics lc=layerCache();
    l.beginShape();
    // p.clear();
    drawFadeBackground(lc.texture);
    super.display();
    box();
    l.endShape();
    //---
    p.beginShape();
    p.beginBlend();
    if(boxed) p.image(l.texture,x1-layer_cell_size,y1-layer_cell_size);
    else p.image(l.texture,x1-w/4f,y1-h/4f);
    p.endBlend();
    //---
    cacheTick=!cacheTick;
    fadeTick++;
    if(fadeTick>fadeTickConst) fadeTick=0;
  }
  /**
   * layerCache经过fade着色器，透明度降低
   */
  public void drawFadeBackground(Texture texture) {
    p.beginBlend();
    if(fadeTick==0) p.imageBatch.setShader(fade);
    p.image(texture,0,0);
    p.endBlend();
    p.imageBatch.setShader(null);
  }
  public Graphics layer() {
    return cacheTick?layer:layer_b;
  }
  public Graphics layerCache() {
    return cacheTick?layer_b:layer;
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
