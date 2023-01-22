package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;

public class StartMenu extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public float time;
  public StartMenu(Screen0011 p) {
    super(p);
    buttons=UiGenerator.genButtons_0003(p);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(255);
    p.textColor(255);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    if(!p.mute) {
      MusicAsset.moonlightSonata.setLooping(true);
      MusicAsset.moonlightSonata.setVolume(p.volume);
      MusicAsset.moonlightSonata.play();
    }
    p.cam2d.active(false);
    p.cam2d.scale.des=3;
    p.cam2d.scale.pos=3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(96,0,0);
    // p.cam.noGrab();
    frameResized(p.width,p.height);
  }
  @Override
  public void to(State0001 in) {
    MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    p.cam2d.active(true);
    p.cam2d.scale.pos=1;
    p.cam2d.scale.des=1;
    p.cam2d.point.des.set(0,0,0);
    p.cam2d.point.pos.set(0,0,0);
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
      // p.println(p.textSize(),p.textScale());
      p.textColor(0);
      p.textScale(4);
      // p.text("空想世界",96-128,-32);
      // p.text("空想",96-64,-48);
      // p.text("世界",96-64,32);
      p.text("空想",-32,-64);
      p.text("世界",-32,16);
      p.textScale(1);
      p.textColor(255);
    }
  }
  float tx,tw;
  @Override
  public void display() {
    if(p.isAndroid) return;
    p.beginBlend();
    p.fill(60,136,136,127);
    p.rect(tx,0,tw,p.height);
    p.fill(120,200,196,127);
    p.rect(tx,p.height/4f-p.bu/2f,tw,p.bu);
    p.rect(tx,p.height/2f-p.bu/2f,tw,p.bu);
    p.rect(tx,p.height/4f*3-p.bu/2f,tw,p.bu);
    p.endBlend();
  }
  @Override
  public void frameResized(int w,int h) {
    tx=w/8f*5;
    tw=w/4f;
  }
}