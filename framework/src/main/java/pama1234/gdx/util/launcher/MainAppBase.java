package pama1234.gdx.util.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import pama1234.gdx.util.SharedResources;
import pama1234.gdx.util.app.UtilScreen;

public class MainAppBase extends Game{
  public static final String[] typeName=new String[] {"通用版","Taptap版","Pico4-VR版"};
  public static final int defaultType=0,taptap=1,pico=2;
  public static MainAppBase instance;
  public static int type;
  public ArrayList<Class<? extends UtilScreen>> screenClassList;
  public ArrayList<UtilScreen> screenList;
  public int screenType;

  public UtilScreen uScreen;
  {
    instance=this;
  }
  @Override
  public void create() {
    setScreen(newScreen());
  }
  public UtilScreen newScreen() {
    return newScreen(false);
  }
  public UtilScreen newScreen(boolean createNew) {
    return newScreen(screenType,createNew);
  }
  public UtilScreen newScreen(int in) {
    return newScreen(in,false);
  }
  public UtilScreen newScreen(int in,boolean createNew) {
    if(screenList==null) {
      screenList=new ArrayList<>(screenClassList.size());
      for(int i=0;i<screenClassList.size();i++) {
        screenList.add(i,null);
      }
    }
    try {
      var out=screenList.get(in);
      if(createNew||screenList.get(in)==null) {
        out=screenClassList.get(in).getDeclaredConstructor().newInstance();
        screenList.set(in,out);
      }
      return out;
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      // e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
    SharedResources.instance.dispose();
  }
  public void restartScreen() {
    screen.hide();
    screen.dispose();
    setScreen(newScreen(true));
  }
  @Override
  public void setScreen(Screen screen) {
    super.setScreen(screen);
    if(screen instanceof UtilScreen e) uScreen=e;
  }
  public void focusGained() {
    uScreen.focusGainedOuter();
  }
  public void focusLost() {
    uScreen.focusLostOuter();
  }
}