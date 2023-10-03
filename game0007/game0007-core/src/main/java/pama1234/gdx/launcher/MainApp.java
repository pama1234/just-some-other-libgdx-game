package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0037;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0037.class//box2d 填充正方形游戏
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}