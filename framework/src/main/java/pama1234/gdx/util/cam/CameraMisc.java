package pama1234.gdx.util.cam;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCore3D;

public class CameraMisc{
  /**
   * @param p
   *          <p/>
   *          _↑ __ _W __
   *          <p/>
   *          _↓ _A _S _D
   *          <p/>
   */
  public static <T extends ScreenCore3D> TextButton<?>[] cam3dButtons(T p) {
    return new TextButton[] {
      new TextButton<T>(p,self->self.text="W",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.W);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.W);
        })
        .rectF(()->p.bu*2.5f,()->p.height-p.bu*2.5f,()->p.bu,p::getButtonUnitLength),
      new TextButton<T>(p,self->self.text="S",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.S);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.S);
        })
        .rectF(()->p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu,p::getButtonUnitLength),
      new TextButton<T>(p,self->self.text="A",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.A);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.A);
        })
        .rectF(()->p.bu*1.5f,()->p.height-p.bu*1.5f,()->p.bu,p::getButtonUnitLength),
      new TextButton<T>(p,self->self.text="D",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.D);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.D);
        })
        .rectF(()->p.bu*3.5f,()->p.height-p.bu*1.5f,()->p.bu,p::getButtonUnitLength),
      //--------------------------------------------------------------------
      new TextButton<T>(p,self->self.text="↑",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SPACE);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SPACE);
        })
        .rectF(()->p.bu*0.5f,()->p.height-p.bu*2.5f,()->p.bu,p::getButtonUnitLength),
      new TextButton<T>(p,self->self.text="↓",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SHIFT_LEFT);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SHIFT_LEFT);
        })
        .rectF(()->p.bu*0.5f,()->p.height-p.bu*1.5f,()->p.bu,p::getButtonUnitLength),
    };
  }
}
