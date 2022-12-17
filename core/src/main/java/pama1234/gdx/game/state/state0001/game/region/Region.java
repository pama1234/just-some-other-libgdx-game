package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave{
  public RegionCenter pr;
  public FileHandle dataLocation;
  //---
  public int chunkWidth=64,chunkHeight=64;
  public Region(Screen0011 p,RegionCenter pr,FileHandle dataLocation) {
    super(p);
    this.pr=pr;
    this.dataLocation=dataLocation;
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
}
