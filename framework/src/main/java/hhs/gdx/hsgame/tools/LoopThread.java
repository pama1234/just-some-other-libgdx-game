package hhs.gdx.hsgame.tools;

public abstract class LoopThread extends Thread{
  /**
   * before time and after time
   */
  // long br,ar;
  //---
  public boolean stop;
  //---
  public float frameRate;
  public float frameRateTarget;
  public long frameRatePeriod;
  public long frameRateLastNanos;
  public int frameCount;
  public LoopThread(String name) {
    super(name);
  }
  public abstract void loop();
  @Override
  public void run() {
    long beforeTime=System.nanoTime();
    long overSleepTime=0L;
    int noDelays=0;
    final int NO_DELAYS_PER_YIELD=15;
    //---
    while(!stop) {
      long now=System.nanoTime();
      double rate=1000000.0/((now-frameRateLastNanos)/1000000.0);
      float instantaneousRate=(float)(rate/1000.0);
      frameRate=(frameRate*0.9f)+(instantaneousRate*0.1f);
      //---
      loop();
      //---
      frameRateLastNanos=now;
      //-
      long afterTime=System.nanoTime();
      long timeDiff=afterTime-beforeTime;
      long sleepTime=(frameRatePeriod-timeDiff)-overSleepTime;
      if(sleepTime>0) {
        try {
          Thread.sleep(sleepTime/1000000L,(int)(sleepTime%1000000L));
        }catch(InterruptedException ex) {}
        overSleepTime=(System.nanoTime()-afterTime)-sleepTime;
      }else {
        overSleepTime=0L;
        noDelays++;
        if(noDelays>NO_DELAYS_PER_YIELD) {
          Thread.yield();
          noDelays=0;
        }
      }
      frameCount++;
      beforeTime=System.nanoTime();
    }
  }
  public void frameRate(float fps) {
    frameRateTarget=fps;
    frameRatePeriod=(long)(1000000000.0/frameRateTarget);
  }
}
