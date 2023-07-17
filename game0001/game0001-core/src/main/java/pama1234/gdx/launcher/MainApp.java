package pama1234.gdx.launcher;

import java.util.Arrays;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    screenList=Arrays.asList(null,
      Screen0011.class//游戏本体            <---------# √
    );
  }
}