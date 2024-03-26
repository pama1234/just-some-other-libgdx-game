package hhs.gdx.hsgame.util.thread;

import com.badlogic.gdx.utils.Array;

public class ThreadCenter extends Thread{
  public Array<Task> method;
  public float delta,tmp;
  int i,size=0;
  public ThreadCenter() {
    method=new Array<>();
  }
  public void addTask(Task t) {
    method.add(t);
    size++;
  }
  public void removeTask(Task t) {
    t.running.setFlag(false);
  }
  @Override
  public void run() {
    while(isInterrupted()) {
      tmp=System.currentTimeMillis()/1000f;
      for(i=0;i<size;i++) {
        Task t=method.get(i);
        if(t.running.getFlag()) method.get(i).run(delta);
      }
      delta=System.currentTimeMillis()/1000f-tmp;
    }
  }
}
