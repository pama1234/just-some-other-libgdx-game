package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.math.Tools;

public class FirstRun extends StateEntity0001{
  // public TinyVGShapeDrawer drawer;
  // public SVGTexture logo;
  public FirstRun(Screen0011 p) {
    super(p);
    Tools.time();
    TvgAsset.load_temp();
    System.out.println(Tools.period());//13s
    // p.drawer=new TinyVGShapeDrawer(new SpriteBatch());
    System.out.println(Tools.period());
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
    // drawer.setColor(1f,1f,1f,.5f);
    // tvg.setRotation(45f);
    // tvg.setScale(2f);
    // tvg.centerOrigin();
    // tvg.setShearX(.25f);
    // tvg.setClipBasedOnTVGSize(false);
    p.tvgDrawer.getBatch().begin();
    TvgAsset.logo0002.draw(p.tvgDrawer);
    p.tvgDrawer.getBatch().end();
  }
}