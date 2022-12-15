package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class FirstRun extends StateEntity0001{
  float time;
  // float time=30;
  public FirstRun(Screen0011 p) {
    super(p);
    // Tools.time();
    TvgAsset.load_temp();
    // TvgAsset.logo0003.setClipBasedOnTVGSize(true);
    TvgAsset.logo0004.centerOrigin();
    // TvgAsset.logo0003.setRotation(45f);
    // TvgAsset.logo0003.setScale(2f);
    // TvgAsset.logo0003.setShearX(.25f);
    // System.out.println(Tools.period());
  }
  @Override
  public void init() {
    MusicAsset.alsoSprachZarathustra.setOnCompletionListener(music->p.state(State0001.Loading));
    MusicAsset.alsoSprachZarathustra.play();
    // MusicAsset.alsoSprachZarathustra.setVolume(0);
    p.backgroundColor(0);
    p.textColor(255);
    p.cam2d.active(false);
    p.cam2d.scale.des=1;
    p.cam2d.point.set(256,256,0);
    // p.textScale(2);
  }
  @Override
  public void dispose() {
    p.textScale(1);
    p.cam2d.active(true);
    p.cam2d.scale.des=1;
    p.cam2d.point.set(0,0,0);
  }
  @Override
  public void displayCam() {
    if(time>6) {
      p.tvgDrawer.getBatch().begin();
      TvgAsset.logo0004.draw(p.tvgDrawer);
      p.tvgDrawer.getBatch().end();
      if(time>18) {
        String text="嵌套宇宙研发组织";
        p.textScale(2);
        p.text(text,256-p.textWidth(text)/2,400+p.textSize()*2);
        if(time>30) {
          text="测试游戏：喵喵大冒险0001";
          p.text(text,256-p.textWidth(text)/2,400+p.textSize()*5);
        }
      }
    }
    // p.text("time "+time,0,0);
  }
  @Override
  public void update() {
    time+=p.frameRate;
  }
}