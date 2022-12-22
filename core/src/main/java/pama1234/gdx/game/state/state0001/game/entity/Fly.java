package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Fly extends TextureCreature{
  public Fly(Screen0011 p,World0001 pw,float x,float y,FlyType type,Game pg) {
    super(p,pw,x,y,type,pg);//TODO
  }
  // @Override
  // public void initTextureRegion() {}
  public static class FlyType extends MetaCreature<Fly>{
    public FlyType(MetaCreatureCenter pc) {
      super(pc,4,3);
    }
    @Override
    public void initTextureRegion() {
      if(tiles[0]==null) return;
      for(int i=0;i<tiles.length;i++) tiles[i]=ImageAsset.creature[6+i][2];//TODO
    }
  }
}
