package pama1234.gdx.game.state.state0001;

import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.SystemSetting;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButton.TextButtonEvent;
import pama1234.util.function.GetFloat;

public class StartMenu extends StateEntity0001{
  public Button<?>[] buttons;

  public float time;

  public float[] alignmentConstY;
  public StartMenu(Screen0011 p,int id) {
    super(p,id);
    buttons=genButtons_0003(p);
  }

  @Override
  public void from(StateEntity0001 in) {
    p.backgroundColor(0);
    Collections.addAll(p.centerScreen.add,buttons);
    if(!SystemSetting.data.mute) {
      MusicAsset.moonlightSonata.setLooping(true);
      MusicAsset.moonlightSonata.setVolume(SystemSetting.data.volume);
      MusicAsset.moonlightSonata.play();
    }
    // p.cam2d.active(false);
    p.cam2d.activeDrag=false;
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid&&!p.settings.showEarth?1:3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    frameResized(p.width,p.height);
  }
  @Override
  public void to(StateEntity0001 in) {
    // MusicAsset.moonlightSonata.pause();
    Collections.addAll(p.centerScreen.remove,buttons);
    p.cam2d.active(true);
    p.cam2d.scale.pos=p.cam2d.scale.des=1;
    p.cam2d.point.des.set(0,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
  }
  @Override
  public void update() {
    time+=p.frameRate/4;
  }
  @Override
  public void displayCam() {
    if(p.settings.showEarth) {
      TextureRegion kf=ImageAsset.bigEarth.getKeyFrame(time);
      p.image(kf,-128,-128);
    }else {
      p.textColor(255,220);
      p.textScale(4);
      p.text("空想",-32,-80);
      p.text("世界",-32,0);
      p.textScale(1);
    }
  }
  @Override
  public void display() {}
  @Override
  public void frameResized(int w,int h) {}

  /**
   * 开始界面右侧的按钮
   * 
   * @param p   parent screen
   * @param <T> type
   * @return 按钮
   */
  public <T extends Screen0011> Button<?>[] genButtons_0003(T p) {
    GetFloat getX=()->p.width/4f*3-p.pu*2.5f;
    alignmentConstY=new float[4];
    //    getAlignmentConstY(p.settings.ideMode?4:3,alignmentConstY);
    ideModeChange();
    GetFloat[] getY=new GetFloat[] {
      ()->p.height/2f+p.bu*alignmentConstY[0],
      ()->p.height/2f+p.bu*alignmentConstY[1],
      ()->p.height/2f+p.bu*alignmentConstY[2],
      ()->p.height/2f+p.bu*alignmentConstY[3],
    };
    TextButtonEvent<T> nop=self-> {};
    return new Button[] {
      new TextButton<>(p,self->self.text=ld.startGame,()->true,true)
        .allButtonEvent(nop,nop,self-> {
          p.state(p.stateCenter.gameMenu);
        })
        .rectAuto(getX,getY[0]),
      new TextButton<>(p,self->self.text=ld.announcement,()->true,true)
        .allButtonEvent(nop,nop,self-> {
          p.state(p.stateCenter.announcement);
        })
        .rectAuto(getX,getY[1]),
      new TextButton<>(p,self->self.text=ld.settings,()->true,true)
        .allButtonEvent(nop,nop,self-> {
          p.state(p.stateCenter.settings);
        })
        .rectAuto(getX,getY[2]),
      new TextButton<>(p,self->self.text=ld.editor,()->p.settings.ideMode,true)
        .allButtonEvent(nop,nop,self-> {
          p.state(p.stateCenter.editor);
        })
        .rectAuto(getX,getY[3]),
    };
  }
  public void ideModeChange() {
    getAlignmentConstY(p.settings.ideMode?4:3,alignmentConstY);
  }

  /**
   * 根据需要的数量生成一个用于“将按钮居中并带有间隔排列”的浮点数数组
   * 
   * @param in 按钮数量
   * @return 浮点数数组
   */
  public static float[] getAlignmentConstY(int in,float[] out) {
    //    float[] out=new float[in];
    float topY=-(in*1.2f-0.2f)/2f;
    for(int i=0;i<in;i++) {
      out[i]=topY+i*1.2f;
    }
    return out;
  }
}