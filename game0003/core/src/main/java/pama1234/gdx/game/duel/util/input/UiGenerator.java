package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.ui.util.TextButton;

public class UiGenerator{
  public static TextButton<?>[] genButtons_0010(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z ",p::getButtonUnitLength,()->p.width-p.bu*3f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text=" X",p::getButtonUnitLength,()->p.width-p.bu*1.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
    };
  }
}