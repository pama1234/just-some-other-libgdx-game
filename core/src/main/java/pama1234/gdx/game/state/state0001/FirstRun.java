package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class FirstRun extends StateEntity0001{
  public FirstRun(Screen0011 p) {
    super(p);
  }
  @Override
  public void init() {
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
    // p.text("FirstRun",0,0);
  }
}