package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public class LayeredEntityCenter extends Entity<UtilScreen>{
  public EntityCenter<?>[] list;
  public boolean reverseDisplay;
  public LayeredEntityCenter(UtilScreen p,int size) {
    super(p);
    list=new EntityCenter<?>[size];
  }
  @Override
  public void init() {
    for(EntityCenter<?> i:list) i.init();
  }
  @Override
  public void update() {
    for(EntityCenter<?> i:list) i.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) for(int i=list.length-1;i>=0;i--) list[i].display();
    else for(EntityCenter<?> i:list) i.display();
  }
  @Override
  public void pause() {
    for(EntityCenter<?> i:list) i.pause();
  }
  @Override
  public void resume() {
    for(EntityCenter<?> i:list) i.resume();
  }
  @Override
  public void dispose() {
    for(EntityCenter<?> i:list) i.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    for(EntityCenter<?> i:list) i.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    for(EntityCenter<?> i:list) i.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    for(EntityCenter<?> i:list) i.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(EntityCenter<?> i:list) i.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    for(EntityCenter<?> i:list) i.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    for(EntityCenter<?> i:list) i.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    for(EntityCenter<?> i:list) i.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(char key) {
    for(EntityCenter<?> i:list) i.keyTyped(key);
  }
  @Override
  public void frameResized(int w,int h) {
    for(EntityCenter<?> i:list) i.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {
    for(EntityCenter<?> i:list) i.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    for(EntityCenter<?> i:list) i.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    for(EntityCenter<?> i:list) i.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    for(EntityCenter<?> i:list) i.touchMoved(info);
  }
}
