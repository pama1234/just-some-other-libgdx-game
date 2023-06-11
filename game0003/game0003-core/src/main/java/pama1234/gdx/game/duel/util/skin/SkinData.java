package pama1234.gdx.game.duel.util.skin;

import com.badlogic.gdx.graphics.Color;

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
  public int[] toIntArray() {
    Color[] cache=new Color[] {shortbowArrow,longbowArrow,
      squareParticles,
      longbowLine,longbowEffect,
      ring,particleDefault,background,
      player_a,player_b,neatVoidBackground};
    int[] out=new int[cache.length];
    for(int i=0;i<cache.length;i++) {
      out[i]=cache[i].toIntBits();
    }
    return out;
  }
  public void fromIntArray(int[] in) {
    shortbowArrow=new Color(in[0]);
    longbowArrow=new Color(in[1]);
    squareParticles=new Color(in[2]);
    longbowLine=new Color(in[3]);
    longbowEffect=new Color(in[4]);
    ring=new Color(in[5]);
    particleDefault=new Color(in[6]);
    background=new Color(in[7]);
    player_a=new Color(in[8]);
    player_b=new Color(in[9]);
    neatVoidBackground=new Color(in[10]);
  }
}
