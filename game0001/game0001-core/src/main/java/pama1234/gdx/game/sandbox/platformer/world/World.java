package pama1234.gdx.game.sandbox.platformer.world;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithScreen;
import pama1234.gdx.util.wrapper.LayeredEntityCenter;

public abstract class World<T extends UtilScreen,G>extends LayeredEntityCenter<T>
  implements DisplayWithCam,DisplayWithScreen{
  public G pg;

  public FileHandle worldDataDir;
  public World(T p,G pg,int size) {
    super(p,size);
    this.pg=pg;
  }
}