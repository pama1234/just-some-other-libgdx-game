package hhs.game.diffjourney.screens;

import hhs.game.diffjourney.ui.UiList;
import hhs.gdx.hsgame.screens.LayersScreen;

public class GameScreen extends LayersScreen{
  @Override
  public void show() {
    super.show();
    //setDebug(true);
    timeAcceleration=1;
    UiList.getBasicUi(this);
  }
}
