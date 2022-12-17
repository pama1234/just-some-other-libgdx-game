package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.util.wrapper.Center;

public class Region extends Center<Chunk> implements LoadAndSave{
  public RegionCenter p;
  public FileHandle dataLocation;
  //---
  public int chunkidth,chunkHeight;
  public Region(RegionCenter p,FileHandle dataLocation) {
    this.p=p;
    this.dataLocation=dataLocation;
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
}
