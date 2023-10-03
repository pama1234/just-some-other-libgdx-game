package pama1234.gdx.game.cide.state.state0003;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.state.state0003.firstRun.FirstRunDisplay0002;
import pama1234.gdx.game.cide.state.state0003.firstRun.FirstRunDisplayBase;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;

public class FirstRun extends StateEntity0003{
  public int time;
  public FileHandle firstRunFile;
  
  public FirstRunDisplayBase display;
  public FirstRun(ScreenCide2D p,int id) {
    super(p,id);
    // MusicAsset.load_init();
    display=new FirstRunDisplay0002(p,this);
  }
  @Override
  public void update() {
    time++;
  }
  public void endFirstRun() {
    boolean firstRun=!firstRunFile.exists();
    if(firstRun) firstRunFile.writeString("1234",false);
    p.state(p.stateCenter.loading);
  }
  @Override
  public void displayCam() {}
  @Override
  public void from(StateEntity0003 in) {
    p.cam2d.scale.des=2;
    p.noStroke();
    p.backgroundColor(221,244,196);
    // MusicAsset.alsoSprachZarathustra.play();
    display.from(in);
  }
  @Override
  public void to(StateEntity0003 in) {
    // MusicAsset.alsoSprachZarathustra.stop();
    display.to(in);
  }
}
