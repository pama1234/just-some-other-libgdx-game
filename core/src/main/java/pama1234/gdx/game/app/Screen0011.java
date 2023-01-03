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
  public long renderTime,updateTime;
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
    if(in!=null) {
      in.resume();
      in.from(state);
      centerScreen.add.add(in);
      centerCam.add.add(in.displayCam);
    }
    return out;
  }
  public State0001 stateNull() {
    State0001 out=state;
    state=null;
    if(out!=null) {
      centerScreen.list.remove(out);
      centerCam.list.remove(out.displayCam);
      out.to(null);
      out.pause();
    }
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
  // public void drawScreenCursor() {
  //   beginBlend();
  //   final int a=0,b=255;
  //   fill(mouse.left?a:b,mouse.center?a:b,mouse.right?a:b,127);
  //   rect(mouse.ox-u*4,mouse.oy-u*0.5f,u*8,u*1);
  //   rect(mouse.ox-u*0.5f,mouse.oy-u*4,u*1,u*8);
  //   endBlend();
  // }
  public void drawCursor() {
    beginBlend();
    final int a=0,b=255;
    fill(mouse.left?a:b,mouse.center?a:b,mouse.right?a:b,127);
    rect(mouse.x-4,mouse.y-0.5f,8,1);
    rect(mouse.x-0.5f,mouse.y-4,1,8);
    endBlend();
  }
  @Override
  public void doDraw() {
    if(debugInfo) {
      Tools.time();
      super.doDraw();
      renderTime=Tools.period();
    }else super.doDraw();
  }
  @Override
  public void doUpdate() {
    if(debugInfo) {
      Tools.time();
      super.doUpdate();
      updateTime=Tools.period();
    }else super.doUpdate();
  }
  @Override
  public void display() {
    if(debugInfo) {
      textScale(pus/2f);
      float th=pu/2f;
      float tx=th;
      float ty=bu*1.5f;
      text("Memory  ="+getMemory()+"Mb",tx,ty);
      text("Render  ="+getMillisString(renderTime)+"ms",tx,ty+th);
      text("Update  ="+getMillisString(updateTime)+"ms",tx,ty+th*2);
      text("Update  ="+getMillisString(updateTime)+"ms",tx,ty+th*3);
      text("CamScale="+Tools.cutToLastDigit(cam2d.scale.pos),tx,ty+th*4);
      textScale(pus);
    }
    if(cam.grabCursor) {
      // drawScreenCursor();
      withCam();
      drawCursor();
    }
  }
  public String getMillisString(long in) {
    return String.format("%03d",in);
  }
  public String getMillisString(long in,int l) {
    return String.format("%0"+l+"d",in);
  }
  public String getFloatString(float in) {
    return String.format("%.2f",in);
  }
  public String getMemory() {
    return Tools.cutToLastDigitString((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024));
  }
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    stateNull();
    super.dispose();
    State0001.disposeAll();
    // State0001.exit();
  }
}
