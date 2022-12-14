package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.State0001.StateChanger;
import pama1234.gdx.game.state.state0001.StateGenerator0001;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0011 extends ScreenCore2D implements StateChanger{
  // public static boolean debugFirstRun=true;//TODO
  public State0001 state;
  @Override
  public void setup() {
    noStroke();
    MusicAsset.load_init();
    StateGenerator0001.loadState0001(this);
    if(Gdx.files.local("data/firstRun.txt").exists()) {
      // if(true) {
      state(State0001.Loading);
    }else {
      state(State0001.FirstRun);
      // if(!debugFirstRun)
      // if(false) 
      Gdx.files.local("data/firstRun.txt").writeString("1234",false);
    }
  }
  @Override
  public State0001 state(State0001 in) {
    in.from(state);
    in.init();
    centerScreen.add.add(in);
    centerCam.add.add(in.displayCam);
    State0001 out=state;
    state=in;
    if(out!=null) {
      centerScreen.remove.add(out);
      centerCam.remove.add(out.displayCam);
      out.to(in);
      out.dispose();
    }
    return out;
  }
  @Override
  public void update() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
