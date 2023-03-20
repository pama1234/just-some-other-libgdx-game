package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.app.Screen0012;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0002.StateGenerator0002.StateEntity0002;
import pama1234.gdx.game.ui.generator.ButtonGenerator0002;
import pama1234.gdx.game.ui.util.Button;

public class StartMenu extends StateEntity0002{
  public Button<?>[] buttons;
  //---
  // public float time;
  public StartMenu(Screen0012 p) {
    super(p);
    buttons=ButtonGenerator0002.genButtons_0003(p);
  }
  @Override
  public void from(State0002 in) {
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
  public void to(State0002 in) {
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
    // time+=p.frameRate/4;
  }
  @Override
  public void displayCam() {
    // TextureRegion kf=GifAsset.bigEarth.getKeyFrame(time);
    // p.image(kf,-128,-128);
  }
  float tx,tw;
  @Override
  public void display() {
    if(p.isAndroid) return;
    p.fill(0);
    p.rect(tx,0,tw,p.height);
    p.fill(63);
    p.rect(tx,p.height/4f-p.bu/2f,tw,p.bu);
    p.rect(tx,p.height/2f-p.bu/2f,tw,p.bu);
    p.rect(tx,p.height/4f*3-p.bu/2f,tw,p.bu);
  }
  @Override
  public void frameResized(int w,int h) {
    tx=w/8f*5;
    tw=w/4f;
  }
}