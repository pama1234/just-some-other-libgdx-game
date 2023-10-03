package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.app.Screen0048;
import pama1234.gdx.game.app.Screen0049;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0011.class,// 1 游戏本体            <---------# √
      Screen0048.class,// 2 地球
      Screen0049.class,// 3 测试
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}