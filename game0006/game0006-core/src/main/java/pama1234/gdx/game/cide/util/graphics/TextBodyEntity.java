package pama1234.gdx.game.cide.util.graphics;

import org.jbox2d.dynamics.Body;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.box2d.BodyEntity;

public class TextBodyEntity<T extends UtilScreen>extends BodyEntity<T>{
  public TextBodyEntity(T p,Body body) {
    super(p,body);
  }
}
