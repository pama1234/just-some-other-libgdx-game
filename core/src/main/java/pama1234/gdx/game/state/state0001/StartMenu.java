package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;

public class StartMenu extends StateEntity0001{
  // public TextureRegion background;
  public Button<?>[] buttons;
  //---
  public float time;
  public StartMenu(Screen0011 p) {
    super(p);
    buttons=UiGenerator.genButtons_0003(p);
    // Graphics tg=new Graphics(p,512,512);
    // tg.beginDraw();
    // // p.beginBlend();
    // p.fill(64,64,255);
    // p.circle(256,256,240);
    // p.fill(0);
    // p.circle(256,256,160);
    // // for(int i=0;i<28;i++) {
    // //   p.fill(255,i*4);
    // //   p.circle(256,256,140-i/2f);
    // // }
    // // p.endBlend();
    // tg.endDraw();
    // background=new TextureRegion(tg.texture);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    p.textColor(255);
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
      // p.tint(127);
      // p.image(kf,-128-4,-128-4,kf.getRegionWidth()+8,kf.getRegionHeight()+8);//256 256
      // p.image(background,-256,-256);
      // p.noTint();
      p.image(kf,-128,-128);
    }else {
      p.textColor(0);
      p.textScale(4);
      p.text("空想",-32,-80);
      p.text("世界",-32,0);
      p.textScale(1);
      p.textColor(255);
    }
  }
  float tx,tw;
  @Override
  public void display() {
    // if(p.isAndroid) return;
    // p.beginBlend();
    // p.fill(60,136,136,127);
    // p.rect(tx,0,tw,p.height);
    // p.fill(120,200,196,127);
    // p.rect(tx,p.height/4f-p.bu/2f,tw,p.bu);
    // p.rect(tx,p.height/2f-p.bu/2f,tw,p.bu);
    // p.rect(tx,p.height/4f*3-p.bu/2f,tw,p.bu);
    // p.endBlend();
  }
  @Override
  public void frameResized(int w,int h) {
    tx=w/8f*5;
    tw=w/4f;
  }
}