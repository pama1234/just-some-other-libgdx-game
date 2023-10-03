package hhs.game.diffjourney.screens;

import hhs.game.diffjourney.ui.UiList;
import hhs.gdx.hsgame.screens.BasicScreen;

public class GameScreen extends BasicScreen{
  @Override
  public void show() {
    super.show();
    //setDebug(true);
    timeAcceleration=1;
    UiList.getBasicUi(this);
    // TODO: Implement this method
  }
}
