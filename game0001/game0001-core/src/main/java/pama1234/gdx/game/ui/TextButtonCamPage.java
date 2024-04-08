package pama1234.gdx.game.ui;

import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.wrapper.EntityCenter;

public class TextButtonCamPage<T extends UtilScreen2D>extends EntityCenter<T,TextButtonCam<T>>{
  public TextButtonCamPage(T p) {
    super(p);
  }
}