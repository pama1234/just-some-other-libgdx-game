package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.util.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.LayeredEntityCenter;

public abstract class World<T extends UtilScreen,G>extends LayeredEntityCenter<T> implements DisplayWithCam{
  public G pg;
  //---
  public FileHandle worldDataDir;
  public World(T p,G pg,int size) {
    super(p,size);
    this.pg=pg;
  }
}