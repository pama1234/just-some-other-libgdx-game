package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.cgj.app.app0001.Screen0036;

@SuppressWarnings("deprecation")
public class ServerApp0002 extends Game{
  @Override
  public void create() {
    setScreen(new Screen0036());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}
