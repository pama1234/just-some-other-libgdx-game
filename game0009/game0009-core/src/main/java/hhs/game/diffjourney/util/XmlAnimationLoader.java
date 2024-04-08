package hhs.game.diffjourney.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.XmlReader;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.AnimationSet;
import hhs.gdx.hsgame.tools.Resource;

public class XmlAnimationLoader{
  public static AnimationSet<Character.State,TextureRegion> getAnimationSet(String xml) {
    return getAnimationSet(Resource.xreader.parse(Gdx.files.internal(xml)));
  }
  public static AnimationSet<Character.State,TextureRegion> getAnimationSet(FileHandle xml) {
    return getAnimationSet(Resource.xreader.parse(xml));
  }
  public static AnimationSet<Character.State,TextureRegion> getAnimationSet(
    XmlReader.Element xml) {
    AnimationSet<Character.State,TextureRegion> set=new AnimationSet<>();
    Texture t;
    XmlReader.Element walk=xml.getChildByName("walk");
    t=Resource.asset.get(walk.get("texture"));
    set.set(Character.State.walk,AnimationBuilder.createAnim(t,walk.getFloat("time",.1f),
      walk.getInt("rows",1),walk.getInt("columns",1)));
    XmlReader.Element idle=xml.getChildByName("idle");
    t=Resource.asset.get(idle.get("texture"));
    set.set(Character.State.idle,AnimationBuilder.createAnim(t,idle.getFloat("time",.1f),
      idle.getInt("rows",1),idle.getInt("columns",1)));
    XmlReader.Element attack=xml.getChildByName("attack");
    t=Resource.asset.get(attack.get("texture"));
    set.set(Character.State.attack,AnimationBuilder.createAnim(t,attack.getFloat("time",.1f),
      attack.getInt("rows",1),attack.getInt("columns",1)));
    XmlReader.Element hurt=xml.getChildByName("hurt");
    t=Resource.asset.get(hurt.get("texture"));
    set.set(Character.State.hurt,AnimationBuilder.createAnim(t,hurt.getFloat("time",.1f),
      hurt.getInt("rows",1),hurt.getInt("columns",1)));
    XmlReader.Element death=xml.getChildByName("death");
    t=Resource.asset.get(death.get("texture"));
    set.set(Character.State.death,AnimationBuilder.createAnim(t,hurt.getFloat("time",.1f),
      hurt.getInt("rows",1),hurt.getInt("columns",1)));
    set.setPlayMode(Animation.PlayMode.LOOP);
    return set;
  }
}
