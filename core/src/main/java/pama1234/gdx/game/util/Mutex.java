package pama1234.gdx.game.util;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mutex{
  public final AtomicBoolean lock;
  public final Object mutex;
  public Mutex(boolean lock) {
      this.lock = new AtomicBoolean(lock);
      this.mutex = new Object();
  }
  public void step() {
    if(lock.get()) synchronized(mutex) {
      try {
        mutex.wait();
      }catch(InterruptedException ex) {}
    }
  }
  public void lock() {
    lock.set(true);
  }
  public void unlock() {
    lock.set(false);
    synchronized(mutex) {
      mutex.notify();
    }
  }
}
