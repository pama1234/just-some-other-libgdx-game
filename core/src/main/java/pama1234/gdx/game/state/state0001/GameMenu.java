package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;

public class GameMenu extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public float time;
  public GameMenu(Screen0011 p) {
    super(p);
    buttons=genButtons_0010(p);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    // p.textColor(255);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    if(!p.settings.mute) {
      MusicAsset.moonlightSonata.setLooping(true);
      MusicAsset.moonlightSonata.setVolume(p.settings.volume);
      MusicAsset.moonlightSonata.play();
    }
    p.cam2d.active(false);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid&&!p.settings.showEarth?1:3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    // p.cam.noGrab();
    frameResized(p.width,p.height);
  }
  @Override
  public void to(State0001 in) {
    MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
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
      TextureRegion kf=GifAsset.bigEarth.getKeyFrame(time);
      p.image(kf,-128,-128);
    }else {
      // p.textColor(0);
      // p.textColor(255);
      p.textScale(4);
      p.text("空想",-32,-80);
      p.text("世界",-32,0);
      p.textScale(1);
      // p.textColor(255);
    }
  }
  float tx,tw;
  @Override
  public void display() {}
  @Override
  public void frameResized(int w,int h) {
    tx=w/8f*5;
    tw=w/4f;
  }
  public static <T extends Screen0011> Button<?>[] genButtons_0010(T p) {
    return new Button[] {
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },self->self.text="　　返回　　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/5f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.Game);
      },self->self.text="　单人游戏　",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/5*2f-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },self->self.text="开启联机房间",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/5f*3-p.bu/2f),
      new TextButton<T>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(State0001.StartMenu);
      },self->self.text="加入联机房间",p::getButtonUnitLength,()->p.width/4f*3-p.pu*2.5f,()->p.height/5f*4-p.bu/2f),
    };
  }
}