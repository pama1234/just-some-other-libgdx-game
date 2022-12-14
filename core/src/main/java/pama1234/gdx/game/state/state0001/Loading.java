package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifLoader;
import pama1234.gdx.game.asset.ImageLoader;
import pama1234.gdx.game.asset.MusicLoader;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class Loading extends StateEntity0001{
  public int frame;
  public Loading(Screen0011 p) {
    super(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.textColor(255);
    frame=p.frameCount;
    // new Thread().start();
    Gdx.app.postRunnable(()-> {
      GifLoader.load_0001();
      MusicLoader.load_0001();
      ImageLoader.load_0001();
      p.state(State0001.StartMenu);
    });
  }
  // @Override
  // public void displayCam() {
  //   p.text("加载中",0,0);
  // }
  @Override
  public void display() {
    String text="加载中 "+frame;
    p.text(text,(p.width-p.textWidth(text))/2f,(p.height-p.pu)/2f);
  }
  @Override
  public void update() {
    frame++;
  }
}