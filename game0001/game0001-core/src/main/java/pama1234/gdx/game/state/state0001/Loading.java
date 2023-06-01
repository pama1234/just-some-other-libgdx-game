package pama1234.gdx.game.state.state0001;

import static pama1234.gdx.game.state.state0001.Settings.ld;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.util.ProgressBar;
import pama1234.math.Tools;

public class Loading extends StateEntity0001{
  public int frame;
  public AssetManager manager;
  public ProgressBar<Screen0011> progress;
  //---
  public String text;
  public float textWidth;
  public Loading(Screen0011 p) {
    super(p);
    manager=new AssetManager();
    Texture.setAssetManager(manager);
  }
  @Override
  public void from(State0001 in) {
    manager.clear();//如果先前有残留的资源，先清除
    p.centerScreen.add.add(progress=new ProgressBar<Screen0011>(p,manager));
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
    //---
    // updateTextAndTextWidth();
  }
  public void updateTextAndTextWidth() {
    updateText();
    textWidth=p.textWidth(text);
  }
  public void updateText() {
    text=ld.loading+String.format("% 3d",frame)+ld.tick+String.format("%5s",Tools.cutToLastDigit(manager.getProgress()*100))+"%";
  }
  @Override
  public void to(State0001 in) {
    frame=0;
    p.centerScreen.remove.add(progress);
  }
  @Override
  public void display() {
    textWidth=p.textWidth(text);
    p.text(text,(p.width-textWidth)/2f,(p.height-p.pu)/2f);
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
    updateTextAndTextWidth();
  }
  @Override
  public void dispose() {
    manager.dispose();
  }
  @Override
  public void frameResized(int w,int h) {
    // textWidth=p.textWidth(text);
  }
}
