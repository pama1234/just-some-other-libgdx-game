package pama1234.gdx.util.wrapper;

import java.util.Iterator;
import java.util.LinkedList;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 一种用于存储实体的链表型容器,相比{@link ArrayEntityCenter
 * 数组型实体容器}，这个实体容器的实现在需要频繁增加和删除实体，并且不需要访问其特定位置的内容时，是更合适的
 * </p>
 * TODO 添加debug功能，对于传入add的null实体，应当在add.add方法调用时抛出异常，这一点需要创建新类继承LinkedList
 * 
 * @see ArrayEntityCenter
 *
 */
public class EntityCenter<T extends UtilScreen,E extends EntityListener>extends Entity<T>{
  public final LinkedList<E> list=new LinkedList<E>(),
    add=new LinkedList<E>(),
    remove=new LinkedList<E>();
  public boolean reverseDisplay=true;
  public EntityCenter(T p) {
    super(p);
  }
  public EntityCenter(T p,E in) {
    this(p);
    list.add(in);
  }
  public EntityCenter(T p,E[] in) {
    this(p);
    for(E i:in) list.add(i);
  }
  public synchronized void refresh() {
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
      Iterator<E> di=list.descendingIterator();
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
  // public <O extends E> void doRemove(O e) {//TODO
  //   if(e instanceof E ie) remove.add(ie);
  // }
}