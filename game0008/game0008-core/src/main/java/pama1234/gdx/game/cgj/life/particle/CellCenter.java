package pama1234.gdx.game.cgj.life.particle;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.element.GameManager;
import pama1234.gdx.game.cgj.life.particle.element.Scoreboard;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectD;
import pama1234.util.wrapper.Center;

public class CellCenter extends EntityCenter<Screen0045,Cell>{
  public static final float r_const=Cell.dist/4;
  public static final int layer_cell_size=(int)Cell.size;
  public static final int fadeStep=16;
  public final int boxR;
  public final boolean boxed=true;
  public final int x1,y1,x2,y2;
  public final int w,h;
  public final MetaCellCenter meta;
  public Center<Box> boxCenter;
  public int maxBoxCount=16;
  public int boxSize=32;
  public int boxLifeTime=60*10;
  public boolean pause;
  public CellCenter(final Screen0045 p,final MetaCellCenter parent) {
    this(p,240,parent);
  }
  public CellCenter(final Screen0045 p,int size,final MetaCellCenter parent) {
    super(p);
    boxR=size;
    x1=-boxR;
    y1=-boxR;
    x2=boxR;
    y2=boxR;
    w=x2-x1;
    h=y2-y1;
    this.meta=parent;
    boxCenter=new Center<Box>();
    // boxCenter.add.add(new Box(new RectD(-32,-32,64,64)));
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    key=Character.toLowerCase(key);
    if(key=='p') pause=!pause;
  }
  @Override
  public void update() {
    if(pause) return;
    if(p.frameCount%30==0) {
      if(Scoreboard.gameLevel>0) {
        if(p.random(1/(Scoreboard.gameLevel+0.1f))<3f) {
          if(boxCenter.list.size()<maxBoxCount) {
            RectD rect=new RectD(p.random(x1,x2-boxSize),p.random(y1,y2-boxSize),boxSize,boxSize);
            if(GameManager.select.point.pos.dist(rect.cx(),rect.cy())<boxSize*1.5f) return;
            boxCenter.add.add(new Box(rect));
          }
        }
      }else {
        if(boxCenter.list.size()>0) {
          if(p.random(10)<3f) {
            boxCenter.remove.add(boxCenter.list.getFirst());
          }
        }
      }
    }
    boxCenter.refresh();
    updateCell();
  }
  public void updateCell() {
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
      for(Box j:boxCenter.list) {
        if(i.point.pos.inBox(j.rect)) {
          i.point.setOutBox(j.rect);
        }
      }
      i.point.pos.toNumber();
      for(Cell t:i.back) {
        final float scoreG=meta.list.get(t.meta).list.get(i.meta).scoreG;
        if(scoreG!=0) i.score.vel+=t.score.pos*scoreG;
      }
    }
    for(Box i:boxCenter.list) {
      i.time++;
      i.displayAlpha=255-(int)UtilMath.clamp(GameManager.select.point.pos.dist(i.rect.cx(),i.rect.cy())*2.5f,0,255);
      if(i.time>boxLifeTime) boxCenter.remove.add(i);
    }
  }
  public float f(final float r,final float g) {
    return g/r;
  }
  public float r(final float r) {
    return -r_const/r;
  }
}
