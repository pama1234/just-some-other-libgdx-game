package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.entity.Creature;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.PointCenter;
import pama1234.math.physics.MassPoint;

public class Player2D extends Creature{
  public TextureRegion[][] tiles;
  public int pointer;
  public boolean dir,pdir;
  {
    w=20;
    h=24;
    dx=-10;
    dy=-24;
  }
  public Player2D(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,new MassPoint(x,y),pg);
    point.step=0.5f;
    this.pw=pw;
  }
  @Override
  public void init() {
    tiles=ImageAsset.player;
  }
  @Override
  public void update() {
    super.update();
    pointer=(int)(pg.time*6)%tiles.length;
    if(pdir!=dir) {
      for(TextureRegion[] i:tiles) i[0].flip(true,false);
      pdir=dir;
    }
  }
  @Override
  public void display() {
    super.display();
    p.image(tiles[pointer][0],point.pos.x+dx,point.pos.y+dy);
  }
  public static class PlayerCenter2D extends PointCenter<Screen0011,MassPoint,Player2D>{
    public PlayerCenter2D(Screen0011 p,float u) {
      super(p,u);
    }
    public PlayerCenter2D(Screen0011 p) {
      super(p);
    }
  }
}