package pama1234.gdx.game.duel.util.skin;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.skin.ServerSkinData;
import pama1234.gdx.game.duel.Duel;

/**
 * 很丑，得改
 */
public class SkinData{
  public Color shortbowArrow,longbowArrow,
    squareParticles,
    longbowLine,longbowEffect,playerDamaged,
    ring,particleDefault,backgroundLine,
    player_a,player_b,neatVoidBackground,
    text,background,
    stroke;
  public SkinData() {}
  public static SkinData fromData(ServerSkinData in) {
    SkinData out=new SkinData();
    Yaml yaml=Duel.localization.yaml;
    String string=yaml.dumpAsMap(out);
    LinkedHashMap<String,Object> map=yaml.load(string);
    Set<Entry<String,Object>> entrySet=map.entrySet();
    for(Entry<String,Object> e:entrySet) {
      String inValue=in.data.get(e.getKey());
      e.setValue(yaml.dumpAsMap(fromStringColor(inValue)));
    }
    out=yaml.loadAs(yaml.dump(map).replace("|",""),SkinData.class);
    return out;
  }
  public static Color fromStringColor(String in) {
    Long decode=Long.decode(in);
    int intValue=(int)(decode.longValue()&0xffffffff);
    return new Color(intValue);
  }
  public void init() {
    shortbowArrow=Duel.color(192);
    longbowArrow=Duel.color(64);
    squareParticles=Duel.color(0);
    longbowLine=Duel.color(192,64,64);
    longbowEffect=Duel.color(192,64,64);
    playerDamaged=Duel.color(192,64,64);
    ring=Duel.color(0);
    particleDefault=Duel.color(0);
    backgroundLine=Duel.color(224);
    player_a=Duel.color(255);
    player_b=Duel.color(0);
    neatVoidBackground=Duel.color(191);
    text=Duel.color(0);
    background=Duel.color(255);
    stroke=Duel.color(0);
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
      ColorData tc=new ColorData();
      tc.load(value);
      e.setValue(tc.toString());
    }
    System.out.println(map);
    out.data=(LinkedHashMap<String,String>)(Object)map;
    System.out.println(out.data.toString());
    return out;
  }
}
