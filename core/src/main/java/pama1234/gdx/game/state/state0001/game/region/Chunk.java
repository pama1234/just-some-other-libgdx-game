package pama1234.gdx.game.state.state0001.game.region;

import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Chunk{
  // public static final int CLEAN=0,MODIFIED=1,COMPLETELY=2;
  // public static final int UPDATE=0,STOPUPDATE=1;
  public Region p;
  public Block[][] data;
  // public int dataState=CLEAN;
  // public int updateState=UPDATE;
  public boolean update=true;
  public Chunk(Region p) {
    this.p=p;
  }
}