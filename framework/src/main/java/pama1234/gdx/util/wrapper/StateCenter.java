package pama1234.gdx.util.wrapper;

import java.util.ArrayList;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public class StateCenter<T extends UtilScreen,E extends EntityListener>extends Entity<T>{
  public final ArrayList<E> list=new ArrayList<E>();
  public int pointer;
  public StateCenter(T p) {
    super(p);
  }
  public StateCenter(T p,E in) {
    this(p);
    list.add(in);
  }
  public StateCenter(T p,E[] in) {
    this(p);
    for(E i:in) list.add(i);
  }
  public E get() {
    return list.get(pointer);
  }
  public void setWithDispose(int in) {
    list.get(pointer).pause();
    list.get(pointer).dispose();
    list.get(in).init();
    list.get(in).resume();
    pointer=in;
  }
  public void set(int in) {
    list.get(pointer).pause();
    list.get(in).resume();
    pointer=in;
  }
  @Override
  public void init() {
    for(E e:list) e.init();
  }
  @Override
  public void update() {
    list.get(pointer).update();
  }
  @Override
  public void display() {
    list.get(pointer).display();
  }
  @Override
  public void pause() {
    list.get(pointer).pause();
  }
  @Override
  public void resume() {
    list.get(pointer).resume();
  }
  @Override
  public void dispose() {
    for(E e:list) e.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    list.get(pointer).mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    list.get(pointer).mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    list.get(pointer).mouseMoved();
  }
  @Override
  public void mouseDragged() {
    list.get(pointer).mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    list.get(pointer).mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    list.get(pointer).keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    list.get(pointer).keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    list.get(pointer).keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    for(E e:list) e.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    for(E e:list) e.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    list.get(pointer).touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    list.get(pointer).touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    list.get(pointer).touchMoved(info);
  }
}
