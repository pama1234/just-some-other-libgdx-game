package pama1234.gdx.game.state.state0001.game.entity;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.util.OuterBox;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.player.GameMode;
import pama1234.gdx.game.state.state0001.game.region.PathVarLighting;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathVar;

public class LivingEntity extends GamePointEntity<MassPoint>{
  //---
  public OuterBox outerBox;
  //---
  @Tag(0)
  public GameMode gameMode=GameMode.survival;
  public MetaCreature<?> type;
  @Tag(1)
  public int typeId;
  @Tag(2)
  public PathVar life;
  public PathVarLighting light;
  public LivingEntity(Screen0011 p,World0001 pw,MassPoint in,MetaCreature<?> type) {
    super(p,pw,in);
    point.step=0.25f;
    outerBox=new OuterBox(this);
    this.type=type;
    life=new PathVar(type.maxLife);
    light=new PathVarLighting();
  }
  @Override
  public void update() {
    super.update();
    life.update();
    outerBox.update();
    lightingUpdate();
  }
  public void lightingUpdate() {
    float cr=0,cg=0,cb=0;
    for(int i=0;i<=outerBox.w;i++) for(int j=0;j<=outerBox.h;j++) {
      // int tx=outerBox.x1+i,
      //   ty=outerBox.y1+j;
      Block tb=pw.getBlock(outerBox.x1+i,outerBox.y1+j);
      if(tb!=null&&tb.light!=null) {
        // float mag=UtilMath.dist((tx+0.5f)*pw.blockWidth(),(ty+0.5f)*pw.blockHeight(),cx(),cy());
        cr+=tb.light.r();
        cg+=tb.light.g();
        cb+=tb.light.b();
      }else {
        cr+=16;
        cg+=16;
        cb+=16;
      }
    }
    int tr=(outerBox.w+1)*(outerBox.h+1);
    cr/=tr;
    cg/=tr;
    cb/=tr;
    light.set(cr,cg,cb);
    light.update();
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
    p.rect(x1(),y1()-4,type.w,2);
    p.fill(255,63,63,191);
    float tp=type.w/type.maxLife;
    float tw=tp*life.pos;
    p.rect(x1(),y1()-4,tw,2);
    if(flag) {
      if(life.des>life.pos) {
        p.fill(156,95,255,191);
        p.rect(x1()+tw,y1()-4,tp*(life.des-life.pos),2);
      }else {
        p.fill(255,255,63,191);
        p.rect(x1()+tw,y1()-4,tp*(life.pos-life.des),2);
      }
    }
    p.endBlend();
  }
  public float cx() {
    return point.pos.x+type.dx+type.w/2f;
  }
  public float cy() {
    return point.pos.y+type.dy+type.h/2f;
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
  public float x1() {
    return x()+type.dx;
  }
  public float y1() {
    return y()+type.dy;
  }
  public float x2() {
    return x()+type.dx+type.w-0.01f;//TODO
  }
  public float y2() {
    return y()+type.dy+type.h-0.01f;//TODO
  }
  public int blockX1() {
    return xToBlockCord(x1());
  }
  public int blockY1() {
    return yToBlockCord(y1());
  }
  public int blockX2() {
    return xToBlockCord(x2());
  }
  public int blockY2() {
    return yToBlockCord(y2());
  }
  public int xToBlockCord(float in) {
    // return UtilMath.floor(in/pw.blockWidth);
    return pw.xToBlockCord(in);
  }
  public int yToBlockCord(float in) {
    // return UtilMath.floor(in/pw.blockHeight);
    return pw.yToBlockCord(in);
  }
  public boolean inOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,outerBox.x1,outerBox.y1,outerBox.w,outerBox.h);
  }
  public boolean inInnerBox(float tx,float ty) {
    return Tools.inBox(tx,ty,x1(),y1(),type.w,type.h);
  }
}