package pama1234.gdx.game.duel.util.skin;

import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.skin.ServerSkinData;
import pama1234.gdx.game.duel.Duel;

public class SkinData{
  public Color shortbowArrow,longbowArrow,
    squareParticles,
    longbowLine,longbowEffect,
    ring,particleDefault,background,
    player_a,player_b,neatVoidBackground;
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
  public ServerSkinData toData() {
    var out=new ServerSkinData();
    var yaml=Duel.localization.yaml;
    out.data=new LinkedHashMap<>();
    // out.data=yaml.load(yaml.dumpAsMap(this));
    LinkedHashMap<String,Object> cache=yaml.load(yaml.dumpAsMap(this));
    // System.out.println(out.data);
    for(var i:cache.entrySet()) {
      // String e=i.getKey();
      // System.out.println(e);
      // String ts=i.getValue().toString();
      // ColorTemp tc=yaml.loadAs(ts.substring(1,ts.length()-1),ColorTemp.class);
      ColorTemp tc=yaml.loadAs(i.getValue().toString(),ColorTemp.class);
      i.setValue(tc.toString());
      out.data.put(i.getKey(),i.getValue().toString());
    }
    System.out.println(out.data);
    return out;
  }
}
