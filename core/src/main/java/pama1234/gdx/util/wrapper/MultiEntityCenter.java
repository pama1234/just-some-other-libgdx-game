package pama1234.gdx.util.wrapper;

import java.util.Iterator;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public class MultiEntityCenter<T extends UtilScreen,E extends EntityCenter<T,?>>extends EntityCenter<T,E>{
  // public EntityCenter<T,?>[] list;
  public boolean reverseDisplay;
  public MultiEntityCenter(T p) {
    super(p);
    // list=(EntityCenter<T,?>[])new EntityCenter<?,?>[size];
  }
  @Override
  public void init() {
    for(EntityCenter<T,?> i:list) i.init();
  }
  @Override
  public void update() {
    for(EntityCenter<T,?> i:list) i.update();
  }
  @Override
  public void display() {
    // if(reverseDisplay) for(int i=list.length-1;i>=0;i--) list[i].display();
    // else for(EntityCenter<T,?> i:list) i.display();
    if(reverseDisplay) {
      Iterator<E> di=list.descendingIterator();
      while(di.hasNext()) di.next().display();
    }else for(E e:list) e.display();
  }
  @Override
  public void pause() {
    for(EntityCenter<T,?> i:list) i.pause();
  }
  @Override
  public void resume() {
    for(EntityCenter<T,?> i:list) i.resume();
  }
  @Override
  public void dispose() {
    for(EntityCenter<T,?> i:list) i.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    for(EntityCenter<T,?> i:list) i.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    for(EntityCenter<T,?> i:list) i.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    for(EntityCenter<T,?> i:list) i.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(EntityCenter<T,?> i:list) i.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    for(EntityCenter<T,?> i:list) i.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    for(EntityCenter<T,?> i:list) i.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    for(EntityCenter<T,?> i:list) i.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(char key) {
    for(EntityCenter<T,?> i:list) i.keyTyped(key);
  }
  @Override
  public void frameResized(int w,int h) {
    for(EntityCenter<T,?> i:list) i.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {
    for(EntityCenter<T,?> i:list) i.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    for(EntityCenter<T,?> i:list) i.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    for(EntityCenter<T,?> i:list) i.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    for(EntityCenter<T,?> i:list) i.touchMoved(info);
  }
}
