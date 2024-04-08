package pama1234.gdx.game.sandbox.platformer.skill;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaSkill;

public class Skill{
  public MetaSkill type;
  @Tag(0)
  public int typeId;
}