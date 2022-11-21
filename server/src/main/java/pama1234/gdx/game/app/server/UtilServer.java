package pama1234.gdx.game.app.server;


import java.util.Random;

import pama1234.gdx.util.listener.ServerEntityListener;
import pama1234.gdx.util.wrapper.ServerEntityCenter;
import pama1234.math.Tools;

public abstract class UtilServer implements Runnable{
  public ServerEntityCenter<ServerEntityListener> center=new ServerEntityCenter<ServerEntityListener>();
  public boolean stop;
  //---
  // public float targetFrameRate=30,frameRate;
  // public long frameRatePeriod=1000000000L/(long)targetFrameRate;
  public float frameRate;
  public float frameRateTarget;
  public long frameRatePeriod;
  public long frameRateLastNanos;
  //---
  public long overSleepTime=0L;
  //---
  public Random rng=new Random();
  public final float random(float high) {
    if(high==0||high!=high) return 0;
    float value=0;
    do value=rng.nextFloat()*high; while(value==high);
    return value;
  }
  public final float random(float low,float high) {
    if(low>=high) return low;
    float diff=high-low;
    float value=0;
    do value=random(diff)+low; while(value==high);
    return value;
  }
  //---
  {
    frameRate(60);
  }
  public abstract void init();
  public abstract void update();
  public abstract void dispose();
  @Override
  public void run() {
    long beforeTime=System.nanoTime();
    long overSleepTime=0L;
    int noDelays=0;
    final int NO_DELAYS_PER_YIELD=15;
    //---
    init();
    while(!stop) {
      long now=System.nanoTime();
      double rate=1000000.0/((now-frameRateLastNanos)/1000000.0);
      float instantaneousRate=(float)(rate/1000.0);
      frameRate=(frameRate*0.9f)+(instantaneousRate*0.1f);
      //---
      center.update();
      update();
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
      beforeTime=System.nanoTime();
    }
    dispose();
  }
  public void frameRate(float fps) {
    frameRateTarget=fps;
    frameRatePeriod=(long)(1000000000.0/frameRateTarget);
  }
  public static void main(String[] args) {
    new UtilServer() {
      @Override
      public void init() {
        frameRate(120);
      }
      @Override
      public void update() {
        float tf=frameRate-frameRateTarget;
        if(tf>15||tf<-15) System.out.println(Tools.cutToLastDigit(tf));
      }
      @Override
      public void dispose() {}
    }.run();
  }
}
