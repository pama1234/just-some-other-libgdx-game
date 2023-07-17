package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore3D;

public class UiGenerator{
  /**
   * @param p <br/>
   *          _T _Z _H __ <br/>
   *          _X _C _N _M <br/>
   *          _R _F sU sD <br/>
   *          ----------- <br/>
   *          _↑ __ _W __ <br/>
   *          _↓ _A _S _D <br/>
   */
  public static <T extends ScreenCore3D> Button<?>[] genButtons_0001(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.fullSettings=!p.fullSettings;
      },self->self.text="T",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z",p::getButtonUnitLength,()->p.bu*1.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.H);
        p.inputProcessor.keyUp(Input.Keys.H);
      },self->self.text="H",p::getButtonUnitLength,()->p.bu*2.5f,()->p.bu*0.5f),
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.I);
        p.inputProcessor.keyUp(Input.Keys.I);
      },self->self.text="I",p::getButtonUnitLength,()->p.bu*3.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text="X",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.C);
        p.inputProcessor.keyUp(Input.Keys.C);
      },self->self.text="C",p::getButtonUnitLength,()->p.bu*1.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.N);
        p.inputProcessor.keyUp(Input.Keys.N);
      },self->self.text="N",p::getButtonUnitLength,()->p.bu*2.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.M);
        p.inputProcessor.keyUp(Input.Keys.M);
      },self->self.text="M",p::getButtonUnitLength,()->p.bu*3.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.R);
        p.inputProcessor.keyUp(Input.Keys.R);
      },self->self.text="R",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,true,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.F);
        p.inputProcessor.keyUp(Input.Keys.F);
      },self->self.text="F",p::getButtonUnitLength,()->p.bu*1.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,false,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.scrolled(0,1);
      },self->self.text="sU",p::getButtonUnitLength,()->p.bu*2.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,false,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.scrolled(0,-1);
      },self->self.text="sD",p::getButtonUnitLength,()->p.bu*3.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.W);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.W);
      },self->self.text="W",p::getButtonUnitLength,()->p.bu*2.5f,()->p.height-p.bu*2.5f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.S);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.S);
      },self->self.text="S",p::getButtonUnitLength,()->p.bu*2.5f,()->p.height-p.bu*1.5f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.A);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.A);
      },self->self.text="A",p::getButtonUnitLength,()->p.bu*1.5f,()->p.height-p.bu*1.5f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.D);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.D);
      },self->self.text="D",p::getButtonUnitLength,()->p.bu*3.5f,()->p.height-p.bu*1.5f),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.SPACE);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.SPACE);
      },self->self.text="↑",p::getButtonUnitLength,()->p.bu*0.5f,()->p.height-p.bu*2.5f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.SHIFT_LEFT);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.SHIFT_LEFT);
      },self->self.text="↓",p::getButtonUnitLength,()->p.bu*0.5f,()->p.height-p.bu*1.5f)
    };
  }
}
