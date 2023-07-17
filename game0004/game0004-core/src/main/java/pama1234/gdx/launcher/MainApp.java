package pama1234.gdx.launcher;

import java.util.Arrays;

import pama1234.gdx.game.dimensional.tower.defense.DemonDefense;
import pama1234.gdx.util.launcher.MainAppBase;

public class MainApp extends MainAppBase{
  {
    screenType=1;
  }
  public MainApp() {
    screenList=Arrays.asList(null,
      DemonDefense.class//12D塔防
    );
  }
}