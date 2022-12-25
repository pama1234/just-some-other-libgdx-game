package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.PointCenter;
import pama1234.math.physics.MassPoint;

public class Player2D extends TextureLivingEntity{
  public Player2D(Screen0011 p,World0001 pw,float x,float y,PlayerType2D type,Game pg) {
    super(p,pw,x,y,type,pg);
  }
  public static class PlayerCenter2D extends PointCenter<Screen0011,MassPoint,Player2D>{
    public PlayerCenter2D(Screen0011 p,float u) {
      super(p,u);
    }
    public PlayerCenter2D(Screen0011 p) {
      super(p);
    }
  }
  public static class PlayerType2D extends MetaCreature<Player2D>{
    {
      w=20;
      h=24;
      dx=-10;
      dy=-24;
    }
    public PlayerType2D(MetaCreatureCenter0001 pc) {
      super(pc,32,4);
    }
    @Override
    public void init() {
      if(tiles[0]==null) for(int i=0;i<tiles.length;i++) tiles[i]=ImageAsset.player[i][0];
    }
  }
}