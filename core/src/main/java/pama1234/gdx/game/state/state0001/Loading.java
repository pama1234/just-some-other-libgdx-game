package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.assets.AssetManager;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class Loading extends StateEntity0001{
  public int frame;
  public AssetManager manager;
  public Loading(Screen0011 p) {
    super(p);
    manager=new AssetManager();
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.textColor(255);
    // frame=p.frameCount;
    // Gdx.app.postRunnable(()-> {
    //   GifLoader.setLoader(manager);
    //   GifLoader.load_0001(manager);
    //   MusicLoader.load_0001(manager);
    //   ImageLoader.load_0001(manager);
    //   // p.sleep(5000);
    //   // long time=0;
    //   // while(time<Long.MAX_VALUE) time++;
    //   p.state(State0001.StartMenu);
    // });
    GifAsset.setLoader(manager);
    GifAsset.load_0001(manager);
    MusicAsset.load_0001(manager);
    ImageAsset.load_0001(manager);
  }
  // @Override
  // public void displayCam() {
  //   p.text("加载中",0,0);
  // }
  @Override
  public void display() {
    String text="加载中 "+frame+" "+manager.getProgress()+"%";
    p.text(text,(p.width-p.textWidth(text))/2f,(p.height-p.pu)/2f);
    // System.out.println(text);
  }
  @Override
  public void update() {
    frame++;
    if(manager.update()) {
      GifAsset.put_0001(manager);
      MusicAsset.put_0001(manager);
      ImageAsset.put_0001(manager);
      p.state(State0001.StartMenu);
    }
  }
}