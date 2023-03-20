package pama1234.gdx.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.app0001.Screen0001;
import pama1234.gdx.game.app.app0001.Screen0002;
import pama1234.gdx.game.app.app0001.Screen0003;
import pama1234.gdx.game.app.app0001.Screen0007;
import pama1234.gdx.util.app.UtilScreen;

@SuppressWarnings("deprecation")
public class MainApp extends Game{
  public static final String[] typeName=new String[] {"通用版","Taptap版","Pico4-VR版"};
  public static final int defaultType=0,taptap=1,pico=2;
  public static int type;
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType=1;
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0001.class,//3D 粒子系统 单机
      Screen0002.class,//2D 粒子系统
      Screen0003.class,//3D 粒子系统 联机 客户端
      null,
      null,
      null,
      Screen0007.class//粒子系统libgdx服务器
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