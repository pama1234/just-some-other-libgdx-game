package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;

public class StartPage extends Page<RealGame0002,Welcome>{
  public StartPage(RealGame0002 p) {
    super(p,new Welcome(p,0,-120));
    content.refresh();
  }
  @Override
  public void show() {
    content.refresh();
  }
  @Override
  public void hide() {}
}