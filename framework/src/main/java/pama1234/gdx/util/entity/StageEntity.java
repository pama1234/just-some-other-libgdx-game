package pama1234.gdx.util.entity;

import com.badlogic.gdx.scenes.scene2d.Stage;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public class StageEntity extends Entity<UtilScreen>{
  Stage stage;
  public StageEntity(UtilScreen p,Stage in) {
    super(p);
    stage=in;
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void dispose() {}
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
  @Override
  public void touchStarted(TouchInfo info) {}
}