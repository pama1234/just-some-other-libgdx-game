package pama1234.gdx.game.app.app0006;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.MobileUtil;
import pama1234.gdx.Pama;
import pama1234.gdx.game.cide.State0003Util;
import pama1234.gdx.game.cide.State0003Util.DebugStateEntitys;
import pama1234.gdx.game.cide.State0003Util.StateCenter0003;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.math.UtilMath;

public class Screen0031 extends ScreenCide2D{
  public FileHandle cidePath;
  {
    // androidTouchHoldToRightButton=true;
    // isAndroid=true;
    laodDebugState=true;
  }
  @Override
  public void setup() {
    if(isAndroid) Pama.mobile.orientation(MobileUtil.portrait);
    camStrokeWeight=()->cam2d.pixelPerfect==CameraController2D.SMOOTH?UtilMath.max(cam2d.scale.pos,2):u/16*cam2d.scale.pos;
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    //---
    cidePath=Gdx.files.local("data/cide/");
    if(!cidePath.exists()) cidePath.mkdirs();
    //---
    stateCenter=new StateCenter0003(this);
    State0003Util.loadState0003(this,stateCenter);
    if(laodDebugState) {
      stateCenter.debug=new DebugStateEntitys(this);
      State0003Util.loadState0003Test(this,stateCenter.debug);
    }
    //---
    FileHandle firstRunFile=cidePath.child("firstRun.txt");
    boolean firstRun=!firstRunFile.exists();
    firstRun=true;
    if(firstRun) {
      stateCenter.firstRun.firstRunFile=firstRunFile;
      state(stateCenter.firstRun);
    }else {
      state(stateCenter.loading);
    }
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
