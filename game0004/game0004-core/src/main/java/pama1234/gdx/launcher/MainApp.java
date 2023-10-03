package pama1234.gdx.launcher;

import java.util.ArrayList;

import pama1234.gdx.game.app.app0001.Screen0032;
import pama1234.gdx.game.app.app0001.Screen0040;
import pama1234.gdx.game.app.app0001.Screen0041;
import pama1234.gdx.game.app.app0001.Screen0043;
import pama1234.gdx.game.app.app0001.Screen0044;
import pama1234.gdx.game.app.app0002.Screen0042;
import pama1234.gdx.game.dimensional.tower.defense.DemonDefense;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=5;
  }
  public MainApp() {
    var classArray=new Class[] {null,
      DemonDefense.class,//1 12D塔防
      Screen0032.class,//  2 测试3D渲染
      Screen0040.class,//  3 测试3D按钮
      Screen0041.class,//  4 测试文件编辑器
      Screen0042.class,//  5 Screen菜单测试
      Screen0043.class,//  6 实体调试工具
      Screen0044.class,//  7 实体调试工具
    };
    screenClassList=new ArrayList<>(classArray.length);
    for(int i=0;i<classArray.length;i++) {
      screenClassList.add(i,classArray[i]);
    }
  }
}