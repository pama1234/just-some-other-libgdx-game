package pama1234.gdx.launcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0045.class//  1 贪吃蛇游戏本体 从Processing那边移植的
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
  @Override
  public UtilScreen newScreen() {
    try {
      Class<? extends UtilScreen> data=screenClassList.get(screenType);
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