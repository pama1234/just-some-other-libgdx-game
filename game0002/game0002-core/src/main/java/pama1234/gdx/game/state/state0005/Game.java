package pama1234.gdx.game.state.state0005;

import pama1234.gdx.game.state.state0005.State0005Util.StateEntity0005;
import pama1234.gdx.game.util.ParticleScreen3D;
import pama1234.gdx.game.world.World0001;
import pama1234.gdx.util.wrapper.DisplayEntity;

public class Game extends StateEntity0005{
  public World0001 world;
  public Game(ParticleScreen3D p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    super.init();
    world=new World0001(p);
    world.init();
  }
  @Override
  public void display() {
    super.display();
  }
  @Override
  public void update() {
    super.update();
  }
  @Override
  public void from(StateEntity0005 in) {
    super.from(in);
    p.centerScreen.add.add(world);
    p.centerCam.add.add(new DisplayEntity(world::displayCam));
  }
  @Override
  public void to(StateEntity0005 in) {
    super.to(in);
  }
}
