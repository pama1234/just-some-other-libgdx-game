package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.ui.util.TextButton;

public class UiGenerator{
  public static TextButton<?>[] genButtons_0001(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z ",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text=" X",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
    };
  }
  public static TextButton<?>[] genButtons_0002(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        if(p.state==p.stateCenter.game) p.state(p.stateCenter.settings);
        else p.state(p.stateCenter.game);
        self.updateText();
      },self-> {},self->self.text=p.state==p.stateCenter.game?"设置":"游戏",p::getButtonUnitLength,()->p.width-p.bu*3f,()->p.bu*0.5f,()->p.bu-p.pus,false),
    };
  }
}