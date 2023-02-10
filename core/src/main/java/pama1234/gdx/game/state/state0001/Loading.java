package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.math.Tools;

public class Loading extends StateEntity0001{
  public int frame;
  public AssetManager manager;
  public Loading(Screen0011 p) {
    super(p);
    manager=new AssetManager();
    Texture.setAssetManager(manager);
  }
  @Override
  public void from(State0001 in) {
    manager.clear();//如果先前有残留的资源，先清除
    ((Game)State0001.Game.entity).firstInit=true;
    p.backgroundColor(0);
    p.textColor(255);
    // SVGAssets.init();
    if(p.settings.showEarth) {
      GifAsset.setLoader(manager);
      GifAsset.load_0001(manager);
    }
    TvgAsset.setLoader(manager);
    TvgAsset.load_0001(manager);
    MusicAsset.load_0001(manager);
    ImageAsset.load_0001(manager);
  }
  @Override
  public void to(State0001 in) {
    frame=0;
  }
  @Override
  public void display() {
    String text="加载中 "+frame+"tick "+Tools.cutToLastDigit(manager.getProgress()*100)+"%";
    p.text(text,(p.width-p.textWidth(text))/2f,(p.height-p.pu)/2f);
  }
  @Override
  public void update() {
    frame++;
    if(manager.update()) {
      if(p.settings.showEarth) GifAsset.put_0001(manager);
      TvgAsset.put_0001(manager);
      MusicAsset.put_0001(manager);
      ImageAsset.put_0001(manager);
      p.state(State0001.StartMenu);
    }
  }
  @Override
  public void dispose() {
    manager.dispose();
  }
}