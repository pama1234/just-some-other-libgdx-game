package pama1234.gdx.game.state.state0001.game.region;

import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Chunk{
  // public static final int CLEAN=0,MODIFIED=1,COMPLETELY=2;
  public Region p;
  public Block[][] data;
  // public int dataState=CLEAN;
  public Chunk(Region p) {
    this.p=p;
  }
}