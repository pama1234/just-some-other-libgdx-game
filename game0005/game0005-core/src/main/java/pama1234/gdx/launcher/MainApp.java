package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0019;
import pama1234.gdx.game.app.Screen0028;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0028.class,//空项目
      Screen0019.class
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}