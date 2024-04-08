package pama1234.gdx.game.ui.generator;

import pama1234.gdx.game.app.app0002.Screen0006;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.ui.UiGeneratorBase;

public class UiGenerator extends UiGeneratorBase{
  public static <T extends Screen0006> Button<?>[] genButtons_0002(T p) {
    return new Button[] {
      new TextButton<T>(p,self->self.text="开始游戏").allTextButtonEvent(self-> {},self-> {},self-> {
        // p.fullSettings=!p.fullSettings;
      }).rectAuto(()->p.width/4f*3-p.pu*2.5f,()->p.height/4f),
      new TextButton<T>(p,self->self.text="　公告　").allTextButtonEvent(self-> {},self-> {},self-> {
        // p.fullSettings=!p.fullSettings;
      }).rectAuto(()->p.width/4f*3-p.pu*2.5f,()->p.height/2f),
      // },"公告",p::getButtonUnitLength,()->p.width/8f*5.5f,()->p.height/2f),
    };
  }
}
