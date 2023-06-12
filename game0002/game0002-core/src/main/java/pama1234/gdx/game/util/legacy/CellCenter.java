package pama1234.gdx.game.util.legacy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;

public class CellCenter extends EntityCenter<RealGame,Cell>{
  public static final float r_const=Cell.dist/4;
  public static final int boxR=320;
  public static final int layer_cell_size=(int)Cell.size;
  public static final int fadeStep=16;
  public final MetaCellCenter meta;
  public static final boolean boxed=true;
  public static final int x1=-boxR,y1=-boxR,x2=boxR,y2=boxR;
  public static final int w=x2-x1,h=y2-y1;
  public ShaderProgram fade;
  public Graphics layer;
  @SuppressWarnings("unused")
  public CellCenter(final RealGame p,final MetaCellCenter parent) {
    super(p);
    this.meta=parent;
    if(boxed) layer=new Graphics(p,w+layer_cell_size*2+1,h+layer_cell_size*2+1);
    else layer=new Graphics(p,w+w/2,h+h/2);
    // layer.begin();
    // p.noStroke();
    // layer.end();
    fade=new ShaderProgram(
      Gdx.files.internal("shader/main0006/fade.vert").readString(),
      Gdx.files.internal("shader/main0006/fade.frag").readString());
  }
  @Override
  public void update() {
    for(Cell i:list) {
      i.point.vel.toNumber();
      i.point.pos.toNumber();
      i.back.clear();
      i.score.toNumber();
    }
    super.update();
    for(Cell i:list) {
      for(Cell j:list) {
        if(i==j) continue;
        final MetaInfo info=meta.list.get(i.meta).list.get(j.meta);
        float dx=j.point.pos.x-i.point.pos.x;
        float dy=j.point.pos.y-i.point.pos.y;
        if(!boxed) {
          if(dx>w/2f) dx=w-dx;
          if(dy>h/2f) dy=h-dy;
        }
        final float r=UtilMath.sqrt(dx*dx+dy*dy);
        if(r>info.max) continue;
        else if(r<info.scoreR&&info.scoreG>0) j.back.add(i);
        dx/=r;
        dy/=r;
        final float f;
        if(r<Cell.dist) f=r(r);
        else if(info.g!=0&&r>info.min) f=f(r,info.g);
        else continue;
        i.point.vel.add(dx*f,dy*f);
      }
    }
    for(Cell i:list) {
      i.point.vel.toNumber();
      if(boxed) i.point.setInBox(x1,y1,x2,y2);
      else i.point.moveInBox(x1,y1,x2,y2);
      i.point.pos.toNumber();
      for(Cell t:i.back) {
        final float scoreG=meta.list.get(t.meta).list.get(i.meta).scoreG;
        if(scoreG!=0) i.score.vel+=t.score.pos*scoreG;
      }
    }
    //---
    // drawCanvas();
  }
  public void drawCanvas() {
    // System.out.println("CellCenter.drawCanvas()");
    p.endShape();
    // layer.begin();
    // fade();
    layer.beginShape();
    // p.beginShape();
    fade();
    super.display();
    box();
    layer.endShape();
  }
  public void box() {
    // layer.begin();
    p.noFill();
    p.stroke(255);
    if(boxed) p.rect(0,0,layer.width()-1,layer.height()-1);
    else p.rect(w/4f-layer_cell_size/2,h/4f-layer_cell_size/2,w-1+layer_cell_size,h-1+layer_cell_size);
    p.noStroke();
    p.doFill();
    // layer.end();
  }
  public float f(final float r,final float g) {
    return g/r;
  }
  public float r(final float r) {
    return -r_const/r;
  }
  @Override
  public void display() {
    drawCanvas();
    if(boxed) p.image(layer.texture,x1-layer_cell_size,y1-layer_cell_size);
    else p.image(layer.texture,x1-w/4f,y1-h/4f);
  }
  public void fade() {
    // fade.bind();
    p.imageBatch.setShader(fade);
    p.image(layer.texture,0,0);
    p.imageBatch.setShader(null);
    // p.background(0);
  }
  public void dispose() {
    super.dispose();
  }
}
