package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class FirstRun extends StateEntity0001{
  // public SVGTexture logo;
  public FirstRun(Screen0011 p) {
    super(p);
  }
  @Override
  public void init() {
    // SVGAssets.init();
    // SvgAsset.load_temp();
    // logo=new SVGTexture(SvgAsset.logo0001,512,512,SVGColor.Clear,false);
    MusicAsset.alsoSprachZarathustra.setOnCompletionListener(new OnCompletionListener() {
      @Override
      public void onCompletion(Music music) {
        p.state(State0001.Loading);
      }
    });
    MusicAsset.alsoSprachZarathustra.play();
    p.backgroundColor(0);
  }
  @Override
  public void displayCam() {
    // p.image(logo,0,0);
  }
}