package pama1234.gdx.util.cam;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore3D;

public class CameraMisc{
  /**
   * @param p <br/>
   *          _↑ __ _W __ <br/>
   *          _↓ _A _S _D <br/>
   */
  public static <T extends ScreenCore3D> TextButton<?>[] cam3dButtons(T p) {
    return new TextButton[] {
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
