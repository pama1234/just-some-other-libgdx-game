package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.app.app0002.Screen0006;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.Settings;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.ui.util.TextureButton;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.app.ScreenCore3D;

public class UiGenerator{
  public static TextField[] genTextFields_0002(Screen0011 p) {
    TextField[] out=new TextField[] {new TextField("写好联机后，这里输入IP地址",new CodeTextFieldStyle(p),
      new RectF(()->0,()->-19,()->256,()->17),
      ()->1)};
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
    // return new TextField[0];
  }
  public static TextField[] genTextFields_0001(Screen0011 p) {
    TextField[] out=new TextField[] {new TextField("测试文本输入框",new CodeTextFieldStyle(p),
      new RectF(()->p.u*5,()->p.u,()->p.width-p.u*6,()->p.u+p.pus),
      ()->p.pus)};
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
    // return new TextField[0];
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0007(T p,Game pg) {
    return new TextButton[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        pg.androidRightMouseButton=!pg.androidRightMouseButton;
        self.updateText();
      },self->self.text=pg.androidRightMouseButton?"mR":"mL",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {},self-> {
        pg.world().yourself.ctrl.shift(!pg.world().yourself.ctrl.shift);
        self.updateText();
      },self-> {},
        self->self.text=pg.world().yourself.ctrl.shift?"S":"s",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,true),
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
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0006(T p,Settings ps) {
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.mute=!p.mute;
        if(p.mute) self.updateText();
      },self->self.text=p.mute?"静音：是":"静音：否",()->18,()->0,()->0),
      ps.volumeSlider=new Slider<T>(p,true,()->true,self-> {
        p.volume=ps.volumeSlider.pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="音量 "+String.format("%6.2f",p.volume*100),()->18,()->0,()->20,1),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.debugInfo=!p.debugInfo;
        Game game=(Game)State0001.Game.entity;
        // game.debug=p.debugInfo;
        if(game.debug=p.debugInfo) game.createDebugDisplay();
        self.updateText();
      },self->self.text=p.debugInfo?"显示文本调试信息：是":"显示文本调试信息：否",()->18,()->0,()->40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        Game game=(Game)State0001.Game.entity;
        game.debugGraphics=!game.debugGraphics;
        if(game.debugGraphics) game.createDebugDisplay();
        if(game.debugGraphics) self.text="显示图形调试信息：是";
        else self.text="显示图形调试信息：否";
      },self->self.text=((Game)State0001.Game.entity).debugGraphics?"显示图形调试信息：是":"显示图形调试信息：否",()->18,()->0,()->60),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        //TODO
      },self->self.text="重新加载图片素材（未实现）",()->18,()->0,()->80),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text="清理内存垃圾",()->18,()->0,()->100),
    };
  }
  public static <T extends Screen0011> Button<?>[] genButtons_0005(T p) {
    return new Button[] {
      new TextureButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },()->ImageAsset.exit,p::getButtonUnitLength,()->p.bu*0.2f,()->p.bu*0.2f,()->p.bu,()->p.bu),
    };
  }
  public static <T extends Screen0011> Button<?>[] genButtons_0004(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },self->self.text="返回",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
    };
  }
  public static <T extends Screen0011> Button<?>[] genButtons_0003(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.Game);
      },self->self.text="开始游戏",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/4f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.Announcement);
      },self->self.text="　公告　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/2f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.Settings);
      },self->self.text="　设置　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/4f*3-p.bu/2f),
    };
  }
  public static <T extends Screen0006> Button<?>[] genButtons_0002(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        // p.fullSettings=!p.fullSettings;
      },self->self.text="开始游戏",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/4f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        // p.fullSettings=!p.fullSettings;
      },self->self.text="　公告　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/2f),
      // },"公告",p::getButtonUnitLength,()->p.width/8f*5.5f,()->p.height/2f),
    };
  }
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
        p.inputProcessor.scrolled(0,-1);
      },self->self.text="sU",p::getButtonUnitLength,()->p.bu*2.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<T>(p,false,()->p.fullSettings,self-> {},self-> {},self-> {
        p.inputProcessor.scrolled(0,1);
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
