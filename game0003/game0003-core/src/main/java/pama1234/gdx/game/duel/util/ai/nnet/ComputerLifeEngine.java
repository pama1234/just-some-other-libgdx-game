package pama1234.gdx.game.duel.util.ai.nnet;

import com.badlogic.gdx.graphics.Pixmap;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;

public class ComputerLifeEngine extends PlayerEngine{
  public static final int dirPos=0,magPos=1,firePos=2;
  //---
  public VisionConverter vision;
  public NetworkGroup networks;
  public int dfire;
  public boolean completeCharge;
  public ComputerLifeEngine(Graphics graphics,NetworkGroup networks) {
    vision=new VisionConverter(graphics,networks.input.data());
    this.networks=networks;
  }
  @Override
  public void run(PlayerActor player) {
    prepareInput();
    networks.execute();
    completeCharge=player.state.hasCompletedLongBowCharge(player);
    operateOutput();
  }
  public void prepareInput() {
    vision.execute();
  }
  public void operateOutput() {
    float rad=networks.output.get(dirPos)*UtilMath.PI;
    float mag=UtilMath.constrain(networks.output.get(magPos),-1,1);
    float dx=UtilMath.sin(rad);
    float dy=UtilMath.cos(rad);
    inputDevice.operateMove(dx*mag,dy*mag);
    // System.out.println(dx*mag+" "+dy*mag);
    //---
    int fire=fireType(networks.output.get(firePos));
    // System.out.println(fire);
    if(fire==3) {
      System.out.println(completeCharge);
      if(completeCharge) inputDevice.operateLongShotButton(false);
      else inputDevice.operateLongShotButton(true);
    }
    if(fire!=dfire) {
      if(dfire==1) inputDevice.operateShotButton(false);
      else if(dfire==2) inputDevice.operateLongShotButton(false);
      // eise if(dfire==3) inputDevice.operateLongShotButton(false);
      //---
      if(fire==1) inputDevice.operateShotButton(true);
      else if(fire==2) inputDevice.operateLongShotButton(true);
      // eise if(fire==3) if(completeCharge) inputDevice.operateLongShotButton(false);
      dfire=fire;
    }
  }
  public static int fireType(float data) {
    return (int)(data*4);
  }
  public static class VisionConverter{
    public Graphics graphics;
    public int[] cache;
    public float[] data;
    public VisionConverter(Graphics graphics,float[] data) {
      this.graphics=graphics;
      this.data=data;
      cache=new int[graphics.width()*graphics.height()];
    }
    public void execute() {
      VisionUtil.pixmapToFloatArray(Pixmap.createFromFrameBuffer(0,0,graphics.width(),graphics.height()),cache,data,0);
    }
  }
  @Override
  public void setScore(float score) {
    networks.setScore(score);
  }
}