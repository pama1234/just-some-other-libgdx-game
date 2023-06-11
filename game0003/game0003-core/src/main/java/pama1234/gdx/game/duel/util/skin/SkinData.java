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
}
