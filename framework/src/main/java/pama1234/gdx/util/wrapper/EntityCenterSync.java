package pama1234.gdx.util.wrapper;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public class EntityCenterSync<T extends UtilScreen,E extends EntityListener>extends Entity<T>{
  public static <T> Iterator<T> descendingIterator(List<? extends T> list) {
    ListIterator<? extends T> li=list.listIterator(list.size());
    return new Iterator<T>() {
      public boolean hasNext() {
        return li.hasPrevious();
      }
      public T next() {
        return li.previous();
      }
    };
  }
  public final List<E> list=Collections.synchronizedList(new LinkedList<E>()),
    add=Collections.synchronizedList(new LinkedList<E>()),
    remove=Collections.synchronizedList(new LinkedList<E>());
  public boolean reverseDisplay=true;
  public EntityCenterSync(T p) {
    super(p);
  }
  public EntityCenterSync(T p,E in) {
    this(p);
    list.add(in);
  }
  public EntityCenterSync(T p,E[] in) {
    this(p);
    for(E i:in) list.add(i);
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
    for(E e:list) e.init();
  }
  @Override
  public void update() {
    refresh();
    for(E e:list) e.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) {
      Iterator<E> di=descendingIterator(list);
      while(di.hasNext()) di.next().display();
    }else for(E e:list) e.display();
  }
  @Override
  public void pause() {
    for(E e:list) e.pause();
  }
  @Override
  public void resume() {
    for(E e:list) e.resume();
  }
  @Override
  public void dispose() {
    refresh();//TODO
    for(E e:list) e.dispose();
    for(E e:add) e.dispose();
    for(E e:remove) e.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    for(E e:list) e.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    for(E e:list) e.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    for(E e:list) e.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(E e:list) e.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    for(E e:list) e.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    for(E e:list) e.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    for(E e:list) e.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    for(E e:list) e.keyTyped(key);
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
    for(E e:list) e.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    for(E e:list) e.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    for(E e:list) e.touchMoved(info);
  }
}