package pama1234.gdx.launcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import pama1234.gdx.game.cgj.app.app0001.Screen0033;
import pama1234.gdx.game.cgj.app.app0001.Screen0034;
import pama1234.gdx.game.cgj.app.app0001.Screen0035;
import pama1234.gdx.game.cgj.app.app0002.MainMenu;
import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.app.app0002.Screen0036;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=6;
  }
  public MainApp() {
    // CGJ48小时游戏比赛特供！
    screenList=Arrays.asList(null,
      Screen0033.class,//1 3D 粒子系统 单机
      Screen0034.class,//2 2D 粒子系统
      Screen0035.class,//3 3D 粒子系统 联机 客户端
      Screen0036.class,//4 贪吃蛇游戏本体 重制版
      MainMenu.class,//  5 菜单
      RealGame0002.class//   6 贪吃蛇游戏本体 从Processing那边移植的
    );
  }
  @Override
  public UtilScreen newScreen() {
    try {
      Class<? extends UtilScreen> data=screenList.get(screenType);
      Constructor<?>[] constructors=data.getConstructors();
      UtilScreen out=null;
      for(Constructor<?> i:constructors) if(i.getParameterTypes().length==0) out=(UtilScreen)i.newInstance();
      if(out!=null) {
        return out;
      }
      for(Constructor<?> i:constructors) {
        Class<?>[] parameterTypes=i.getParameterTypes();
        if(parameterTypes.length==1) {
          if(parameterTypes[0].getName().equals(getClass().getName())) out=(UtilScreen)i.newInstance(this);
          else out=(UtilScreen)i.newInstance(new Object[] {null});
        }
      }
      if(out!=null) {
        return out;
      }
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|SecurityException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}