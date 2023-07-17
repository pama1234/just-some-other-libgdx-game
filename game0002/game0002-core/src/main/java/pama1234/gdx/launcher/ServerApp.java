package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.app0001.Screen0004;

@SuppressWarnings("deprecation")
public class ServerApp extends Game{
  @Override
  public void create() {
    setScreen(new Screen0004());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}
