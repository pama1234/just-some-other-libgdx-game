package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntityListener0001;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public enum State0001 implements EntityListener{
  // public enum State0001 extends EntityWrapper{
  FirstRun,
  Loading,
  StartMenu,
  Game,
  Settings,
  Announcement,
  Exception;
  public StateEntityListener0001 entity;
  public EntityListener displayCam;
  public void from(State0001 in) {
    entity.from(in);
  }
  public void to(State0001 in) {
    entity.to(in);
  }
  @Override
  public void init() {
    entity.init();
  }
  @Override
  public void update() {
    entity.update();
  }
  @Override
  public void display() {
    entity.display();
  }
  @Override
  public void pause() {
    entity.pause();
  }
  @Override
  public void resume() {
    entity.resume();
  }
  @Override
  public void dispose() {
    entity.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    entity.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    entity.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    entity.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    entity.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    entity.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    entity.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    entity.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    entity.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    entity.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    entity.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    entity.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    entity.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    entity.touchMoved(info);
  }
  //---------------------------------------------------------------
  public interface StateChanger{
    /**
     * @param in
     * @return out
     */
    public State0001 state(State0001 in);
  }
  //---------------------------------------------------------------
  public static State0001[] stateArray=State0001.values();
  public static int stateToInt(State0001 in) {
    return in.ordinal();
  }
  public static State0001 intToState(int in) {
    if(in<0||in>stateArray.length) {
      System.out.println("State0001 intToState in="+in);
      return State0001.Exception;
    }
    return stateArray[in];
  }
}
