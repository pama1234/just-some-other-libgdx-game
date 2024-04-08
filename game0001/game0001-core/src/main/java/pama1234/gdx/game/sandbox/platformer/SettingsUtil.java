package pama1234.gdx.game.sandbox.platformer;

import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.Tools;
import pama1234.gdx.SystemSetting;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.setting.Settings;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.launcher.MainApp;
import pama1234.math.geometry.RectF;

public class SettingsUtil{
  /**
   * 生成语言设置文本框
   * 
   * @param p parent screen
   * @return output
   */
  public static TextField[] genTextFields_0002(Screen0011 p) {
    TextField[] out=new TextField[] {new TextField(Tools.indent(p.settings.langType,1),new CodeTextFieldStyle(p),
      new RectF(()->0,()->-20,()->82,()->18),
      ()->1,ld.languageSettings) {
      @Override
      public void draw(Batch batch,float parentAlpha) {
        super.draw(batch,parentAlpha);
        p.textColor(204);
        p.text(ld.languageSettings,86,-20);
      }
    }};
    out[0].addListener(new FocusListener() {
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        if(!focused) {
          String newLangType=out[0].getText().trim();
          if(!p.settings.langType.equals(newLangType)) {
            p.settings.langType=newLangType;
            Settings.initLocalization(p);
            out[0].setText(Tools.indent(p.settings.langType,1));
          }
        }
      }
    });
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
  }
  /**
   * 生成调试按钮
   * 
   * @param <T>
   * @param p
   * @param ps
   * @return
   */
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_debug(T p,Settings ps) {
    return new TextButtonCam[] {
      // 重启游戏
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        MainApp.instance.restartScreen();
      },self->self.text=ld.restart,()->18,()->0,()->-120),
      // 重置设置
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.initSettings();
        p.saveSettings();
        for(TextButtonCam<?> e:ps.buttonsCam) e.updateText();
        self.updateText();
      },self->self.text=ld.resetSettings,()->18,()->0,()->-100),
      // 调试信息
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugInfo=!p.settings.debugInfo;
        p.debugInfoChange();
        self.updateText();
      },self->self.text=p.settings.debugInfo?ld.debugInfoYes:ld.debugInfoNo,()->18,()->0,()->-40),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugGraphics=!p.settings.debugGraphics;
        Game game=p.stateCenter.game;
        if(p.settings.debugGraphics) game.createDebugDisplay();
        self.updateText();
      },self->self.text=p.settings.debugGraphics?ld.debugGraphicsYes:ld.debugGraphicsNo,()->18,()->0,()->60),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        boolean firstInit=(p.stateCenter.game).firstInit;
        if(!firstInit) (p.stateCenter.game).world().pauseSave();//判断有没有初始化
        p.state(p.stateCenter.loading);
        //由于Loading中会把firstInit修改为true,所以要修改会原来的值，否则就会因为线程再次启动而崩溃
        (p.stateCenter.game).firstInit=firstInit;
      },self->self.text=ld.reloadAssets,()->18,()->0,()->80),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text=ld.systemGc,()->18,()->0,()->100),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.debug);
      },self->self.text=ld.debugView,()->18,()->0,()->160),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        SystemSetting.data.overridePlatform=!SystemSetting.data.overridePlatform;
        self.updateText();
      },self->self.text=ld.setDebugPlatformType+(SystemSetting.data.overridePlatform?ld.yes:ld.no),()->18,()->0,()->280) {
        @Override
        public void display() {
          super.display();
          p.text(ld.needRestart,ps.needRestartTextPosX,rect.y1());
        }
      },
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        SystemSetting.data.isAndroid=!SystemSetting.data.isAndroid;
        self.updateText();
      },self->self.text=ld.overridePlatform+(SystemSetting.data.isAndroid?ld.phone:ld.personalComputer),()->18,()->0,()->300),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showLog=!p.settings.showLog;
        self.updateText();
        p.checkNeedLog();
      },self->self.text=ld.showLog+(p.settings.showLog?ld.yes:ld.no),()->18,()->0,()->320),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        SystemSetting.data.printLog=!SystemSetting.data.printLog;
        self.updateText();
      },self->self.text=ld.printLog+(SystemSetting.data.printLog?ld.yes:ld.no),()->18,()->0,()->340),
    };
  }
}