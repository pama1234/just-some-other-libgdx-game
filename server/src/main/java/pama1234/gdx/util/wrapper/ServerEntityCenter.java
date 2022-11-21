package pama1234.gdx.util.wrapper;

import java.util.Iterator;
import java.util.LinkedList;

import pama1234.gdx.util.entity.ServerEntity;
import pama1234.gdx.util.listener.ServerEntityListener;

public class ServerEntityCenter<T extends ServerEntityListener> extends ServerEntity{
  public final LinkedList<T> list=new LinkedList<T>(),
    add=new LinkedList<T>(),
    remove=new LinkedList<T>();
  public boolean reverseDisplay=true;
  public ServerEntityCenter() {}
  public ServerEntityCenter(T in) {
    list.add(in);
  }
  public ServerEntityCenter(T[] in) {
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
      Iterator<T> di=list.descendingIterator();
      while(di.hasNext()) di.next().display();
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
}