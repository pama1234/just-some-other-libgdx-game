package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;

public class SettingsPage extends Page<RealGame,Settings>{
  public SettingsPage(RealGame p) {
    super(p,"  设置  ",new Settings(p));
  }
  @Override
  public void show() {}
  @Override
  public void hide() {}
}
