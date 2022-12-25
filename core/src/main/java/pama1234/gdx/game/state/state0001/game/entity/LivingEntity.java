package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.entity.util.OuterBox;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathVar;

public class LivingEntity extends GamePointEntity<MassPoint>{
  public World0001 pw;
  //---
  public int w,h;
  public float dx,dy;
  public OuterBox outerBox;
  //---
  // public float maxLife=32;
  public MetaCreature<?> type;
  public PathVar life;
  public LivingEntity(Screen0011 p,MassPoint in,MetaCreature<?> type,Game pg) {
    super(p,in,pg);
    outerBox=new OuterBox(this);
    this.type=type;
    life=new PathVar(type.maxLife);
  }
  @Override
  public void update() {
    super.update();
    outerBox.update();
  }
  @Override
  public void display() {
    displayLife();
  }
  public void displayLife() {
    boolean flag=UtilMath.abs(life.des-life.pos)>0.1f;
    if(!flag&&UtilMath.abs(life.des-type.maxLife)<=0.1f) return;
    p.beginBlend();
    p.fill(127,127);
    p.rect(x()+dx,y()+dy-4,w,2);
    p.fill(255,63,63,191);
    float tp=w/type.maxLife;
    float tw=tp*life.pos;
    p.rect(x()+dx,y()+dy-4,tw,2);
    if(flag) {
      if(life.des>life.pos) {
        p.fill(156,95,255,191);
        p.rect(x()+dx+tw,y()+dy-4,tp*(life.des-life.pos),2);
      }else {
        p.fill(255,255,63,191);
        p.rect(x()+dx+tw,y()+dy-4,tp*(life.pos-life.des),2);
      }
    }
    p.endBlend();
  }
  public float cx() {
    return point.pos.x+dx+w/2f;
  }
  public float cy() {
    return point.pos.y+dy+h/2f;
  }
  public Block getBlock(int xIn,int yIn) {
    return pw.regions.getBlock(xIn,yIn);
  }
  public Block getBlock(float xIn,float yIn) {
    return pw.regions.getBlock(xToBlockCord(xIn),yToBlockCord(yIn));
  }
  public int blockX() {
    return xToBlockCord(x());
  }
  public int blockY() {
    return yToBlockCord(y());
  }
  public int blockX1() {
    return xToBlockCord(x()+dx);
  }
  public int blockY1() {
    return yToBlockCord(y()+dy);
  }
  public int blockX2() {
    return xToBlockCord(x()+dx+w-0.01f);//TODO
  }
  public int blockY2() {
    return yToBlockCord(y()+dy+h-0.01f);//TODO
  }
  public int xToBlockCord(float in) {//TODO static
    return UtilMath.floor(in/pw.blockWidth);
  }
  public int yToBlockCord(float in) {
    return UtilMath.floor(in/pw.blockHeight);
  }
  public boolean inOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,outerBox.x1,outerBox.y1,outerBox.w,outerBox.h);
  }
  public boolean inInnerBox(float tx,float ty) {
    return Tools.inBox(tx,ty,x()+dx,y()+dy,w,h);
  }
}