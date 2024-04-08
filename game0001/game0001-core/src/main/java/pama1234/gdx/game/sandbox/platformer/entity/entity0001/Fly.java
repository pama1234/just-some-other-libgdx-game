package pama1234.gdx.game.sandbox.platformer.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.util.MovementLimitBox;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.player.Player;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.math.UtilMath;

public class Fly extends MobEntity{
  @Deprecated
  public Fly() {
    super();
  }
  public Fly(Screen0011 p,World0001 pw,float x,float y) {
    super(p,pw,x,y,pw.type.metaEntitys.fly);
    outerBox=limitBox=new MovementLimitBox(this);
    type.attr.count++;
  }
  @Override
  public void deserializationInit(Screen0011 p,WorldBase2D<? extends WorldType0001Base<?>> pw,MetaCreature<?> type) {
    super.deserializationInit(p,pw,type);
    outerBox=limitBox=new MovementLimitBox(this);
    type.attr.count++;
  }
  @Override
  public void update() {
    limitBox.preCtrlUpdate();
    if(target==null) findTarget();
    else playerAttract();
    super.update();
    if((point.vel.x>0)!=flipX) flipX=!flipX;
  }
  public void findTarget() {
    if(pw.yourself==null||!checkTarget(pw.yourself)) for(Player e:pw.entities.players.list) if(checkTarget(e)) break;
  }
  public boolean checkTarget(Player in) {//TODO
    if(UtilMath.dist(in.cx(),in.cy(),cx(),cy())<type.attr.intData[0]) {
      target=in;
      return true;
    }
    return false;
  }
  public void playerAttract() {
    float minVel=1.2f,maxVel=0.8f;
    float vx=(target.x()-x()),
      vy=(target.y()-y());
    float l=UtilMath.mag(vx,vy)/type.attr.moveSpeed;
    point.vel.set(vx/l*p.random(minVel,maxVel),vy/l*p.random(minVel,maxVel));//set or add
    float td=UtilMath.dist(target.x(),target.y(),x(),y());
    if(td<36) target.life.des-=0.1f;
  }
  public static class FlyType extends MetaCreature<Fly>{
    {
      attr.w=24;
      attr.h=24;
      attr.dx=-12;
      attr.dy=-24;
    }
    public FlyType(MetaCreatureCenter0001<?> pc,int id) {
      super(pc,"fly",id,4,3,1);
      attr.naturalMaxCount=4;
      attr.moveSpeed=4;
      rttr.spawnDatas=new SpawnData[] {new SpawnData(pc.pw.metaBlocks.air,0.01f)};

      attr.intData=new int[] {36*18};
    }
    @Override
    public void init() {
      for(int i=0;i<rttr.tiles[0].length;i++) rttr.tiles[0][i]=ImageAsset.creature[6+i][2];
    }
    @Override
    public Fly createCreature(World0001 world,float x,float y) {
      return new Fly(pc.pw.pc.pg.p,world,x,y);
    }
  }
}