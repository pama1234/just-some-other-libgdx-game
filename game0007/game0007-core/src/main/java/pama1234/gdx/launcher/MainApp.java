package pama1234.gdx.launcher;

import java.util.Arrays;

import pama1234.gdx.game.app.Screen0037;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0037.class//box2d 填充正方形游戏
    );
  }
}