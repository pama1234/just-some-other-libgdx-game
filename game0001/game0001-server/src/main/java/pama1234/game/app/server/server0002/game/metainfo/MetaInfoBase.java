package pama1234.game.app.server.server0002.game.metainfo;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.game.app.server.server0002.game.metainfo.io.PlainAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.StoredAttribute;

public abstract class MetaInfoBase<A extends PlainAttribute,S extends StoredAttribute,R extends RuntimeAttribute>{

  /**
   * 用于yaml的序列化
   */
  public static class StoredInfo{

    public String name;
    public int id;
    public PlainAttribute attr;
    public StoredAttribute sttr;

    public StoredInfo(MetaInfoBase<?,?,?> in) {
      this.name=in.name;
      this.id=in.id;
      this.attr=in.attr;
      this.sttr=in.sttr;
    }
  }

  @Tag(0)
  public String name;
  @Tag(1)
  public int id;
  @Tag(2)
  public A attr;
  @Tag(3)
  public S sttr;
  public R rttr;
  public MetaInfoBase(String name,int id) {
    this.name=name;
    this.id=id;
  }
  public abstract void init();

  public abstract void loadRuntimeAttribute();
  public abstract void saveRuntimeAttribute();
}