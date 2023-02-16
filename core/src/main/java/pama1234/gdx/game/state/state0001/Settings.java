package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.util.net.ServerInfo;

public class Settings extends StateEntity0001{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?>[] sliders;
  // public TextField[] screenTextFields;
  public TextField[] camTextFields;
  public int tx,ty;
  public Settings(Screen0011 p) {
    super(p);
    sliders=new Slider[4];
    buttons=UiGenerator.genButtons_0004(p);
    buttonsCam=genButtons_0006(p,this);
    initSliders();
    // screenTextFields=UiGenerator.genTextFields_0001(p);
    camTextFields=UiGenerator.genTextFields_0002(p);
  }
  public void initSliders() {
    sliders[0].pos=p.settings.volume;
    sliders[1].pos=p.settings.gyroscopeSensitivity;
    sliders[2].pos=p.settings.gyroscopeSensitivity;
    sliders[3].pos=p.settings.gConst;
  }
  @Override
  public void from(State0001 in) {
    if(p.settings.serverInfo==null) p.settings.serverInfo=new ServerInfo("127.0.0.1",12347);
    camTextFields[0].setText(p.settings.serverInfo.toString());
    p.backgroundColor(0);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
    // for(TextField e:screenTextFields) p.screenStage.addActor(e);
    for(TextField e:camTextFields) p.camStage.addActor(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
    // for(TextField e:screenTextFields) e.remove();
    for(TextField e:camTextFields) e.remove();
    p.cam2d.minScale=1;
    p.cam2d.testScale();
    p.settings.serverInfo.setFromString(camTextFields[0].getText(),12347);
  }
  @Override
  public void displayCam() {
    tx=-128;
    ty=0;
    text(p.gyroscope?"陀螺仪：  可用":"陀螺仪：不可用");
    text(p.compass?"指南针：  可用":"指南针：不可用");
    text(p.accelerometer?"加速计：  可用":"加速计：不可用");
    if(p.localHost!=null) p.text("本设备的名称与内网IP地址："+p.localHost.toString(),0,-60);
    if(p.settings.debugInfo) debugText();
  }
  public void text(String in) {
    p.text(in,tx,ty);
    line();
  }
  public void line() {
    ty+=18;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
  public void debugText() {
    tx=-256;
    if(p.gyroscope) {
      line();
      text("陀螺仪 X: "+Gdx.input.getGyroscopeX());
      text("陀螺仪 Y: "+Gdx.input.getGyroscopeY());
      text("陀螺仪 Z: "+Gdx.input.getGyroscopeZ());
    }
    if(p.compass) {
      line();
      text("指南针 X: "+Gdx.input.getPitch());
      text("指南针 Y: "+Gdx.input.getRoll());
      text("指南针 Z: "+Gdx.input.getAzimuth());
      line();
      text("重力   X: "+p.gVel.x);
      text("重力   Y: "+p.gVel.y);
      text("重力   Z: "+p.gVel.z);
    }
    if(p.accelerometer) {
      line();
      text("加速计 X: "+Gdx.input.getAccelerometerX());
      text("加速计 Y: "+Gdx.input.getAccelerometerY());
      text("加速计 Z: "+Gdx.input.getAccelerometerZ());
    }
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
        if(!p.gyroscope) return;
        p.settings.useGyroscope=!p.settings.useGyroscope;
        self.updateText();
      },self->self.text="使用陀螺仪："+(p.settings.useGyroscope?"是":"否"),()->18,()->0,()->180),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        if(!p.accelerometer) return;
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
}