package pama1234.gdx.launcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import pama1234.gdx.game.app.app0002.Screen0052;
import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=2;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0052.class,// 1
      Screen0055.class,// 2
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