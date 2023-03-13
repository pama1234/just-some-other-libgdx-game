package pama1234.gdx.game.state.state0003;

import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;
import pama1234.math.Tools;

public class FirstRun extends StateEntity0003{
  float time;
  // float time=30;
  boolean moveCam;
  public FirstRun(Screen0016 p) {
    super(p);
    TvgAsset.load_temp();
    TvgAsset.logo0005.centerOrigin();
  }
  @Override
  public void from(State0003 in) {
    MusicAsset.alsoSprachZarathustra.setOnCompletionListener(music->p.state(State0003.Loading));
    MusicAsset.alsoSprachZarathustra.play();
    p.backgroundColor(0);
    p.textColor(255);
    p.cam2d.active(false);
    p.cam2d.scale.des=p.isAndroid?1/3f:1;
    p.cam2d.point.set(256,256,0);
    // p.textScale(2);
  }
  @Override
  public void to(State0003 in) {
    p.textScale(1);
    p.cam2d.active(true);
    p.cam2d.scale.des=1;
    p.cam2d.point.set(0,0,0);
  }
  @Override
  public void displayCam() {
    if(time>1) {
      p.tvg(TvgAsset.logo0005);
      if(time>18) {
        String text="嵌套宇宙研发组织";
        p.textScale(2);
        p.text(text,256-p.textWidth(text)/2,400+p.textSize()*2);
        if(time>30) {
          text="空想世界：PVP";
          p.text(text,256-p.textWidth(text)/2,400+p.textSize()*5);
        }
      }
    }
  }
  @Override
  public void display() {
    if(Tools.inRange(time,1,6)) {
      p.beginBlend();
      p.fill(0,(int)Tools.map(time,1,6,255,0));
      p.rect(0,0,p.width,p.height);
      p.endBlend();
    }
  }
  @Override
  public void update() {
    time+=p.frameRate;
    if(!moveCam&&time>29) {
      p.cam2d.point.set(256,320,0);
      moveCam=true;
    }
  }
}