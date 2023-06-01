package pama1234.gdx.game.state.state0001.settings;

import static pama1234.gdx.game.state.state0001.Settings.ld;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.Settings;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.launcher.MainApp;

public class SettingsUtil{
  public static TextField[] genTextFields_0002(Screen0011 p) {
    TextField[] out=new TextField[] {new TextField(p.settings.langType,new CodeTextFieldStyle(p),
      new RectF(()->0,()->-20,()->256,()->18),
      ()->1)};
    out[0].setMessageText("语言设置");
    out[0].addListener(new FocusListener() {
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        if(!focused) p.settings.langType=out[0].getText();
      }
    });
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
    // return new TextField[0];
  }
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0006(T p,Settings ps) {
    float resetSettingsButtonPosX=-p.textWidth(ld.resetSettings)-24;//-100
    float restartButtonPosX=-p.textWidth(ld.restart)-24;
    return new TextButtonCam[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.initSettings();
        p.saveSettings();
        for(TextButtonCam<?> e:ps.buttonsCam) e.updateText();
        self.updateText();
      },self->self.text=ld.resetSettings,()->18,()->resetSettingsButtonPosX,()->-20),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        MainApp.instance.restartScreen();
      },self->self.text=ld.restart,()->18,()->restartButtonPosX,()->-40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.mute=!p.settings.mute;
        self.updateText();
      },self->self.text=p.settings.mute?ld.muteYes:ld.muteNo,()->18,()->0,()->0),
      ps.sliders[0]=new Slider<T>(p,true,()->true,self-> {
        p.settings.volume=ps.sliders[0].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.volume+String.format("%6.2f",p.settings.volume*100),()->18,()->0,()->20,1),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugInfo=!p.settings.debugInfo;
        p.debugInfoChange();
        self.updateText();
      },self->self.text=p.settings.debugInfo?"显示文本调试信息：是":"显示文本调试信息：否",()->18,()->0,()->40),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.debugGraphics=!p.settings.debugGraphics;
        Game game=(Game)State0001.Game.entity;
        if(p.settings.debugGraphics) game.createDebugDisplay();
        self.updateText();
      },self->self.text=p.settings.debugGraphics?"显示图形调试信息：是":"显示图形调试信息：否",()->18,()->0,()->60),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        boolean firstInit=((Game)State0001.Game.entity).firstInit;
        if(!firstInit) ((Game)State0001.Game.entity).world().pauseSave();//判断有没有初始化
        p.state(State0001.Loading);
        //由于Loading中会把firstInit修改为true,所以要修改会原来的值，否则就会因为线程再次启动而崩溃
        ((Game)State0001.Game.entity).firstInit=firstInit;
      },self->self.text="重新加载图片素材",()->18,()->0,()->80),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        System.gc();
        Runtime.getRuntime().runFinalization();
      },self->self.text="清理内存垃圾",()->18,()->0,()->100),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showEarth=!p.settings.showEarth;
        if(p.settings.showEarth&&ImageAsset.bigEarth==null) ImageAsset.loadEarth();
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
        if(!p.gyroscopeAvailable) return;
        p.settings.useGyroscope=!p.settings.useGyroscope;
        self.updateText();
      },self->self.text="使用陀螺仪："+(p.settings.useGyroscope?"是":"否"),()->18,()->0,()->180),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.accelerometerAvailable) return;
        p.settings.useAccelerometer=!p.settings.useAccelerometer;
        self.updateText();
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
      },self->self.text="使用覆盖平台类型："+(p.settings.overridePlatform?"是":"否"),()->18,()->0,()->280),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.isAndroid=!p.settings.isAndroid;
        self.updateText();
      },self->self.text="覆盖平台类型："+(p.settings.isAndroid?"手机":"电脑"),()->18,()->0,()->300),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.refreshLocalHost();
        // self.updateText();
      },self->self.text="刷新本机网络地址",()->18,()->0,()->-20),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showLog=!p.settings.showLog;
        self.updateText();
        p.checkNeedLog();
      },self->self.text="显示日志信息："+(p.settings.showLog?"是":"否"),()->18,()->0,()->320),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.printLog=!p.settings.printLog;
        // p.debugInfoChange(p.settings.printLog);
        self.updateText();
      },self->self.text="打印日志信息："+(p.settings.printLog?"是":"否"),()->18,()->0,()->340),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.ctrlButton=!p.settings.ctrlButton;
        self.updateText();
      },self->self.text="安卓版玩家控制移动UI："+(p.settings.ctrlButton?"按钮":"摇杆"),()->18,()->0,()->360),
    };
  }
}