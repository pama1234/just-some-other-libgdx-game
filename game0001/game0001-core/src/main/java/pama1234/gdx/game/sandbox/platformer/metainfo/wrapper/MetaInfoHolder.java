package pama1234.gdx.game.sandbox.platformer.metainfo.wrapper;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;

/**
 * 用于序列化
 */
public class MetaInfoHolder<E extends MetaInfoBase<?,?,?>>{
  @Tag(0)
  public int id;
  public E d;
}
