package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public class EntityWrapper<T extends EntityListener>extends Entity<UtilScreen>{
  public final T content;
  public EntityWrapper(UtilScreen p,T son) {
    super(p);
    this.content=son;
  }
  @Override
  public void init() {
    content.init();
  }
  @Override
  public void update() {
    content.update();
  }
  @Override
  public void display() {
    content.display();
  }
  @Override
  public void pause() {
    content.pause();
  }
  @Override
  public void resume() {
    content.resume();
  }
  @Override
  public void dispose() {
    content.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    content.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    content.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    content.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    content.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    content.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    content.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    content.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    content.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    content.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    content.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    content.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    content.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    content.touchMoved(info);
  }
}
