package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.*;
import pama1234.gdx.game.app.app0001.*;
import pama1234.gdx.game.app.app0002.*;
import pama1234.gdx.game.app.app0003.*;
import pama1234.gdx.game.app.app0004.*;

public class MainApp extends Game{
  @Override
  public void create() {
    // setScreen(new Screen0012());
    setScreen(new Screen0011());//main game
    // setScreen(new Screen0010());
    // setScreen(new Screen0009());
    // setScreen(new Screen0008());
    // setScreen(new Screen0006());
    // setScreen(new Screen0005());
    // setScreen(new Screen0004());
    // setScreen(new Screen0003());
    // setScreen(new Screen0002());
    // setScreen(new Screen0001());
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}
