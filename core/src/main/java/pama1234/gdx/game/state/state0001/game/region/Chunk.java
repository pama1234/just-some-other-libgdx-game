package pama1234.gdx.game.state.state0001.game.region;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Chunk{
  // public static final int CLEAN=0,MODIFIED=1,COMPLETELY=2;
  // public static final int UPDATE=0,STOPUPDATE=1;
  public Region p;
  @Tag(0)
  public Block[][] data;
  // public int dataState=CLEAN;
  // public int updateState=UPDATE;
  public boolean update=true;
  @Deprecated
  public Chunk() {//只能用于kryo
  }
  public Chunk(Region p) {
    innerInit(p);
  }
  public void innerInit(Region p) {
    this.p=p;
  }
}