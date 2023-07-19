package pama1234.gdx.game.cide.state.state0003.firstRun;

import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.game.cide.util.graphics.TextBodyEntity;
import pama1234.gdx.util.wrapper.EntityCenter;

public class FirstRunDisplay0002 extends FirstRunDisplayBase{
  public TextBodyEntityCenter bodyCenter;
  public FirstRunDisplay0002(ScreenCide2D p,FirstRun pf) {
    super(p,pf);
    bodyCenter=new TextBodyEntityCenter(p);
  }
  public static class TextBodyEntityCenter extends EntityCenter<ScreenCide2D,TextBodyEntity<ScreenCide2D>>{
    public TextBodyEntityCenter(ScreenCide2D p) {
      super(p);
    }
  }
}
