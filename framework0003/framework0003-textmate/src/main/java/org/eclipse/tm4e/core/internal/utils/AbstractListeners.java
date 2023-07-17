package org.eclipse.tm4e.core.internal.utils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class AbstractListeners<LISTENER,EVENT>{
  // private static final Logger LOGGER=System.getLogger(AbstractListeners.class.getName());
  private final Set<LISTENER> listeners=new CopyOnWriteArraySet<>();
  public boolean add(final LISTENER listener) {
    if(!listeners.add(listener)) {
      System.out.println("Trying to add listener {0} which is already registered with {1}."+" "+listener+" "+this);
      return false;
    }
    return true;
  }
  public int count() {
    return listeners.size();
  }
  public void dispatchEvent(final EVENT e) {
    listeners.forEach(l->dispatchEvent(e,l));
  }
  public abstract void dispatchEvent(EVENT e,LISTENER l);
  public boolean isEmpty() {
    return listeners.isEmpty();
  }
  public boolean isNotEmpty() {
    return !listeners.isEmpty();
  }
  public boolean remove(final LISTENER listener) {
    if(!listeners.remove(listener)) {
      System.out.println("Trying to remove listener {0} which is not registered with {1}."+" "+listener+" "+this);
      return false;
    }
    return true;
  }
  public void removeAll() {
    listeners.clear();
  }
}