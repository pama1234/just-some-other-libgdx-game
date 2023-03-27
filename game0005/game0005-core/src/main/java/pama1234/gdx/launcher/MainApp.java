package pama1234.gdx.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.Screen0019;
import pama1234.gdx.game.app.Screen0020;
import pama1234.gdx.game.app.app0002.Screen0004;
import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.app.app0002.Screen0006;
import pama1234.gdx.game.app.app0002.Screen0010;
import pama1234.gdx.game.app.app0002.Screen0014;
import pama1234.gdx.game.app.app0002.Screen0015;
import pama1234.gdx.game.app.app0003.Screen0008;
import pama1234.gdx.game.app.app0003.Screen0009;
import pama1234.gdx.game.app.app0003.Screen0013;
import pama1234.gdx.game.app.app0004.Screen0012;
import pama1234.gdx.game.app.app0004.Screen0016;
import pama1234.gdx.game.app.app0004.Screen0017;
import pama1234.gdx.game.app.app0004.Screen0018;
import pama1234.gdx.util.app.UtilScreen;

public class MainApp extends Game{
  public static final String[] typeName=new String[] {"通用版","Taptap版","Pico4-VR版"};
  public static final int defaultType=0,taptap=1,pico=2;
  public static int type;
  public List<Class<? extends UtilScreen>> screenList;
  public int screenType=20;
  public MainApp() {
    screenList=Arrays.asList(null,
      null,//几何决斗
      null,//12D塔防
      null,//3D 粒子系统 联机 客户端
      Screen0004.class,//测试 3D渲染 文本框UI
      Screen0005.class,//空白
      Screen0006.class,//测试 按钮UI
      null,//粒子系统libgdx服务器
      Screen0008.class,//测试 TextArea
      Screen0009.class,//测试 GIF加载
      Screen0010.class,//测试 天空着色器
      null,
      Screen0012.class,//空白游戏框架
      Screen0013.class,//测试 透明渲染模式
      Screen0014.class,//测试 贴图渲染优化
      Screen0015.class,//测试 贴图渲染优化
      Screen0016.class,//测试 联机游戏 客户端
      Screen0017.class,//某个音游项目
      Screen0018.class,//网络爬虫之类的
      Screen0019.class,//NEAT4J魔改版测试
      Screen0020.class//更多的NEAT4J魔改版测试
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