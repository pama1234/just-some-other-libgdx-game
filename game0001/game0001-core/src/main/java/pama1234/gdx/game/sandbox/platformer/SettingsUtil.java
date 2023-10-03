package pama1234.gdx.game.sandbox.platformer;

import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.Tools;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.setting.Settings;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.math.geometry.RectF;

public class SettingsUtil{
  /**
   * 生成语言设置文本框
   * 
   * @param p
   * @return
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
   * 生成旧版设置
   * 
   * @param <T>
   * @param p
   * @param ps
   * @param sliders
   * @return
   */
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0006(T p,Settings ps,Slider<?>[] sliders) {
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.refreshLocalHost();
        // self.updateText();
      },self->self.text="刷新本机网络地址",()->18,()->0,()->-40),
      // 重置设置
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.initSettings();
        p.saveSettings();
        for(TextButtonCam<?> e:ps.buttonsCam) e.updateText();
        self.updateText();
      },self->self.text=ld.resetSettings,()->18,()->0,()->-100),
      // 重启游戏
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        MainApp.instance.restartScreen();
      },self->self.text=ld.restart,()->18,()->0,()->-120),
      // 静音
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.mute=!p.settings.mute;

        if(p.settings.mute) MusicAsset.moonlightSonata.pause();
        else MusicAsset.moonlightSonata.play();

        self.updateText();
      },self->self.text=p.settings.mute?ld.muteYes:ld.muteNo,()->18,()->0,()->0),
      // 音量控制条
      sliders[0]=new Slider<T>(p,true,()->true,self-> {
        p.settings.volume=ps.sliders[0].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.volume+String.format("%6.2f",p.settings.volume*100),()->18,()->0,()->20,1),
      // 调试信息
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugInfo=!p.settings.debugInfo;
        p.debugInfoChange();
        self.updateText();
      },self->self.text=p.settings.debugInfo?ld.debugInfoYes:ld.debugInfoNo,()->18,()->0,()->40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugGraphics=!p.settings.debugGraphics;
        Game game=p.stateCenter.game;
        if(p.settings.debugGraphics) game.createDebugDisplay();
        self.updateText();
      },self->self.text=p.settings.debugGraphics?ld.debugGraphicsYes:ld.debugGraphicsNo,()->18,()->0,()->60),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        boolean firstInit=(p.stateCenter.game).firstInit;
        if(!firstInit) (p.stateCenter.game).world().pauseSave();//判断有没有初始化
        p.state(p.stateCenter.loading);
        //由于Loading中会把firstInit修改为true,所以要修改会原来的值，否则就会因为线程再次启动而崩溃
        (p.stateCenter.game).firstInit=firstInit;
      },self->self.text=ld.reloadAssets,()->18,()->0,()->80),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text=ld.systemGc,()->18,()->0,()->100),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showEarth=!p.settings.showEarth;
        if(p.settings.showEarth&&ImageAsset.bigEarth==null) ImageAsset.loadEarth();
        self.updateText();
      },self->self.text=ld.displayEarthInStartMenu+(p.settings.showEarth?ld.yes:ld.no),()->18,()->0,()->120),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.zoomButton=!p.settings.zoomButton;
        if(p.settings.zoomButton) for(TextButton<?> e:p.buttons) p.centerScreen.add.add(e);
        else for(TextButton<?> e:p.buttons) p.centerScreen.remove.add(e);
        self.updateText();
      },self->self.text=ld.showZoomButton+(p.settings.zoomButton?ld.yes:ld.no),()->18,()->0,()->140),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.debug);
      },self->self.text=ld.debugView,()->18,()->0,()->160),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.gyroscopeAvailable) return;
        p.settings.useGyroscope=!p.settings.useGyroscope;
        self.updateText();
      },self->self.text=ld.useGyroscope+(p.settings.useGyroscope?ld.yes:ld.no),()->18,()->0,()->180),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.accelerometerAvailable) return;
        p.settings.useAccelerometer=!p.settings.useAccelerometer;
        self.updateText();
      },self->self.text=ld.useAccelerometer+(p.settings.useAccelerometer?ld.yes:ld.no),()->18,()->0,()->200),
      sliders[1]=new Slider<T>(p,true,()->true,self-> {
        p.settings.gyroscopeSensitivity=ps.sliders[1].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.gyroscopeSensitivity+String.format("%5.2f",p.settings.gyroscopeSensitivity),()->18,()->0,()->220,1,-10,10),
      sliders[2]=new Slider<T>(p,true,()->true,self-> {
        p.settings.accelerometerSensitivity=ps.sliders[2].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.accelerometerSensitivity+String.format("%5.2f",p.settings.accelerometerSensitivity),()->18,()->0,()->240,1,-10,10),
      sliders[3]=new Slider<T>(p,true,()->true,self-> {
        p.settings.gConst=ps.sliders[3].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.gravitationalConstant+String.format("%5.2f",p.settings.gConst),()->18,()->0,()->260,1,9.5f,10f),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.overridePlatform=!p.settings.overridePlatform;
        self.updateText();
      },self->self.text=ld.setDebugPlatformType+(p.settings.overridePlatform?ld.yes:ld.no),()->18,()->0,()->280) {
        @Override
        public void display() {
          super.display();
          p.text(ld.needRestart,ps.needRestartTextPosX,280);
        }
      },
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.isAndroid=!p.settings.isAndroid;
        self.updateText();
      },self->self.text=ld.overridePlatform+(p.settings.isAndroid?ld.phone:ld.personalComputer),()->18,()->0,()->300),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showLog=!p.settings.showLog;
        self.updateText();
        p.checkNeedLog();
      },self->self.text=ld.showLog+(p.settings.showLog?ld.yes:ld.no),()->18,()->0,()->320),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.printLog=!p.settings.printLog;
        self.updateText();
      },self->self.text=ld.printLog+(p.settings.printLog?ld.yes:ld.no),()->18,()->0,()->340),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.ctrlButton=!p.settings.ctrlButton;
        self.updateText();
      },self->self.text=ld.androidPlayerControlUi+(p.settings.ctrlButton?ld.button:ld.joystick),()->18,()->0,()->360),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.pixelPerfectGlobal=p.settings.pixelPerfectGlobal==CameraController2D.SMOOTH?CameraController2D.NONE:CameraController2D.SMOOTH;
        self.updateText();
      },self->self.text="全局精准像素："+(p.settings.pixelPerfectGlobal==CameraController2D.SMOOTH?ld.yes:ld.no),()->18,()->0,()->380),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.pixelPerfectIngame=p.settings.pixelPerfectIngame==CameraController2D.SMOOTH?CameraController2D.NONE:CameraController2D.SMOOTH;
        self.updateText();
      },self->self.text="游戏内精准像素："+(p.settings.pixelPerfectIngame==CameraController2D.SMOOTH?ld.yes:ld.no),()->18,()->0,()->400),
    };
  }
}