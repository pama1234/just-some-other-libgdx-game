package pama1234.gdx.game.state.state0001.game.metainfo;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public abstract class MetaInfoBase{
  @Tag(0)
  public String name;
  @Tag(1)
  public int id;
  public MetaInfoBase(String name,int id) {
    this.name=name;
    this.id=id;
  }
  public abstract void init();
}
