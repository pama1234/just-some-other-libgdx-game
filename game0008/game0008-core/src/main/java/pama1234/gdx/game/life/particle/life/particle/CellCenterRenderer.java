package pama1234.gdx.game.life.particle.life.particle;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.util.ui.FadeGraphics;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.vec.Vec2f;
import pama1234.server.game.life.particle.core.Box;

import com.badlogic.gdx.graphics.Texture;

public class CellCenterRenderer extends CellCenter{
  public FadeGraphics fadeGraphics;
  public boolean drawScore;
  public boolean debugRect;

  public float fracX,fracY;
  public float x,y;
  public float playerPX,playerPY;

  public CellCenterRenderer(Screen0045 p,ParticleSandbox particleSandbox,MetaCellCenter parent) {
    super(p,particleSandbox,parent);
    int d=UtilMath.ceil(core.viewRange*2);
    fadeGraphics=new FadeGraphics(p,d,d);
  }
  public Graphics createGraphics(Screen0045 p,int w,int h) {
    Graphics out=new Graphics(p,w,h);
    return out;
  }
  @Override
  public void display() {
    if(!render) return;
    render();
    p.beginBlend();

    p.image(layer(1).texture,x,y);
    if(p.debug) drawDebug();
    p.endBlend();

    fadeGraphics.tick();
  }
  /** 将位面的内容渲染到Graphics上 */
  public void render() {
    p.endShape();

    Graphics l_0=layer(0);
    Graphics l_1=layer(1);
    p.beginBlend();
    {
      l_1.beginShape();
      Cell player=particleSandbox.gameManager.select;
      MassPoint point=player.core.point;
      float playerX=point.pos.x;
      float playerY=point.pos.y;
      fracX=playerX-UtilMath.round(playerX);
      fracY=playerY-UtilMath.round(playerY);
      playerX-=fracX;
      playerY-=fracY;

      p.pushMatrix();
      x=playerX-core.viewRange;
      y=playerY-core.viewRange;
      p.translate(-x,-y);
      float playerDX=playerPX-playerX;
      float playerDY=playerPY-playerY;
      drawFadeBackground(l_0.texture,playerDX+x,playerDY+y);
      playerPX=playerX;
      playerPY=playerY;
      if(debugRect) debugRect();
      super.display();
      box();
      p.popMatrix();

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
  public void debugRect() {
    p.rect(x-2,y-2,4,core.viewRange*2+4);
    p.rect(x-2,y-2,core.viewRange*2+4,4);
    p.rect(x+core.viewRange*2-2,y-2,4,core.viewRange*2+4);
    p.rect(x-2,y+core.viewRange*2-2,core.viewRange*2+4,4);
  }
  public void drawDebug() {
    if(drawScore) {
      p.textColor(255);
      p.textScale(1/16f);
      for(Cell i:list) {
        Vec2f pos=i.core.point.pos;
        p.text(Tools.getFloatString(i.core.score.pos,4,4),pos.x-40*p.textScale(),pos.y-8*p.textScale());
      }
      p.textScale(1);
    }
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
    p.translate(-core.x1+layer_cell_size,-core.y1+layer_cell_size);
    p.strokeWeight(1);
    p.beginBlend();
    for(Box i:core.box.boxCenter.list) {
      if(i.displayAlpha>0) {
        p.stroke(255,i.displayAlpha);
        p.rect(i.rect);
      }
    }
    p.endBlend();
    p.popMatrix();
  }
  /**
   * layerCache经过fade着色器，透明度降低
   */
  public void drawFadeBackground(Texture texture,float x,float y) {
    fadeGraphics.drawFadeBackground(texture,x,y);
  }
  public Graphics layer(int in) {
    return fadeGraphics.layer(in);
  }
}
