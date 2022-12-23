package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.State0001.StateChanger;
import pama1234.gdx.game.state.state0001.StateGenerator0001;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;

public class Screen0011 extends ScreenCore2D implements StateChanger{
  public State0001 state;
  public boolean firstRun;
  public boolean debugInfo;
  public boolean mute;
  public float volume=1;
  @Override
  public void setup() {
    noStroke();
    MusicAsset.load_init();
    StateGenerator0001.loadState0001(this);
    firstRun=!Gdx.files.local("data/firstRun.txt").exists();
    // firstRun=true;
    if(firstRun) {
      state(State0001.FirstRun);
      Gdx.files.local("data/firstRun.txt").writeString("1234",false);
    }else {
      state(State0001.Loading);
    }
  }
  @Override
  public State0001 state(State0001 in) {
    State0001 out=state;
    state=in;
    if(out!=null) {
      centerScreen.remove.add(out);
      centerCam.remove.add(out.displayCam);
      out.to(in);
      out.pause();
    }
    //TODO
    in.resume();
    in.from(state);
    centerScreen.add.add(in);
    centerCam.add.add(in.displayCam);
    return out;
  }
  @Override
  public void update() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {
    // if(cam.grabCursor) drawCursor();
  }
  public void drawCursor() {
    beginBlend();
    final int a=0,b=255;
    fill(mouse.left?a:b,mouse.center?a:b,mouse.right?a:b,127);
    rect(mouse.x-4,mouse.y-0.5f,8,1);
    rect(mouse.x-0.5f,mouse.y-4,1,8);
    endBlend();
  }
  @Override
  public void display() {
    if(debugInfo) text("Memory="+getMemory()+"Mb",0,bu*1.5f);
    if(cam.grabCursor) {
      withCam();
      drawCursor();
    }
  }
  public String getMemory() {
    return Tools.cutToLastDigitString((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024));
  }
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
    State0001.exit();
  }
}
