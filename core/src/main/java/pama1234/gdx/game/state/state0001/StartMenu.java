package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class StartMenu extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public float time;
  public StartMenu(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0003(p);
  }
  @Override
  public void init() {
    p.backgroundColor(255);
    p.textColor(255);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    MusicAsset.moonlightSonata.setLooping(true);
    MusicAsset.moonlightSonata.play();
    p.cam2d.active(false);
    p.cam2d.scale.des=3;
    p.cam2d.point.set(96,0,0);
    p.cam.noGrab();
  }
  @Override
  public void dispose() {
    MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    p.cam2d.active(true);
    p.cam2d.scale.des=1;
    p.cam2d.point.set(0,0,0);
  }
  @Override
  public void update() {
    time+=p.frameRate/4;
  }
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
    TextureRegion kf=GifAsset.bigEarth.getKeyFrame(time);
    p.image(kf,-128,-128);
  }
  @Override
  public void display() {
    p.fill(0);
    // rect(width/8*5,0,width/4,frameCount<height?sq(frameCount):height);
    p.rect(p.width/8f*5,0,p.width/4f,p.height);
    p.fill(63);
    p.rect(p.width/8f*5,p.height/4f-p.bu/2f,p.width/4f,p.bu);
    p.rect(p.width/8f*5,p.height/2f-p.bu/2f,p.width/4f,p.bu);
    p.rect(p.width/8f*5,p.height/4f*3-p.bu/2f,p.width/4f,p.bu);
  }
}