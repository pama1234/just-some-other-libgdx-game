package pama1234.gdx.game.sandbox.platformer;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextureButton;

public class GameCtrlUtil{
  /**
   * 生成返回按钮
   * 
   * @param <T>
   * @param p
   * @return
   */
  public static <T extends Screen0011> Button<?>[] genButtons_0005(T p) {
    return new Button[] {
      new TextureButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.startMenu);
      },()->ImageAsset.exit,p::getButtonUnitLength,()->p.bu*0.2f,()->p.bu*0.2f,()->p.bu,()->p.bu),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0007(T p,Game pg) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="↑",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SPACE);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SPACE);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*2.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text="↓",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.S);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.S);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="← ",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.A);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.A);
        })
        .rectAutoWidth(()->p.bu*1.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=" →",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.D);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.D);
        })
        .rectAutoWidth(()->p.bu*3f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="+ ",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,1);
        })
        .rectAutoWidth(()->p.width-p.bu*3.5f,()->p.bu*0.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=" -",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,-1);
        })
        .rectAutoWidth(()->p.width-p.bu*2f,()->p.bu*0.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=pg.androidRightMouseButton?"mR":"mL",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          pg.androidRightMouseButton=!pg.androidRightMouseButton;
          self.updateText();
        })
        .rectAutoWidth(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0011(T p,Game pg) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="+ ",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,1);
        })
        .rectAutoWidth(()->p.width-p.bu*3.5f,()->p.bu*0.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=" -",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,-1);
        })
        .rectAutoWidth(()->p.width-p.bu*2f,()->p.bu*0.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=pg.androidRightMouseButton?"mR":"mL",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          pg.androidRightMouseButton=!pg.androidRightMouseButton;
          self.updateText();
        })
        .rectAutoWidth(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0012(T p,Game pg) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="↑",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SPACE);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SPACE);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*2.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text="↓",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.S);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.S);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="← ",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.A);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.A);
        })
        .rectAutoWidth(()->p.bu*1.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text=" →",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.D);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.D);
        })
        .rectAutoWidth(()->p.bu*3f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
    };
  }
}