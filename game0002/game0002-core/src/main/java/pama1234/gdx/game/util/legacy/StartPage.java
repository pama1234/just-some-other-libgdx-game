package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;

public class StartPage extends Page<RealGame,Welcome>{
  public StartPage(RealGame p) {
    super(p,"初始界面",new Welcome(p,0,-120));
    content.refresh();
  }
  @Override
  public void show() {
    content.refresh();
  }
  @Override
  public void hide() {}
}