package pama1234.gdx.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.util.localization.Localization;

public class MainApp extends Game{
  public String langType;
  public static final Localization localization=new Localization();
  // public static final String[] typeName=new String[] {"通用版","Taptap版","Pico4-VR版"};
  public static final String[] typeName=localization.readYaml("t1: 通用版\nt2: Taptap版\nt3: Pico4-VR版").data;
  public static final int defaultType=0,taptap=1,pico=2;
  public static int type;
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType=1;
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0011.class//游戏本体            <---------# √
    );
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