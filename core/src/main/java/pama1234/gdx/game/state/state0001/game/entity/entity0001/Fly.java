package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.UtilMath;

public class Fly extends MobEntity{
  public MovementLimitBox limitBox;
  public Fly(Screen0011 p,World0001 pw,float x,float y) {
    super(p,pw,x,y,pw.metaEntitys.fly);
    limitBox=new MovementLimitBox(this);
  }
  @Override
  public void update() {
    limitBox.update();
    limitBox.updateLimit();
    limitBox.doInAirTest();
    super.update();
    limitBox.constrain();
    playerAttract();
    if((point.vel.x>0)!=flipX) flipX=!flipX;
  }
  public void playerAttract() {
    if(target==null) return;
    float minVel=1.2f,maxVel=0.8f;
    float vx=(target.x()-x()),
      vy=(target.y()-y());
    float l=UtilMath.mag(vx,vy)/type.moveSpeed;
    point.vel.set(vx/l*p.random(minVel,maxVel),vy/l*p.random(minVel,maxVel));//set or add
    float td=UtilMath.dist(target.x(),target.y(),x(),y());
    if(td<36) target.life.des-=0.1f;
  }
  public static class FlyType extends MetaCreature<Fly>{
    {
      w=24;
      h=24;
      dx=-12;
      dy=-24;
    }
    public FlyType(MetaCreatureCenter0001 pc,int id) {
      super(pc,"fly",id,4,3,1);
      naturalMaxCount=4;
      moveSpeed=4;
      spawnDatas=new SpawnData[] {new SpawnData(pc.pw.metaBlocks.air,0.01f)};
    }
    @Override
    public void init() {
      for(int i=0;i<tiles[0].length;i++) tiles[0][i]=ImageAsset.creature[6+i][2];
    }
    @Override
    public Fly createCreature(float x,float y) {
      count++;
      return new Fly(pc.pw.p,pc.pw,x,y);
    }
  }
}