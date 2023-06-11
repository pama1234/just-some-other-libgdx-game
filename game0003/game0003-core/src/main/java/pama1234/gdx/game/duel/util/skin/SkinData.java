package pama1234.gdx.game.duel.util.skin;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;

public class SkinData{
  public Color shortbowArrow,longbowArrow,squareParticles,longbowLine,longbowEffect;
  public void init() {
    shortbowArrow=Duel.color(192);
    longbowArrow=Duel.color(64);
    squareParticles=Duel.color(0);
    longbowLine=Duel.color(192,64,64);
    longbowEffect=Duel.color(192,64,64);
  }
}
