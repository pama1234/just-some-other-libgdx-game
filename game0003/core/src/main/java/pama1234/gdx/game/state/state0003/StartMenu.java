package pama1234.gdx.game.state.state0003;

import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;
import pama1234.gdx.game.ui.generator.UiGenerator0003;
import pama1234.gdx.game.ui.util.Button;

public class StartMenu extends StateEntity0003{
  public Button<?>[] buttons;
  public StartMenu(Screen0016 p) {
    super(p);
    buttons=UiGenerator0003.genButtons_0003(p);
  }
  @Override
  public void from(State0003 in) {
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
  public void to(State0003 in) {
    MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    p.cam2d.active(true);
    p.cam2d.scale.pos=1;
    p.cam2d.scale.des=1;
    p.cam2d.point.des.set(0,0,0);
    p.cam2d.point.pos.set(0,0,0);
  }
  @Override
  public void update() {}
  @Override
  public void displayCam() {}
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