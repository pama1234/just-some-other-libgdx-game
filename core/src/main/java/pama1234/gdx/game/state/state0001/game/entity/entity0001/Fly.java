package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Fly extends TextureLivingEntity{
  public Fly(Screen0011 p,World0001 pw,float x,float y) {
    super(p,pw,x,y,pw.metaEntitys.fly);
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
      spawnDatas=new SpawnData[] {new SpawnData(pc.pw.metaBlocks.air,1f)};
    }
    @Override
    public void init() {
      for(int i=0;i<tiles[0].length;i++) tiles[0][i]=ImageAsset.creature[6+i][2];
    }
    @Override
    public Fly createCreature(float x,float y) {
      count++;
      pc.pw.p.println(x,y);
      return new Fly(pc.pw.p,pc.pw,x,y);
    }
  }
}