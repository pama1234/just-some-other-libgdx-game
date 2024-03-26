package pama1234.gdx.launcher;

import pama1234.gdx.game.app.App0001;
import pama1234.gdx.util.launcher.MainAppBase;

import java.util.ArrayList;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    screenClassList=new ArrayList<>();
    screenClassList.add(null);
    screenClassList.add(App0001.class);
  }
}