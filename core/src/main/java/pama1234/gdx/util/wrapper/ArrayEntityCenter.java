package pama1234.gdx.util.wrapper;

import java.util.ArrayList;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public class ArrayEntityCenter<T extends EntityListener>extends Entity<UtilScreen>{
  public final ArrayList<T> list=new ArrayList<T>(),
    add=new ArrayList<T>(),
    remove=new ArrayList<T>();
  public boolean reverseDisplay=true;
  public ArrayEntityCenter(UtilScreen p) {
    super(p);
  }
  public ArrayEntityCenter(UtilScreen p,T in) {
    this(p);
    list.add(in);
  }
  public ArrayEntityCenter(UtilScreen p,T[] in) {
    this(p);
    for(T i:in) list.add(i);
  }
  public void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    // for(T i:remove) i.dispose();
    remove.clear();
  }
  @Override
  public void init() {
    for(T e:list) e.init();
  }
  @Override
  public void update() {
    refresh();
    for(T e:list) e.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) {
      // Iterator<T> di=list.descendingIterator();
      // while(di.hasNext()) di.next().display();
      for(int i=list.size()-1;i>=0;i--) list.get(i).display();
    }else for(T e:list) e.display();
  }
  @Override
  public void pause() {
    for(T e:list) e.pause();
  }
  @Override
  public void resume() {
    for(T e:list) e.resume();
  }
  @Override
  public void dispose() {
    for(T e:list) e.dispose();
    for(T e:add) e.dispose();
    for(T e:remove) e.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    for(T e:list) e.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    for(T e:list) e.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    for(T e:list) e.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(T e:list) e.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    for(T e:list) e.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    for(T e:list) e.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    for(T e:list) e.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    for(T e:list) e.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    for(T e:list) e.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    for(T e:list) e.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    for(T e:list) e.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    for(T e:list) e.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    for(T e:list) e.touchMoved(info);
  }
}