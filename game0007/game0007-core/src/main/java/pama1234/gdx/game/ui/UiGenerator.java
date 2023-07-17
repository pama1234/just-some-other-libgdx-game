package pama1234.gdx.game.ui;

import pama1234.gdx.game.app.Screen0037;
import pama1234.gdx.game.ui.util.TextButton;

public class UiGenerator{
  public static TextButton<?>[] genButtons_0005(Screen0037 p) {
    return new TextButton[] {
      new TextButton<Screen0037>(p,true,()->true,self-> {},self-> {
        p.reset();
      },self-> {},self->self.text="重置",p::getButtonUnitLength,()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*0.5f),()->p.bu-p.pus,true),
    };
  }
}
