package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.state.State0001;
import pama1234.gdx.game.state.State0001.StateChanger;
import pama1234.gdx.game.state.StateUtil0001;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0011 extends ScreenCore2D implements StateChanger{
  State0001 state;
  @Override
  public void setup() {
    noStroke();
    StateUtil0001.loadState0001(this);
    // Gdx.files.local("firstRun.txt").readString();
    // System.out.println(Gdx.files.local("firstRun.txt").readString());
    // System.out.println(Gdx.files.local("data/firstRun.txt").exists());
    if(Gdx.files.local("data/firstRun.txt").exists()) {
      state(State0001.Loading);
      // Gdx.files.local("data/firstRun.txt").readString();
      System.out.println(Gdx.files.local("data/firstRun.txt").readString());
    }else {
      state(State0001.FirstRun);
      Gdx.files.local("data/firstRun.txt").writeString("1234",false);
    }
  }
  @Override
  public State0001 state(State0001 in) {
    center.add.add(in);
    State0001 out=state;
    state=in;
    if(out!=null) center.remove.add(out);
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
