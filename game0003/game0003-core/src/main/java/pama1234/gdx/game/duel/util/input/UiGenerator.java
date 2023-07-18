package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Input;

import pama1234.app.game.server.duel.ServerConfigData.ServerAttr;
import pama1234.gdx.MobileUtil;
import pama1234.gdx.Pama;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.theme.ThemeData;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.editor.TextEditor;
import pama1234.gdx.util.element.AndroidCtrlBase;
import pama1234.math.Tools;

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
  @Deprecated
  public static TextButton<?>[] genButtons_0002(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        if(p.state==p.stateCenter.game) p.state(p.stateCenter.settings);
        else p.state(p.stateCenter.game);
        self.updateText();
      },self-> {},self->self.text=p.state==p.stateCenter.game?"设置":"游戏",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.bu*0.5f,()->p.bu-p.pus,false),
    };
  }
  public static String getSkinText(Duel p) {
    return p.config.theme==null?"无可加载的皮肤配置，重启游戏试试":Duel.localization.yaml.dumpAsMap(p.config.theme.data);
  }
  public static String getServerAttrText(Duel p) {
    return p.config.server==null?"无可加载的联机配置，重启游戏试试":Duel.localization.yaml.dumpAsMap(p.config.server);
  }
  public static TextEditor<?>[] genUi_0002(Duel p) {
    TextEditor<?>[] out=new TextEditor[] {new TextEditor<Duel>(p,p.theme.stroke,-160,-160,320,640) {
      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) try {
          p.config.theme.data=Duel.localization.yaml.load(textArea.getText());
          p.theme=ThemeData.fromData(p.config.theme);
        }catch(RuntimeException e) {
          textArea.setText(getSkinText(p));
        }
      }
    },new TextEditor<Duel>(p,p.theme.stroke,180,-160) {
      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) p.config.server=Duel.localization.yaml.loadAs(in.getText(),ServerAttr.class);
      }
    }};
    out[0].textArea.setText(getSkinText(p));
    out[0].textArea.setMessageText("皮肤配置文件");
    out[1].textArea.setText(getServerAttrText(p));
    out[1].textArea.setMessageText("联机设置");
    return out;
  }
  public static TextButtonCam<?>[] genButtons_0003(Duel p) {
    return new TextButtonCam[] {
      new TextButtonCam<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        MainApp.instance.restartScreen();
      },self->self.text="重启游戏",()->18,()->-40,()->-250),
      new TextButtonCam<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        p.config.gameMode=p.config.gameMode.next();
        self.updateText();
      },self->self.text="模式："+p.config.gameMode.toString(),()->18,()->-40,()->-230),
      new TextButtonCam<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        p.config.orientation=(p.config.orientation+1)%2;
        if(p.isAndroid) {
          if(p.config.orientation==1) p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.portraitCondition;
          else p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.landscapeCondition;
          Pama.mobile.orientation(p.config.orientation);
        }
        self.updateText();
      },self->self.text="显示方向："+(p.config.orientation==MobileUtil.landscape?"横向":"竖向"),()->18,()->-40,()->-210),
      new TextButtonCam<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        p.config.themeType=p.config.nextTheme(p.config.themeType);
        p.updateThemeFromType(p.config.themeType);
        self.updateText();
      },self->self.text="主题："+p.config.themeType.toString(),()->18,()->-40,()->-190),
    };
  }
  public static TextButton<?>[] genButtons_0004(Duel p) {
    // TODO 重写按钮逻辑
    var out=new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        // if(p.debug) {
        //   // System.out.println("UiGenerator.genButtons_0004() firstPlay "+p.config.firstPlay);
        //   // p.config.firstPlay=true;
        //   p.state(p.stateCenter.debug.gamePrototype);
        //   return;
        // }
        if(p.config.firstPlay) {
          // p.config.firstPlay=false;
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
  public static TextButton<?>[] genButtons_0005(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.startMenu);
      },self->self.text="返回",p::getButtonUnitLength,()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*0.5f),()->p.bu-p.pus,true),
    };
  }
}