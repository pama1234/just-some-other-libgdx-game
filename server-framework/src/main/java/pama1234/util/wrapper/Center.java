package pama1234.util.wrapper;

import java.util.LinkedList;

public class Center<T>{
  public final LinkedList<T> list=new LinkedList<T>(),
    add=new LinkedList<T>(),
    remove=new LinkedList<T>();
  public synchronized void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }
}
