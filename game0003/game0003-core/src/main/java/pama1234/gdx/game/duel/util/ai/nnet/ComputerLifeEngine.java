package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;

public class ComputerLifeEngine extends PlayerEngine{
  public VisionConverter vision;
  public NetworkGroup networks;
  public int dfire;
  public ComputerLifeEngine(Graphics graphics,NetworkGroup networks) {
    vision=new VisionConverter(graphics,networks.input.data());
    this.networks=networks;
  }
  @Override
  public void run(PlayerActor player) {
    prepareInput();
    networks.execute();
    operateOutput();
  }
  public void prepareInput() {
    vision.execute();
  }
  public void operateOutput() {
    float rad=networks.output.get(0)*UtilMath.PI;
    float mag=UtilMath.constrain(networks.output.get(1),-1,1);
    float dx=UtilMath.sin(rad);
    float dy=UtilMath.cos(rad);
    inputDevice.operateMove(dx*mag,dy*mag);
    //---
    int fire=fireType(networks.output.get(2));
    if(fire!=dfire) {
      if(dfire==1) inputDevice.operateShotButton(false);
      else if(dfire==2) inputDevice.operateLongShotButton(false);
      //---
      if(fire==1) inputDevice.operateLongShotButton(true);
      else if(fire==2) inputDevice.operateLongShotButton(true);
      dfire=fire;
    }
  }
  public int fireType(float data) {
    return (int)(data*3);
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
      VisionUtil.textureToFloatArray(graphics.texture,cache,data,0);
    }
  }
}