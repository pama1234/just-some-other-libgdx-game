package pama1234.gdx.game.state.state0001.game.skill;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaSkill;

public class Skill{
  public MetaSkill type;
  @Tag(0)
  public int typeId;
}