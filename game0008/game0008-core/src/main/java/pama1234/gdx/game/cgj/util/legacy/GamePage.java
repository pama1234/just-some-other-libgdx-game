package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;

public class GamePage extends Page<RealGame0002,ParticleAutomata>{
  public GamePage(RealGame0002 p) {
    super(p,new ParticleAutomata(p));
  }
  @Override
  public void show() {
    // System.out.println("GamePage.show()");
    content.gameManager.show();
  }
  @Override
  public void hide() {
    // System.out.println("GamePage.hide()");
    content.gameManager.hide();
  }
}