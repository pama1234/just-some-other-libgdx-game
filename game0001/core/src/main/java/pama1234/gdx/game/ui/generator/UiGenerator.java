package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextureButton;
import pama1234.gdx.util.app.ScreenCore3D;

public class UiGenerator{
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0010(T p,WorldBase2D<?> world,Block in) {
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        //---
      },self->self.text="背包显示方式：同心圆（未实现）",()->18,()->in.intData[2]-20,()->in.intData[3]),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        world.pg.world0001().timeF=0;
        world.pg.world0001().data.tick=0;
        world.pg.world0001().data.time=12000;
      },self->self.text="重置世界时间",()->18,()->in.intData[2]-20,()->in.intData[3]+20),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        world.pg.world0001().yourself.point.pos.set(4134.0f,3708.0f);
      },self->self.text="传送",()->18,()->in.intData[2]-20,()->in.intData[3]+40),
    };
  }
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0009(T p,Block in) {
    String[] text0001=new String[] {"On  ","Off ","Step"};
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[1]=(in.intData[1]+1)%3;
        self.updateText();
      },self->self.text=text0001[in.intData[1]],()->18,()->in.intData[2]-5,()->in.intData[3]),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[0]-=1;
        if(in.intData[0]<0) in.intData[0]+=in.type.intData[0];
      },self->self.text="-",()->18,()->in.intData[2]+45,()->in.intData[3]),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        in.intData[0]+=1;
        if(in.intData[0]>=in.type.intData[0]) in.intData[0]-=in.type.intData[0];
      },self->self.text="+",()->18,()->in.intData[2]+71,()->in.intData[3]),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0007(T p,Game pg) {
    return new TextButton[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        pg.androidRightMouseButton=!pg.androidRightMouseButton;
        self.updateText();
      },self->self.text=pg.androidRightMouseButton?"mR":"mL",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      // new TextButton<T>(p,true,()->true,self-> {},self-> {
      //   pg.world().yourself.ctrl.shift(!pg.world().yourself.ctrl.shift);
      //   self.updateText();
      // },self-> {},
      //   self->self.text=pg.world().yourself.ctrl.shift?"S":"s","s",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,true),
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
