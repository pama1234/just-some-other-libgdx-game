package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.Screen0007;

public class MainApp0002 extends Game{
  @Override
  public void create() {
    setScreen(new Screen0007());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}
