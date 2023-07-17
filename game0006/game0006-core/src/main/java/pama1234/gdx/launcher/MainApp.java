package pama1234.gdx.launcher;

import java.util.Arrays;

import pama1234.gdx.game.app.app0005.Screen0022;
import pama1234.gdx.game.app.app0005.Screen0023;
import pama1234.gdx.game.app.app0005.Screen0024;
import pama1234.gdx.game.app.app0005.Screen0025;
import pama1234.gdx.game.app.app0005.Screen0026;
import pama1234.gdx.game.app.app0005.Screen0027;
import pama1234.gdx.game.app.app0005.Screen0029;
import pama1234.gdx.game.app.app0006.Screen0030;
import pama1234.gdx.game.app.app0006.Screen0031;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=9;
  }
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0022.class,//1 测试 Class轻量编译热部署工具
      Screen0023.class,//2 电脑端实时编译和运行工具
      Screen0024.class,//3 测试 Dex创建和生成工具
      Screen0025.class,//4 测试 Class转Dex工具
      Screen0026.class,//5 测试 javaparser
      Screen0027.class,//6 测试 运行gradle
      Screen0029.class,//7 测试 文本着色
      Screen0030.class,//8 测试 TextMate 
      Screen0031.class// 9 文本编辑器
    );
  }
}