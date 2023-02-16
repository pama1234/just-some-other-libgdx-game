package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.app.app0002.Screen0006;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.Settings;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
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
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0009(Screen0011 p,Block in) {
    String[] text0001=new String[] {"On  ","Off ","Step"};
    return new TextButtonCam[] {
      new TextButtonCam<Screen0011>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[1]=(in.intData[1]+1)%3;
        self.updateText();
      },self->self.text=text0001[in.intData[1]],()->18,()->in.intData[2]-5,()->in.intData[3]),
      new TextButtonCam<Screen0011>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[0]-=1;
        if(in.intData[0]<0) in.intData[0]+=in.type.intData[0];
      },self->self.text="-",()->18,()->in.intData[2]+45,()->in.intData[3]),
      new TextButtonCam<Screen0011>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[0]+=1;
        if(in.intData[0]>=in.type.intData[0]) in.intData[0]-=in.type.intData[0];
      },self->self.text="+",()->18,()->in.intData[2]+71,()->in.intData[3]),
    };
  }
  public static TextField[] genTextFields_0002(Screen0011 p) {
    TextField[] out=new TextField[] {new TextField("联机写好后，这里输入IP地址哦",new CodeTextFieldStyle(p),
      new RectF(()->0,()->-20,()->256,()->18),
      ()->1)};
    out[0].setMessageText("联机网络地址");
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
    // return new TextField[0];
  }
  public static TextField[] genTextFields_0001(Screen0011 p) {
    // TextField[] out=new TextField[] {new TextField("测试文本输入框",new CodeTextFieldStyle(p),
    //   new RectF(()->p.u*5,()->p.u,()->p.width-p.u*6,()->p.u+p.pus*2),
    //   ()->p.pus)};
    // for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    // return out;
    return new TextField[0];
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
        self-> {
          self.text=pg.world().yourself.ctrl.shift?"S":"s";
          // self.text=pg.world().yourself==null?"s":(pg.world().yourself.ctrl.shift?"S":"s");
        },"s",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,true),
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
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0006(T p,Settings ps) {
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.mute=!p.settings.mute;
        self.updateText();
      },self->self.text=p.settings.mute?"静音：是":"静音：否",()->18,()->0,()->0),
      ps.sliders[0]=new Slider<T>(p,true,()->true,self-> {
        p.settings.volume=ps.sliders[0].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="音量 "+String.format("%6.2f",p.settings.volume*100),()->18,()->0,()->20,1),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugInfo=!p.settings.debugInfo;
        Game game=(Game)State0001.Game.entity;
        if(game.debug=p.settings.debugInfo) game.createDebugDisplay();
        self.updateText();
      },self->self.text=p.settings.debugInfo?"显示文本调试信息：是":"显示文本调试信息：否",()->18,()->0,()->40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        Game game=(Game)State0001.Game.entity;
        game.debugGraphics=!game.debugGraphics;
        if(game.debugGraphics) game.createDebugDisplay();
        self.updateText();
      },self->self.text=((Game)State0001.Game.entity).debugGraphics?"显示图形调试信息：是":"显示图形调试信息：否",()->18,()->0,()->60),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        ((Game)State0001.Game.entity).world().pauseSave();
        p.state(State0001.Loading);
      },self->self.text="重新加载图片素材",()->18,()->0,()->80),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text="清理内存垃圾",()->18,()->0,()->100),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showEarth=!p.settings.showEarth;
        if(p.settings.showEarth&&GifAsset.bigEarth==null) GifAsset.bigEarth=GifAsset.load(Animation.PlayMode.LOOP,Gdx.files.internal("image/bigEarth.gif").read());
        self.updateText();
      },self->self.text="开始界面显示地球："+(p.settings.showEarth?"是":"否"),()->18,()->0,()->120),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.zoomButton=!p.settings.zoomButton;
        if(p.settings.zoomButton) for(TextButton<?> e:p.buttons) p.centerScreen.add.add(e);
        else for(TextButton<?> e:p.buttons) p.centerScreen.remove.add(e);
        self.updateText();
      },self->self.text="显示缩放按钮："+(p.settings.zoomButton?"是":"否"),()->18,()->0,()->140),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.Debug);
      },self->self.text="调试视图（别按这个）",()->18,()->0,()->160),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(p.gyroscope) {
          p.settings.useGyroscope=!p.settings.useGyroscope;
          self.updateText();
        }
      },self->self.text="使用陀螺仪："+(p.settings.useGyroscope?"是":"否"),()->18,()->0,()->180),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(p.accelerometer) {
          p.settings.useAccelerometer=!p.settings.useAccelerometer;
          self.updateText();
        }
      },self->self.text="使用加速计（未实现）："+(p.settings.useAccelerometer?"是":"否"),()->18,()->0,()->200),
      ps.sliders[1]=new Slider<T>(p,true,()->true,self-> {
        p.settings.gyroscopeSensitivity=ps.sliders[1].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="陀螺仪灵敏度 "+String.format("%5.2f",p.settings.gyroscopeSensitivity),()->18,()->0,()->220,1,-10,10),
      ps.sliders[2]=new Slider<T>(p,true,()->true,self-> {
        p.settings.accelerometerSensitivity=ps.sliders[2].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="加速计灵敏度 "+String.format("%5.2f",p.settings.accelerometerSensitivity),()->18,()->0,()->240,1,-10,10),
      ps.sliders[3]=new Slider<T>(p,true,()->true,self-> {
        p.settings.gConst=ps.sliders[3].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="重力常数 "+String.format("%5.2f",p.settings.gConst),()->18,()->0,()->260,1,9.5f,10f),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.overridePlatform=!p.settings.overridePlatform;
        self.updateText();
      },self->self.text="使用覆盖平台类型："+(p.settings.overridePlatform?"是":"否")+"  重启后生效",()->18,()->0,()->280),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.isAndroid=!p.settings.isAndroid;
        self.updateText();
      },self->self.text="覆盖平台类型："+(p.settings.isAndroid?"手机":"电脑"),()->18,()->0,()->300),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.refreshLocalHost();
        // self.updateText();
      },self->self.text="刷新本机网络地址",()->18,()->0,()->-40),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0008(T p) {
    return new TextButton[] {
      new TextButton<T>(p,true,()->true,self-> {
        p.cam2d.scale.des+=1/32f;
        p.cam2d.testScale();
      },self-> {},self-> {},self->self.text="+",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*1.5f+p.pus,()->p.bu-p.pus,false),
      new TextButton<T>(p,true,()->true,self-> {
        p.cam2d.scale.des-=1/32f;
        p.cam2d.testScale();
      },self-> {},self-> {},self->self.text="-",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*2.5f+p.pus,()->p.bu-p.pus,false),
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
