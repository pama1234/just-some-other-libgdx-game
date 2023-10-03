package pama1234.gdx.util.ui;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCore3D;

public class UiGeneratorBase{
  /**
   * @param p <br/>
   *          _T _Z _H __ <br/>
   *          _X _C _N _M <br/>
   *          _R _F sU sD <br/>
   */
  public static <T extends ScreenCore3D> Button<?>[] genButtons_0001(T p) {
    return new Button[] {
      new TextButton<T>(p,self->self.text="T",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.fullSettings=!p.fullSettings;
        })
        .rectAuto(()->p.bu*0.5f,()->p.bu*0.5f),
      new TextButton<T>(p,self->self.text="Z",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.Z);
          p.inputProcessor.keyUp(Input.Keys.Z);
        })
        .rectAuto(()->p.bu*1.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,self->self.text="H",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.H);
          p.inputProcessor.keyUp(Input.Keys.H);
        })
        .rectAuto(()->p.bu*2.5f,()->p.bu*0.5f),
      new TextButton<T>(p,self->self.text="I",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.I);
          p.inputProcessor.keyUp(Input.Keys.I);
        })
        .rectAuto(()->p.bu*3.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,self->self.text="X",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.X);
          p.inputProcessor.keyUp(Input.Keys.X);
        }).rectF(()->p.bu*0.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="C",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.C);
          p.inputProcessor.keyUp(Input.Keys.C);
        }).rectF(()->p.bu*1.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="N",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.N);
          p.inputProcessor.keyUp(Input.Keys.N);
        }).rectF(()->p.bu*2.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="M",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.M);
          p.inputProcessor.keyUp(Input.Keys.M);
        }).rectF(()->p.bu*3.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,self->self.text="R",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.R);
          p.inputProcessor.keyUp(Input.Keys.R);
        }).rectF(()->p.bu*0.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="F",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.F);
          p.inputProcessor.keyUp(Input.Keys.F);
        }).rectF(()->p.bu*1.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="sU",()->p.fullSettings,false)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,-1);
        }).rectF(()->p.bu*2.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,self->self.text="sD",()->p.fullSettings,false)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,1);
        }).rectF(()->p.bu*3.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
    };
  }
}
