package pama1234.gdx.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.*;
import pama1234.gdx.game.app.app0001.*;
import pama1234.gdx.game.app.app0002.*;
import pama1234.gdx.game.app.app0003.*;
import pama1234.gdx.game.app.app0004.*;
import pama1234.gdx.util.app.UtilScreen;

public class MainApp extends Game{
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType=10;
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0001.class,//3D 粒子系统 单机
      Screen0002.class,//2D 粒子系统
      Screen0003.class,//3D 粒子系统 联机 客户端
      Screen0004.class,//测试 3D渲染 文本框UI
      Screen0005.class,//测试 天空着色器
      Screen0006.class,//测试 按钮UI
      Screen0007.class,//粒子系统libgdx服务器
      Screen0008.class,//TextArea test
      Screen0009.class,//测试 GIF加载
      Screen0010.class,//测试 天空着色器
      Screen0011.class,//main game
      Screen0012.class,//空白游戏框架
      Screen0013.class//测试 透明渲染模式
    );
  }
  @Override
  public void create() {
    try {
      setScreen(screenList.get(screenType).getDeclaredConstructor().newInstance());
    }catch(InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}