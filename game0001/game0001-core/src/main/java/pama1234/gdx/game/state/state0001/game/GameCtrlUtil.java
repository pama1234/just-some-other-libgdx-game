package pama1234.gdx.game.state.state0001.game;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextureButton;

public class GameCtrlUtil{
  public static <T extends Screen0011> Button<?>[] genButtons_0005(T p) {
    return new Button[] {
      new TextureButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },()->ImageAsset.exit,p::getButtonUnitLength,()->p.bu*0.2f,()->p.bu*0.2f,()->p.bu,()->p.bu),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0007(T p,Game pg) {
    return new TextButton[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        pg.androidRightMouseButton=!pg.androidRightMouseButton;
        self.updateText();
      },self->self.text=pg.androidRightMouseButton?"mR":"mL",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.S);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.S);
      },self->self.text="↓",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.A);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.A);
      },self->self.text="← ",p::getButtonUnitLength,()->p.bu*1.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.D);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.D);
      },self->self.text=" →",p::getButtonUnitLength,()->p.bu*3f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      //--------------------------------------------------------------------
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.SPACE);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.SPACE);
      },self->self.text="↑",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*2.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.scrolled(0,1);
      },self->self.text="+ ",p::getButtonUnitLength,()->p.width-p.bu*3.5f,()->p.bu*0.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.scrolled(0,-1);
      },self->self.text=" -",p::getButtonUnitLength,()->p.width-p.bu*2f,()->p.bu*0.5f,()->p.bu-p.pus,false),
    };
  }
}