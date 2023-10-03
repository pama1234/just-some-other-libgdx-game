package pama1234.gdx.game.duel.util.theme;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.theme.ServerThemeData;
import pama1234.gdx.game.duel.Duel;

/**
 * 很丑，得改
 */
public class ThemeData{
  public Color shortbowArrow,longbowArrow,
    squareParticles,
    longbowLine,longbowEffect,longbowStroke,
    teleportEffect,teleportStroke,
    playerDamaged,
    ring,particleDefault,backgroundLine,
    player_a,player_b,neatVoidBackground,
    text,background,
    stroke;
  public ThemeData() {}
  public static ThemeData fromData(ServerThemeData in) {
    ThemeData out=new ThemeData();
    Yaml yaml=Duel.localization.yaml;
    String string=yaml.dumpAsMap(out);
    LinkedHashMap<String,Object> map=yaml.load(string);
    Set<Entry<String,Object>> entrySet=map.entrySet();
    for(Entry<String,Object> e:entrySet) {
      String inValue=in.data.get(e.getKey());
      e.setValue(yaml.dumpAsMap(fromStringColor(inValue)));
    }
    out=yaml.loadAs(yaml.dump(map).replace("|",""),ThemeData.class);
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
    teleportStroke=Duel.color(0,89,132);
    teleportEffect=Duel.color(0,132,196);
    longbowStroke=Duel.color(0,128);
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
  public ServerThemeData toData() {
    ServerThemeData out=new ServerThemeData();
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
    // System.out.println(map);
    out.data=(LinkedHashMap<String,String>)(Object)map;
    // System.out.println(out.data.toString());
    return out;
  }
}
