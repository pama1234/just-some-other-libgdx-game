package pama1234.gdx.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.*;
import pama1234.gdx.game.app.app0001.*;
import pama1234.gdx.game.app.app0002.*;
import pama1234.gdx.game.app.app0003.*;
import pama1234.gdx.game.app.app0004.*;
import pama1234.gdx.util.app.UtilScreen;

public class MainApp extends Game{
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType=8;
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0001.class,//particle system
      Screen0002.class,
      Screen0003.class,
      Screen0004.class,
      Screen0005.class,
      Screen0006.class,
      Screen0007.class,
      Screen0008.class,//TextArea test
      Screen0009.class,
      Screen0010.class,
      Screen0011.class,//main game
      Screen0012.class,
      Screen0013.class);
  }
  @Override
  public void create() {
    try {
      setScreen(screenList.get(screenType).getDeclaredConstructor().newInstance());
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}