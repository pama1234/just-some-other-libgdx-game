package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

public class ServerApp extends Game{
  @Override
  public void create() {
    // setScreen(new Screen0022());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}