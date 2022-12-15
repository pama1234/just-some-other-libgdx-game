package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;
import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.math.Tools;

public class FirstRun extends StateEntity0001{
  public TinyVG logo0002;
  public TinyVGShapeDrawer drawer;
  // public SVGTexture logo;
  public FirstRun(Screen0011 p) {
    super(p);
    Tools.time();
    TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
    System.out.println(Tools.period());
    logo0002=assetLoader.load("tvg/logo0002.tvg");
    // tvg=assetLoader.load("tvg/pig.tvg");
    System.out.println(Tools.period());//13s
    drawer=new TinyVGShapeDrawer(new SpriteBatch());
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
    drawer.getBatch().setProjectionMatrix(p.cam2d.camera.combined);
    // drawer.setColor(1f,1f,1f,.5f);
    // tvg.setRotation(45f);
    // tvg.setScale(2f);
    // tvg.centerOrigin();
    // tvg.setShearX(.25f);
    // tvg.setClipBasedOnTVGSize(false);
    drawer.getBatch().begin();
    logo0002.draw(drawer);
    drawer.getBatch().end();
  }
}