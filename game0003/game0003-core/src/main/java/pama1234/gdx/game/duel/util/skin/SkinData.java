package pama1234.gdx.game.duel.util.skin;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.skin.ServerSkinData;
import pama1234.gdx.game.duel.Duel;

public class SkinData{
  public Color shortbowArrow,longbowArrow,
    squareParticles,
    longbowLine,longbowEffect,
    ring,particleDefault,background,
    player_a,player_b,neatVoidBackground;
  public SkinData() {}
  public static SkinData fromData(ServerSkinData in) {
    SkinData out=new SkinData();
    Yaml yaml=Duel.localization.yaml;
    String string=yaml.dumpAsMap(out);
    // System.out.println(string);
    LinkedHashMap<String,Object> map=yaml.load(string);
    Set<Entry<String,Object>> entrySet=map.entrySet();
    for(Entry<String,Object> e:entrySet) {
      String inValue=in.data.get(e.getKey());
      e.setValue(fromStringColor(inValue));
    }
    return out;
  }
  public static Color fromStringColor(String in) {
    return new Color(Long.decode(in).intValue());
  }
  public void init() {
    shortbowArrow=Duel.color(192);
    longbowArrow=Duel.color(64);
    squareParticles=Duel.color(0);
    longbowLine=Duel.color(192,64,64);
    longbowEffect=Duel.color(192,64,64);
    ring=Duel.color(0);
    particleDefault=Duel.color(0);
    background=Duel.color(224);
    player_a=Duel.color(255);
    player_b=Duel.color(0);
    neatVoidBackground=Duel.color(191);
  }
  /**
   * 强制转换之恶魔
   * 
   * @return
   */
  public ServerSkinData toData() {
    ServerSkinData out=new ServerSkinData();
    Yaml yaml=Duel.localization.yaml;
    String string=yaml.dumpAsMap(this);
    LinkedHashMap<String,Object> map=yaml.load(string);
    Set<Entry<String,Object>> entrySet=map.entrySet();
    for(Entry<String,Object> e:entrySet) {
      LinkedHashMap<String,Object> value=(LinkedHashMap<String,Object>)e.getValue();
      // System.out.println(value);
      ColorTemp tc=new ColorTemp();
      tc.load(value);
      e.setValue(tc.toString());
    }
    System.out.println(map);
    out.data=(LinkedHashMap<String,String>)(Object)map;
    System.out.println(out.data.toString());
    return out;
  }
}
