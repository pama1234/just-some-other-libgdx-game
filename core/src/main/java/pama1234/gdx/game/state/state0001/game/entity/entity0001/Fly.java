package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Fly extends TextureLivingEntity{
  public Fly(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,pw,x,y,pw.metaEntitys.fly,pg);
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
    }
    @Override
    public void init() {
      // if(tiles[0]!=null) return;
      // System.out.println("Fly.FlyType.init()");
      for(int i=0;i<tiles.length;i++) tiles[i][0]=ImageAsset.creature[6+i][2];
    }
  }
}
