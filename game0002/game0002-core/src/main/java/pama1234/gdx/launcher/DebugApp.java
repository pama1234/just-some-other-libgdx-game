package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.app0001.Screen0001;

public class DebugApp extends Game{
  @Override
  public void create() {
    setScreen(new Screen0001(null));
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}