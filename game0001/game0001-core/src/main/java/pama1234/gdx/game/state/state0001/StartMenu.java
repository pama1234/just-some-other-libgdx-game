package pama1234.gdx.game.state.state0001;

import static pama1234.gdx.game.state.state0001.Settings.ld;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Button.ButtonEvent;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.util.function.GetFloat;

public class StartMenu extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public float time;
  public StartMenu(Screen0011 p,int id) {
    super(p,id);
    buttons=genButtons_0003(p);
  }
  @Override
  public void from(StateEntity0001 in) {
    p.backgroundColor(0);
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
    frameResized(p.width,p.height);
  }
  @Override
  public void to(StateEntity0001 in) {
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
  public static <T extends Screen0011> Button<?>[] genButtons_0003(T p) {
    GetFloat getX=()->p.width/4f*3-p.pu*2.5f;
    ButtonEvent nop=self-> {};
    return new Button[] {
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        p.state(p.stateCenter.GameMenu);
      },self->self.text=ld.startGame,p::getButtonUnitLength,getX,()->p.height/4f-p.bu/2f),
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        p.state(p.stateCenter.Announcement);
      },self->self.text=ld.announcement,p::getButtonUnitLength,getX,()->p.height/2f-p.bu/2f),
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        p.state(p.stateCenter.Settings);
      },self->self.text=ld.settings,p::getButtonUnitLength,getX,()->p.height/4f*3-p.bu/2f),
    };
  }
}