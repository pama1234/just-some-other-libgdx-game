package pama1234.gdx.game.ui.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.info.MouseInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.assets.AssetManager;

public class ProgressBar<T extends UtilScreen>extends Entity<T>{
  public Color color=Color.WHITE;
  public float barHeight=1;
  public AssetManager manager;
  public ProgressBar(T p,AssetManager manager) {
    super(p);
    this.manager=manager;
  }
  @Override
  public void display() {
    p.fill(color);
    p.beginBlend();
    p.rect(0,p.height-barHeight*p.u,p.width*manager.getProgress(),barHeight*p.u);
    p.endBlend();
  }
  @Override
  public void update() {}
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
