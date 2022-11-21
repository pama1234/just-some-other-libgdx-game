package pama1234.gdx.util.wrapper;

import java.util.ArrayList;

import pama1234.gdx.util.entity.ServerEntity;
import pama1234.gdx.util.listener.ServerEntityListener;

public class ServerStateCenter<T extends ServerEntityListener> extends ServerEntity{
  public final ArrayList<T> list=new ArrayList<T>();
  public int pointer;
  public ServerStateCenter() {}
  public ServerStateCenter(T in) {
    list.add(in);
  }
  public ServerStateCenter(T[] in) {
    for(T i:in) list.add(i);
  }
  public void set(int in) {
    list.get(pointer).pause();
    list.get(in).resume();
    pointer=in;
  }
  @Override
  public void init() {
    for(T e:list) e.init();
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
    for(T e:list) e.dispose();
  }
}
