package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.PointCenter;
import pama1234.math.physics.MassPoint;

public class Player extends TextureLivingEntity{
  public Player(Screen0011 p,World0001 pw,float x,float y,PlayerType type) {
    super(p,pw,x,y,type);
    timeStep=1/2f;
  }
  public static class PlayerCenter extends PointCenter<Screen0011,MassPoint,Player>{
    public PlayerCenter(Screen0011 p,float u) {
      super(p,u);
    }
    public PlayerCenter(Screen0011 p) {
      super(p);
    }
  }
  public static class PlayerType extends MetaCreature<Player>{
    {
      //36*54 (old=20*24)
      w=36;
      h=54;
      dx=-w/2f;
      dy=-h;
    }
    public PlayerType(MetaCreatureCenter0001 pc,int id) {
      super(pc,"player",id,32,new TextureRegion[][] {new TextureRegion[4],new TextureRegion[8]});
    }
    @Override
    public void init() {
      for(int i=0;i<tiles.length;i++) for(int j=0;j<tiles[i].length;j++) tiles[i][j]=ImageAsset.player[j][i];
    }
  }
}