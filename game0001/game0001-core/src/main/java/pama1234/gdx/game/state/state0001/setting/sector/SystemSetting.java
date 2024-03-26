package pama1234.gdx.game.state.state0001.setting.sector;

import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.sandbox.platformer.CompositionUtilLocal;
import pama1234.gdx.game.state.state0001.StartMenu;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.state.state0001.setting.Settings;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.cam.CameraController2D;

import java.util.Collections;

public class SystemSetting extends SettingSector0011{
  @Override
  public void createButton(Screen0011 p,Settings ps) {
    // 此处生成设置中的按钮

    Slider<?>[] sliders=new Slider[4];
    TextButtonCam<?>[] tbs=genButtons_0006(p,ps,sliders);
    buttonsCam.addAll(CompositionUtilLocal.linearComposition(tbs,0,999,true,2));
  }
  /**
   * 生成系统设置
   *
   * @param <T>     泛型
   * @param p       parent screen
   * @param ps      parent settings class
   * @param sliders 滑动条
   * @return 按钮数组
   */
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0006(T p,Settings ps,Slider<?>[] sliders) {
    return new TextButtonCam[] {
      new TextButtonCam<>(p,()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.refreshLocalHost();
          // self.updateText();
        }).textSupplier(self->self.text="刷新本机网络地址")
        .rectAutoWidth(0,-40,18),
      // 静音
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        pama1234.gdx.SystemSetting.data.mute=!pama1234.gdx.SystemSetting.data.mute;

        if(pama1234.gdx.SystemSetting.data.mute) MusicAsset.moonlightSonata.pause();
        else MusicAsset.moonlightSonata.play();

        self.updateText();
      },self->self.text=pama1234.gdx.SystemSetting.data.mute?ld.muteYes:ld.muteNo)
        .rectHeight(18),
      // 音量控制条
      sliders[0]=new Slider<>(p,()->true,true)
        .allSliderEvent(self-> {
          pama1234.gdx.SystemSetting.data.volume=ps.sliders[0].pos;
          self.updateText();
        },self-> {},self-> {})
        .textSupplierSlider(self->self.text=ld.volume+String.format("%6.2f",pama1234.gdx.SystemSetting.data.volume*100))
        .rectAutoWidth(0,20,18)
        .pos(pama1234.gdx.SystemSetting.data.volume),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.zoomButton=!p.settings.zoomButton;
        if(p.settings.zoomButton) Collections.addAll(p.centerScreen.add,p.buttons);
        else Collections.addAll(p.centerScreen.remove,p.buttons);
        self.updateText();
      },self->self.text=ld.showZoomButton+(p.settings.zoomButton?ld.yes:ld.no),()->18,()->0,()->140),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.gyroscopeAvailable) return;
        p.settings.useGyroscope=!p.settings.useGyroscope;
        self.updateText();
      },self->self.text=ld.useGyroscope+(p.settings.useGyroscope?ld.yes:ld.no),()->18,()->0,()->180),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.accelerometerAvailable) return;
        p.settings.useAccelerometer=!p.settings.useAccelerometer;
        self.updateText();
      },self->self.text=ld.useAccelerometer+(p.settings.useAccelerometer?ld.yes:ld.no),()->18,()->0,()->200),
      sliders[1]=new Slider<>(p,true,()->true,self-> {
        p.settings.gyroscopeSensitivity=ps.sliders[1].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.gyroscopeSensitivity+String.format("%5.2f",p.settings.gyroscopeSensitivity),()->18,()->0,()->220,1,-10,10)
          .pos(p.settings.gyroscopeSensitivity),
      sliders[2]=new Slider<>(p,true,()->true,self-> {
        p.settings.accelerometerSensitivity=ps.sliders[2].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.accelerometerSensitivity+String.format("%5.2f",p.settings.accelerometerSensitivity),()->18,()->0,()->240,1,-10,10)
          .pos(p.settings.accelerometerSensitivity),
      sliders[3]=new Slider<>(p,true,()->true,self-> {
        p.settings.gConst=ps.sliders[3].pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text=ld.gravitationalConstant+String.format("%5.2f",p.settings.gConst),()->18,()->0,()->260,1,9.5f,10f)
          .pos(p.settings.gConst),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.pixelPerfectGlobal=p.settings.pixelPerfectGlobal==CameraController2D.SMOOTH?CameraController2D.NONE:CameraController2D.SMOOTH;
        self.updateText();
      },self->self.text="全局精准像素："+(p.settings.pixelPerfectGlobal==CameraController2D.SMOOTH?ld.yes:ld.no),()->18,()->0,()->380),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.pixelPerfectIngame=p.settings.pixelPerfectIngame==CameraController2D.SMOOTH?CameraController2D.NONE:CameraController2D.SMOOTH;
        self.updateText();
      },self->self.text="游戏内精准像素："+(p.settings.pixelPerfectIngame==CameraController2D.SMOOTH?ld.yes:ld.no),()->18,()->0,()->400),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.frameRateFix=!p.settings.frameRateFix;
        self.updateText();
      },self->self.text="帧率修复："+(p.settings.frameRateFix?ld.yes:ld.no),()->18,()->0,()->420),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.ideMode=!p.settings.ideMode;
        p.stateCenter.startMenu.ideModeChange();
        self.updateText();
      },self->self.text="编辑器模式："+(p.settings.ideMode?ld.yes:ld.no),()->18,()->0,()->440),
    };
  }
}
