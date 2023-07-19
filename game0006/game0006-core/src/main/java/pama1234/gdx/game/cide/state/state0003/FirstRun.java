package pama1234.gdx.game.cide.state.state0003;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.state.state0003.firstRun.FirstRunDisplay0001;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;

public class FirstRun extends StateEntity0003{
  public int state;
  public int time;
  public FileHandle firstRunFile;
  //---
  public FirstRunDisplay0001 display01;
  public FirstRun(ScreenCide2D p,int id) {
    super(p,id);
    MusicAsset.load_init();
    display01=new FirstRunDisplay0001(p,this);
  }
  @Override
  public void update() {
    time++;
    if(state==1) {
      firstRunFile.writeString("1234",false);
      p.state(p.stateCenter.loading);
    }
  }
  @Override
  public void displayCam() {}
  @Override
  public void from(StateEntity0003 in) {
    p.cam2d.scale.des=2;
    // p.centerCam.add.add(boxCenter);
    p.noStroke();
    p.backgroundColor(221,244,196);
    MusicAsset.alsoSprachZarathustra.play();
    display01.from(in);
    // p.centerCam.add.add(display01);
  }
  @Override
  public void to(StateEntity0003 in) {
    MusicAsset.alsoSprachZarathustra.stop();
    display01.to(in);
    // p.centerCam.remove.add(display01);
    // p.centerCam.remove.add(boxCenter);
  }
}
