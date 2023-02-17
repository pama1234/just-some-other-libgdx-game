package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.KryoUtil;

public class WorldData{
  @Tag(0)
  public String dir;
  @Tag(1)
  public int time=12000;
  @Tag(2)
  public int tick;
  public static WorldData load(FileHandle file) {
    WorldData out=KryoUtil.load(WorldKryoUtil.kryo,file,WorldData.class);
    if(out!=null) return out;
    return new WorldData();
  }
  public static void save(FileHandle file,WorldData in) {
    KryoUtil.save(WorldKryoUtil.kryo,file,in);
  }
}