package pama1234.gdx.game.cgj.ui.generator;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.cgj.app.app0002.MainMenu;
import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.ui.UiGeneratorBase;

public class UiGenerator extends UiGeneratorBase{
  public static TextButton<?>[] genButtons_0002(MainMenu p) {
    return new TextButton[] {
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.realGame);
      },self->self.text="开始游戏",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="2D沙盒",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*1.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="3D沙盒",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*2.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="开启服务器",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*3.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="加入服务器",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*4.5f),
      //--------------------------------------------------------------------
    };
  }
  public static TextButton<?>[] genButtons_0003(ScreenCore2D p) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="前",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.Z);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.Z);
        })
        .rectAutoWidth(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text="后",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.X);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.X);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
    };
  }
  public static TextButton<?>[] genButtons_0005(Screen0045 p) {
    return new TextButton[] {
      new TextButton<Screen0045>(p,true,()->true,self-> {},self-> {
        // TODO
        self.touch.state=1;
      },self-> {
        p.state(p.stateCenter.startMenu);
      },self->self.text="返回",p::getButtonUnitLength,()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*1.5f),()->p.bu-p.pus).mouseLimit(true),
    };
  }
}
