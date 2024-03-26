package pama1234.gdx.game.ui.generator;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.item.Inventory;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.state.state0001.setting.Settings;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.ui.UiGeneratorBase;

import com.badlogic.gdx.Input;

// TODO "world.pg.world()"更改为更为合理的访问方式
public class UiGenerator extends UiGeneratorBase{
  /**
   * 生成WorldDebugger的按钮
   * 
   * @param <T>
   * @param p
   * @param world
   * @param in
   * @return
   */
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0010(T p,WorldBase2D<?> world,Block in) {
    return new TextButtonCam[] {
      new TextButtonCam<>(p,self->self.text="背包显示方式："+(world.pg.world().yourself.inventory.gridUi?"阵列":"同心圆"),()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          Inventory inventory=world.pg.world().yourself.inventory;
          inventory.gridUi=!inventory.gridUi;
          self.updateText();
        })
        .rectAuto(()->in.intData[2]-20,()->in.intData[3]),
      new TextButtonCam<>(p,self->self.text="重置世界时间",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          world.pg.world().timeF=0;
          world.pg.world().data.tick=0;
          world.pg.world().data.time=12000;
        })
        .rectAuto(()->in.intData[2]-20,()->in.intData[3]+20),
      new TextButtonCam<>(p,self->self.text="传送",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          world.pg.world().yourself.point.pos.set(4134.0f,3708.0f);
        })
        .rectAuto(()->in.intData[2]-20,()->in.intData[3]+40),
      new TextButtonCam<>(p,self->self.text="安卓版操作UI",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          // world.pg.world().yourself.point.pos.set(4134.0f,3708.0f);
        })
        .rectAuto(()->in.intData[2]-20,()->in.intData[3]+60),
    };
  }
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0009(T p,Block in) {
    String[] text0001=new String[] {"On  ","Off ","Step"};
    return new TextButtonCam[] {
      new TextButtonCam<>(p,self->self.text=text0001[in.intData[1]],()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          in.intData[1]=(in.intData[1]+1)%3;
          self.updateText();
        })
        .rectAuto(()->in.intData[2]-5,()->in.intData[3]),
      new TextButtonCam<>(p,self->self.text="-",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          in.intData[0]-=1;
          if(in.intData[0]<0) in.intData[0]+=in.type.attr.intData[0];
        })
        .rectAuto(()->in.intData[2]+45,()->in.intData[3]),
      new TextButtonCam<>(p,self->self.text="+",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          in.intData[0]+=1;
          if(in.intData[0]>=in.type.attr.intData[0]) in.intData[0]-=in.type.attr.intData[0];
        })
        .rectAuto(()->in.intData[2]+71,()->in.intData[3]),
    };
  }
  public static <T extends Screen0011> TextButton<?>[] genButtons_0008(T p) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="+",()->true,false)
        .allTextButtonEvent(self-> {
          p.cam2d.scale.des+=1/32f;
          p.cam2d.testScale();
        },self-> {},self-> {})
        .rectAutoWidth(()->p.bu*0.5f,()->p.bu*1.5f+p.pus,()->p.bu-p.pus),
      new TextButton<>(p,self->self.text="-",()->true,false)
        .allTextButtonEvent(self-> {
          p.cam2d.scale.des-=1/32f;
          p.cam2d.testScale();
        },self-> {},self-> {})
        .rectAutoWidth(()->p.bu*0.5f,()->p.bu*2.5f+p.pus,()->p.bu-p.pus),
    };
  }
  @Deprecated
  public static <T extends Screen0011> Button<?>[] genButtons_return(T p,Settings ps,SettingSector0011 ss) {
    return new Button[] {
      new TextButton<>(p,self->self.text="返回",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          ps.sec.switchSetting(ss);
        })
        .rectAuto(()->p.bu*0.5f,()->p.bu*0.5f),
    };
  }
  /**
   * 生成返回按钮
   * 
   * @param <T>
   * @param p
   * @return
   */
  public static <T extends Screen0011> Button<?>[] genButtons_0004(T p) {
    return new Button[] {
      new TextButton<>(p,self->self.text="返回",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.state(p.stateCenter.startMenu);
        })
        .rectAuto(()->p.bu*0.5f,()->p.bu*0.5f),
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
      new TextButton<>(p,self->self.text="T",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.fullSettings=!p.fullSettings;
        })
        .rectAuto(()->p.bu*0.5f,()->p.bu*0.5f),
      new TextButton<>(p,self->self.text="Z",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.Z);
          p.inputProcessor.keyUp(Input.Keys.Z);
        })
        .rectAuto(()->p.bu*1.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="H",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.H);
          p.inputProcessor.keyUp(Input.Keys.H);
        })
        .rectAuto(()->p.bu*2.5f,()->p.bu*0.5f),
      new TextButton<>(p,self->self.text="I",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.I);
          p.inputProcessor.keyUp(Input.Keys.I);
        })
        .rectAuto(()->p.bu*3.5f,()->p.bu*0.5f),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="X",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.X);
          p.inputProcessor.keyUp(Input.Keys.X);
        }).rectF(()->p.bu*0.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="C",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.C);
          p.inputProcessor.keyUp(Input.Keys.C);
        }).rectF(()->p.bu*1.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="N",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.N);
          p.inputProcessor.keyUp(Input.Keys.N);
        }).rectF(()->p.bu*2.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="M",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.M);
          p.inputProcessor.keyUp(Input.Keys.M);
        }).rectF(()->p.bu*3.5f,()->p.bu*1.5f,()->p.bu,()->p.bu*0.75f),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="R",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.R);
          p.inputProcessor.keyUp(Input.Keys.R);
        }).rectF(()->p.bu*0.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="F",()->p.fullSettings,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.F);
          p.inputProcessor.keyUp(Input.Keys.F);
        }).rectF(()->p.bu*1.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="sU",()->p.fullSettings,false)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,-1);
        }).rectF(()->p.bu*2.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      new TextButton<>(p,self->self.text="sD",()->p.fullSettings,false)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.inputProcessor.scrolled(0,1);
        }).rectF(()->p.bu*3.5f,()->p.bu*2.25f,()->p.bu,()->p.bu*0.75f),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="W",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.W);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.W);
        })
        .rectAuto(()->p.bu*2.5f,()->p.height-p.bu*2.5f),
      new TextButton<>(p,self->self.text="S",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.S);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.S);
        })
        .rectAuto(()->p.bu*2.5f,()->p.height-p.bu*1.5f),
      new TextButton<>(p,self->self.text="A",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.A);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.A);
        })
        .rectAuto(()->p.bu*1.5f,()->p.height-p.bu*1.5f),
      new TextButton<>(p,self->self.text="D",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.D);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.D);
        })
        .rectAuto(()->p.bu*3.5f,()->p.height-p.bu*1.5f),
      //--------------------------------------------------------------------
      new TextButton<>(p,self->self.text="↑",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SPACE);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SPACE);
        })
        .rectAuto(()->p.bu*0.5f,()->p.height-p.bu*2.5f),
      new TextButton<>(p,self->self.text="↓",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.SHIFT_LEFT);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.SHIFT_LEFT);
        })
        .rectAuto(()->p.bu*0.5f,()->p.height-p.bu*1.5f)
    };
  }
}
