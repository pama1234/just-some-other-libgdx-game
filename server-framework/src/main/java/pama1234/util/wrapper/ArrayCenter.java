package pama1234.util.wrapper;

import java.util.ArrayList;

public class ArrayCenter<T>{
  public final ArrayList<T> list=new ArrayList<T>(),
    add=new ArrayList<T>(),
    remove=new ArrayList<T>();
  public synchronized void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }
}
