package pama1234.gdx.game.state.state0002;

import com.badlogic.gdx.assets.AssetManager;

import pama1234.gdx.game.app.app0004.Screen0012;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0002.StateGenerator0002.StateEntity0002;
import pama1234.math.Tools;

public class Loading extends StateEntity0002{
  public int frame;
  public AssetManager manager;
  public Loading(Screen0012 p) {
    super(p);
    manager=new AssetManager();
  }
  @Override
  public void from(State0002 in) {
    p.backgroundColor(0);
    p.textColor(255);
    // SVGAssets.init();
    // GifAsset.setLoader(manager);
    TvgAsset.setLoader(manager);
    // GifAsset.load_0001(manager);
    TvgAsset.load_0001(manager);
    MusicAsset.load_0001(manager);
    ImageAsset.load_0001(manager);
  }
  @Override
  public void to(State0002 in) {}
  @Override
  public void display() {
    String text="加载中 "+frame+"tick "+Tools.cutToLastDigit(manager.getProgress()*100)+"%";
    p.text(text,(p.width-p.textWidth(text))/2f,(p.height-p.pu)/2f);
  }
  @Override
  public void update() {
    frame++;
    if(manager.update()) {
      // GifAsset.put_0001(manager);
      TvgAsset.put_0001(manager);
      MusicAsset.put_0001(manager);
      ImageAsset.put_0001(manager);
      p.state(State0002.StartMenu);
    }
  }
}