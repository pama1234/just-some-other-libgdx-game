package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.app0001.Screen0001;
import pama1234.gdx.game.app.app0001.Screen0002;
import pama1234.gdx.game.app.app0001.Screen0003;
import pama1234.gdx.game.app.app0001.Screen0004;
import pama1234.gdx.game.app.app0002.MainMenu;
import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.app.app0002.Screen0047;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=6;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      Screen0001.class,//1 3D 粒子系统 单机
      Screen0002.class,//2 2D 粒子系统
      Screen0003.class,//3 3D 粒子系统 联机 客户端
      Screen0004.class,//4 粒子系统libgdx服务器
      Screen0005.class,//5 贪吃蛇游戏本体 重制版
      MainMenu.class,//  6 菜单
      Screen0047.class,//7 JCOL
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}