package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.app0004.Screen0016;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0003.Game;
import pama1234.gdx.game.state.state0003.Settings;
import pama1234.gdx.game.state.state0003.State0003;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextureButton;

public class UiGenerator0003{
  public static <T extends Screen0016> TextButton<?>[] genButtons_0007(T p,Game pg) {
    return new TextButton[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        pg.androidRightMouseButton=!pg.androidRightMouseButton;
        pg.ctrlButtons[0].text=pg.androidRightMouseButton?"mR":"mL";
      },self->self.text="mL",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        // p.inputProcessor.keyDown(Input.Keys.SHIFT_LEFT);
        // pg.world.yourself.ctrl.shift(!pg.world.yourself.ctrl.shift);
        // pg.ctrlButtons[1].text=pg.world.yourself.ctrl.shift?"S":"s";
      },self-> {
        // p.inputProcessor.keyUp(Input.Keys.SHIFT_LEFT);
      },self->self.text="s",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,true),
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
    };
  }
  public static <T extends Screen0016> TextButtonCam<?>[] genButtons_0006(T p,Settings ps) {
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.mute=!p.mute;
        if(p.mute) ps.buttonsCam[0].text="静音：是";
        else ps.buttonsCam[0].text="静音：否";
      },self->self.text="静音：否",()->18,()->0,()->0),
      ps.volumeSlider=new Slider<T>(p,true,()->true,self-> {
        p.volume=ps.volumeSlider.pos;
        // ps.volumeSlider.text="音量 "+Tools.cutToLastDigit(p.volume*100);
        ps.volumeSlider.text="音量 "+String.format("%6.2f",p.volume*100);
      },self-> {},self-> {},self->self.text="音量 100.00",()->18,()->0,()->20,1),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.debugInfo=!p.debugInfo;
        Game game=(Game)State0003.Game.entity;
        // boolean flag=game.debug;
        game.debug=p.debugInfo;
        if(game.debug=p.debugInfo) game.createDebugDisplay();
        // if(game.debug) {
        //   game.createDebugDisplay();
        //   if(!flag) p.centerCam.add.add(game.displayCamTop);
        // }else if(flag) p.centerCam.remove.add(game.displayCamTop);
        if(p.debugInfo) ps.buttonsCam[2].text="显示调试信息：是";
        else ps.buttonsCam[2].text="显示调试信息：否";
      },self->self.text="显示调试信息：否",()->18,()->0,()->40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text="清理内存垃圾",()->18,()->0,()->60),
    };
  }
  public static <T extends Screen0016> Button<?>[] genButtons_0005(T p) {
    return new Button[] {
      new TextureButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.StartMenu);
      },()->ImageAsset.exit,p::getButtonUnitLength,()->p.bu*0.2f,()->p.bu*0.2f,()->p.bu,()->p.bu),
    };
  }
  public static <T extends Screen0016> Button<?>[] genButtons_0004(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.StartMenu);
      },self->self.text="返回",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
    };
  }
  public static <T extends Screen0016> Button<?>[] genButtons_0003(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.Game);
      },self->self.text="开始游戏",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/4f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.Announcement);
      },self->self.text="　公告　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/2f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.Settings);
      },self->self.text="　设置　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/4f*3-p.bu/2f),
    };
  }
  public static <T extends Screen0016> Button<?>[] genButtons_0001(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0003.StartMenu);
      },self->self.text="返回",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
    };
  }
}
