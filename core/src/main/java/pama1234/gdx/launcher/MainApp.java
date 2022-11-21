package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.Screen0003;

public class MainApp extends Game{
  @Override
  public void create() {
    // setScreen(new Screen0005());
    // setScreen(new Screen0004());
    setScreen(new Screen0003());
    // setScreen(new Screen0002());
    // setScreen(new Screen0001());
    // setScreen(new Screen9999());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}
