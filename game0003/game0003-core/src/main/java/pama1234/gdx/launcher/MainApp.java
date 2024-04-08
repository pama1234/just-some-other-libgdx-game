package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0021;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Duel.class,//几何决斗
      Screen0021.class//着色器测试
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}