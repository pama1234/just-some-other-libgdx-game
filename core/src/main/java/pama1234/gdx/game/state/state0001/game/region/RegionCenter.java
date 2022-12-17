package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.EntityCenter;

public class RegionCenter extends EntityCenter<Region> implements LoadAndSave{
  public World0001 pw;
  public FileHandle metadata;
  public int regionWidth=4,regionHeight=4;
  public RegionCenter(UtilScreen p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
}
