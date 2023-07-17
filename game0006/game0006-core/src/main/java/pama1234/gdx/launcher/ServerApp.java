package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.app0005.Screen0022;

public class ServerApp extends Game{
  @Override
  public void create() {
    setScreen(new Screen0022());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}