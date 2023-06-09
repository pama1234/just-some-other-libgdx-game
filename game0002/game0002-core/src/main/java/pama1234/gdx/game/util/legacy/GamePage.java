package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;

public class GamePage extends Page<RealGame,ParticleAutomata>{
  public GamePage(RealGame p) {
    super(p,"开始",new ParticleAutomata(p));
  }
  @Override
  public void show() {}
  @Override
  public void hide() {}
}
