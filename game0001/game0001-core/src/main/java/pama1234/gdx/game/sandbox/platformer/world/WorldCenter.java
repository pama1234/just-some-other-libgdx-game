package pama1234.gdx.game.sandbox.platformer.world;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;

public class WorldCenter<T extends UtilScreen,G,E extends World<T,G>>extends StateCenter<T,E> implements DisplayWithCam{
  public WorldCenter(T p,E in) {
    super(p,in);
  }
  public WorldCenter(T p,E[] in) {
    super(p,in);
  }
  public WorldCenter(T p) {
    super(p);
  }
  @Override
  public void displayCam() {
    list.get(pointer).displayCam();
  }
}