package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Input;

import pama1234.Tools;
import pama1234.app.game.server.duel.ServerConfigData.ServerAttr;
import pama1234.gdx.MobileUtil;
import pama1234.gdx.Pama;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.theme.ThemeData;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.android.AndroidCtrlBase;
import pama1234.gdx.util.editor.TextEditor;
import pama1234.gdx.util.ui.UiGeneratorBase;

public class UiGenerator extends UiGeneratorBase{
  public static TextButton<?>[] genButtons_0001(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z ",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus).mouseLimit(false),
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text=" X",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus).mouseLimit(false),
    };
  }
  @Deprecated
  public static TextButton<?>[] genButtons_0002(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        if(p.state==p.stateCenter.game) p.state(p.stateCenter.settings);
        else p.state(p.stateCenter.game);
        self.updateText();
      },self-> {},self->self.text=p.state==p.stateCenter.game?"设置":"游戏",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.bu*0.5f,()->p.bu-p.pus).mouseLimit(false),
    };
  }
  public static String getSkinText(Duel p) {
    return p.config.customThemeData==null?"无可加载的皮肤配置，重启游戏试试":Duel.localization.yaml.dumpAsMap(p.config.customThemeData.data);
  }
  public static String getServerAttrText(Duel p) {
    return p.config.data.server==null?"无可加载的联机配置，重启游戏试试":Duel.localization.yaml.dumpAsMap(p.config.data.server);
  }
  public static TextEditor<?>[] genUi_0002(Duel p) {
    TextEditor<?>[] out=new TextEditor[] {new TextEditor<Duel>(p,p.theme().stroke,-160,-160,320,480) {
      @Override
      public void display() {
        super.display();
        p.textColor(p.theme().text);
        p.text("皮肤设置",rect.x(),rect.y()-20);
      }
      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) try {
          p.config.customThemeData.data=Duel.localization.yaml.load(textArea.getText());
          p.theme(ThemeData.fromData(p.config.customThemeData));
        }catch(RuntimeException e) {
          textArea.setText(getSkinText(p));
        }
      }
    },new TextEditor<Duel>(p,p.theme().stroke,180,-160,320,60) {
      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) p.config.data.server=Duel.localization.yaml.loadAs(in.getText(),ServerAttr.class);
      }
    }};
    out[0].textArea.setText(getSkinText(p));
    out[0].textArea.setMessageText("皮肤设置");
    out[1].textArea.setText(getServerAttrText(p));
    out[1].textArea.setMessageText("联机设置");
    return p.debug?out:new TextEditor[] {out[0]};
  }
  public static TextButtonCam<?>[] genButtons_0003(Duel p) {
    return new TextButtonCam[] {
      new TextButtonCam<Duel>(p,self->self.text="重启游戏")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          MainApp.instance.restartScreen();
        })
        .rectAuto(()->-40,()->-250),
      new TextButtonCam<Duel>(p,self->self.text="模式："+p.config.data.gameMode.toString(),()->p.debug,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.gameMode=p.config.data.gameMode.next();
          self.updateText();
        })
        .rectAuto(()->-40,()->-230),
      new TextButtonCam<Duel>(p,self->self.text="显示方向："+(p.config.data.orientation==MobileUtil.landscape?"横向":"竖向"))
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.orientation=(p.config.data.orientation+1)%2;
          if(p.isAndroid) {
            if(p.config.data.orientation==1) p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.portraitCondition;
            else p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.landscapeCondition;
            Pama.mobile.orientation(p.config.data.orientation);
          }
          self.updateText();
        })
        .rectAuto(()->-40,()->-210),
      new TextButtonCam<Duel>(p,self->self.text="主题："+p.config.data.themeType.toString())
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.themeType=p.config.data.nextTheme(p.config.data.themeType);
          p.config.updateThemeFromType(p.config.data.themeType);
          self.updateText();
        })
        .rectAuto(()->-40,()->-190),
      // 音量控制条
      new Slider<Duel>(p,true,()->true,self-> {
        p.config.data.volume=self.pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="音量："+String.format("%6.2f",p.config.data.volume*100),()->18,()->-40,()->-270,1)
          .pos(p.config.data.volume),
      new TextButtonCam<Duel>(p,self->self.text="FPS修复：（需重启游戏）"+(p.config.data.fpsFix?"是":"否"))
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.fpsFix=!p.config.data.fpsFix;
          self.updateText();
        })
        .rectAuto(()->-40,()->-290),
    };
  }
  public static TextButton<?>[] genButtons_0004(Duel p) {
    // TODO 重写按钮逻辑
    var out=new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        // if(p.debug) {
        //   // System.out.println("UiGenerator.genButtons_0004() firstPlay "+p.config.data.firstPlay);
        //   // p.config.data.firstPlay=true;
        //   p.state(p.stateCenter.debug.gamePrototype);
        //   return;
        // }
        if(p.config.data.firstPlay) {
          // p.config.data.firstPlay=false;
          p.state(p.stateCenter.tutorial);
        }else p.state(p.stateCenter.game);
      },self->self.text="开始游戏",()->p.bu,()->(int)((p.width-p.textWidth("开始游戏"))/2f-p.pu/2),()->(int)(p.height*0.45f)),
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.settings);
      },self->self.text="  设置  ",()->p.bu,()->(int)((p.width-p.textWidth("  设置  "))/2f-p.pu/2),()->(int)(p.height*0.45f+p.bu*1.2f)),
    };
    if(p.debug) {
      out=Tools.concat(out,new TextButton[] {
        new TextButton<Duel>(p,true,()->true,self-> {},self-> {},self-> {
          p.state(p.stateCenter.debug.gamePrototype);
        },self->self.text="原型测试",()->p.bu,()->(int)((p.width-p.textWidth("原型测试"))/2f-p.pu/2),()->(int)(p.height*0.45f+p.bu*2.4f)),});
    }
    return out;
  }
  /**
   * 生成返回按钮
   * 
   * @param p
   * @return
   */
  public static TextButton<?>[] genButtons_0005(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,self->self.text="返回",()->true,true).allTextButtonEvent(self-> {},self-> {},self-> {
        p.state(p.stateCenter.startMenu);
      }).rectAutoWidth(()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*0.5f),()->p.bu-p.pus).mouseLimit(true),
    };
  }
}