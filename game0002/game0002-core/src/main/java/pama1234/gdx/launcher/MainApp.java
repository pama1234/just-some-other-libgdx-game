package pama1234.gdx.launcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.app0002.RealGame;
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
      // Screen0001.class,//3D 粒子系统 单机
      // Screen0002.class,//2D 粒子系统
      // Screen0003.class,//3D 粒子系统 联机 客户端
      // Screen0004.class,//粒子系统libgdx服务器
      // Screen0005.class,//贪吃蛇游戏本体 重制版
      // MainMenu.class,//6 菜单
      RealGame.class//7 贪吃蛇游戏本体 从Processing那边移植的
    );
  }
  @Override
  public void create() {
    try {
      Class<? extends UtilScreen> data=screenList.get(screenType);
      Constructor<?>[] constructors=data.getConstructors();
      UtilScreen out=null;
      for(Constructor<?> i:constructors) if(i.getParameterTypes().length==0) out=(UtilScreen)i.newInstance();
      if(out!=null) {
        setScreen(out);
        return;
      }
      for(Constructor<?> i:constructors) {
        Class<?>[] parameterTypes=i.getParameterTypes();
        // System.out.println(parameterTypes[0].getName());
        if(parameterTypes.length==1) {
          if(parameterTypes[0].getName().equals(getClass().getName())) out=(UtilScreen)i.newInstance(this);
          else out=(UtilScreen)i.newInstance(new Object[] {null});
        }
      }
      if(out!=null) {
        setScreen(out);
        return;
      }
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|SecurityException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}