package pama1234.gdx.game.duel.util.skin;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.skin.ServerSkinData;
import pama1234.gdx.game.duel.Duel;

/**
 * TODO 丑，得改
 */
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
  public ServerSkinData toServerSkinData() {
    return toServerSkinData(new ServerSkinData());
  }
  public ServerSkinData toServerSkinData(ServerSkinData in) {
    in.shortbowArrow=Integer.toHexString(shortbowArrow.toIntBits());
    in.longbowArrow=Integer.toHexString(longbowArrow.toIntBits());
    in.squareParticles=Integer.toHexString(squareParticles.toIntBits());
    in.longbowLine=Integer.toHexString(longbowLine.toIntBits());
    in.longbowEffect=Integer.toHexString(longbowEffect.toIntBits());
    in.ring=Integer.toHexString(ring.toIntBits());
    in.particleDefault=Integer.toHexString(particleDefault.toIntBits());
    in.background=Integer.toHexString(background.toIntBits());
    in.player_a=Integer.toHexString(player_a.toIntBits());
    in.player_b=Integer.toHexString(player_b.toIntBits());
    in.neatVoidBackground=Integer.toHexString(neatVoidBackground.toIntBits());
    return in;
  }
  public SkinData fromServerSkinData(ServerSkinData in) {
    shortbowArrow=new Color((int)Long.parseLong(in.shortbowArrow,16));
    longbowArrow=new Color((int)Long.parseLong(in.longbowArrow,16));
    squareParticles=new Color((int)Long.parseLong(in.squareParticles,16));
    longbowLine=new Color((int)Long.parseLong(in.longbowLine,16));
    longbowEffect=new Color((int)Long.parseLong(in.longbowEffect,16));
    ring=new Color((int)Long.parseLong(in.ring,16));
    particleDefault=new Color((int)Long.parseLong(in.particleDefault,16));
    background=new Color((int)Long.parseLong(in.background,16));
    player_a=new Color((int)Long.parseLong(in.player_a,16));
    player_b=new Color((int)Long.parseLong(in.player_b,16));
    neatVoidBackground=new Color((int)Long.parseLong(in.neatVoidBackground,16));
    return this;
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
