package pama1234.gdx.game.state.state0001.game.region;

import java.util.LinkedList;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Chunk{
  public Region p;
  @Tag(0)
  public BlockData[][] data;
  public int biome;
  public LinkedList<LivingEntity> entityList;//TODO
  public boolean update=true;
  @Deprecated
  public Chunk() {}//只能用于kryo
  public Chunk(Region p) {
    innerInit(p);
  }
  public void innerInit(Region p) {
    this.p=p;
  }
  public static class BlockData{
    @Tag(0)
    public Block block;
    // public Block[] blockArray;
    // public Block blockEnd;
    // public int xOff,yOff;
    // public int biome;
    @Deprecated
    public BlockData() {}//只能用于kryo
    public BlockData(Block block) {
      this.block=block;
    }
  }
}