package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.math.Tools;

public class FirstRun extends StateEntity0001{
  public FirstRun(Screen0011 p) {
    super(p);
    Tools.time();
    TvgAsset.load_temp();
    // TvgAsset.logo0003.setClipBasedOnTVGSize(true);
    TvgAsset.logo0003.centerOrigin();
    // TvgAsset.logo0003.setRotation(45f);
    // TvgAsset.logo0003.setScale(2f);
    // TvgAsset.logo0003.setShearX(.25f);
    System.out.println(Tools.period());
  }
  @Override
  public void init() {
    MusicAsset.alsoSprachZarathustra.setOnCompletionListener(music->p.state(State0001.Loading));
    MusicAsset.alsoSprachZarathustra.play();
    // MusicAsset.alsoSprachZarathustra.setVolume(0);
    p.backgroundColor(0);
  }
  @Override
  public void displayCam() {
    p.tvgDrawer.getBatch().begin();
    TvgAsset.logo0003.draw(p.tvgDrawer);
    p.tvgDrawer.getBatch().end();
  }
}