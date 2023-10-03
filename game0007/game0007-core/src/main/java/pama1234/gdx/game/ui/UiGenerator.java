package pama1234.gdx.game.ui;

import pama1234.gdx.game.app.Screen0037;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.ui.UiGeneratorBase;

public class UiGenerator extends UiGeneratorBase{
  public static TextButton<?>[] genButtons_0005(Screen0037 p) {
    return new TextButton[] {
      new TextButton<Screen0037>(p,self->self.text="重置")
        .allTextButtonEvent(self-> {},self-> {
          p.reset();
        },self-> {})
        .rectAutoWidth(()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*0.5f),()->p.bu-p.pus),
    };
  }
}
