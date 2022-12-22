package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Fly extends TextureCreature{
  public Fly(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,pw,x,y,pg);
  }
  @Override
  public void initTextureRegion() {}
}
