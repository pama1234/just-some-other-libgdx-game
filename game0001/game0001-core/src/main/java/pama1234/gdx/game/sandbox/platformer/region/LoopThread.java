package pama1234.gdx.game.sandbox.platformer.region;

import pama1234.util.Mutex;
import pama1234.util.function.GetBoolean;

public class LoopThread extends Thread{
  public static final LoopThread.ExecuteLoopThread doNothing=(self)-> {};
  public Mutex lock;
  public long sleepSize=50;//set to negative for no sleep
  public long millis,stepMillis;
  public boolean finished;
  public LoopThread.ExecuteLoopThread doUpdate=doNothing;
  public LoopThread.ExecuteLoopThread doFinished=doNothing;
  public GetBoolean stop;
  // public GetBoolean stop=()->p.stop||RegionCenter.this.stop;
  public LoopThread(String name) {
    super(name);
    lock=new Mutex(true);
    setPriority(MIN_PRIORITY);//TODO
  }
  public LoopThread(String name,LoopThread.ExecuteLoopThread doUpdate) {
    this(name);
    this.doUpdate=doUpdate;
  }
  public LoopThread(String name,long sleepSize,LoopThread.ExecuteLoopThread doUpdate) {
    this(name);
    this.sleepSize=sleepSize;
    this.doUpdate=doUpdate;
  }
  public LoopThread(String name,long sleepSize,LoopThread.ExecuteLoopThread doUpdate,LoopThread.ExecuteLoopThread doFinished) {
    this(name);
    this.sleepSize=sleepSize;
    this.doUpdate=doUpdate;
    this.doFinished=doFinished;
  }
  @Override
  public void run() {
    long beforeM=System.currentTimeMillis();
    while(!stop.get()) {
      lock.step();
      if(stop.get()) return;//TODO
      long tl=System.currentTimeMillis();
      stepMillis=tl-beforeM;
      beforeM=tl;
      finished=false;
      doUpdate.execute(this);
      // doUpdate();
      finished=true;
      doFinished.execute(this);
      millis=System.currentTimeMillis()-beforeM;
      doSleep();
    }
  }
  public void doSleep() {
    if(millis<sleepSize) try {
      sleep(sleepSize-millis);
    }catch(InterruptedException e) {}
  }
  @FunctionalInterface
  public interface ExecuteLoopThread{
    public void execute(LoopThread self);
  }
}