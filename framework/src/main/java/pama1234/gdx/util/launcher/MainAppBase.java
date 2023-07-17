package pama1234.gdx.util.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.util.app.UtilScreen;

public class MainAppBase extends Game{
  public static final String[] typeName=new String[] {"通用版","Taptap版","Pico4-VR版"};
  public static final int defaultType=0,taptap=1,pico=2;
  public static MainAppBase instance;
  public static int type;
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType;
  {
    instance=this;
  }
  @Override
  public void create() {
    setScreen(newScreen());
  }
  public UtilScreen newScreen() {
    try {
      return screenList.get(screenType).getDeclaredConstructor().newInstance();
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      // e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
  public void restartScreen() {
    screen.hide();
    screen.dispose();
    setScreen(newScreen());
  }
}