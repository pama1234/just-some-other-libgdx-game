package pama1234.game.app.server.server0001.particle.with2d;

import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.exception.CompileFailedException;
import com.aparapi.internal.kernel.KernelManager;

import pama1234.util.entity.ServerEntity;

public class CellGroup2D extends ServerEntity{
  public static final int SIZE=2,DIST=SIZE*2;//TODO change to non static
  public final int size;
  public final float[] posX,posY,velX,velY;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final int[] colors;
  public final Range r,rSquare;
  public final CellUpdater2D updater;
  public final CellSquareUpdater2D squareUpdater;
  public CellGroup2D(int size,float boxR,int[] type,float[][][] core,int[] colors) {
    this(0.8f,boxR,size,type,core,colors);
  }
  public CellGroup2D(int size,int[] type,float[][][] core,int[] colors) {
    this(0.8f,160,size,type,core,colors);
  }
  public CellGroup2D(float f,float boxR,int size,int[] type,float[][][] core,int[] colors) {
    KernelManager kernelManager=KernelManager.instance();
    Device bestDevice=kernelManager.bestDevice();
    //---
    this.size=size;
    this.type=type;
    this.forceMatrix=core;
    this.colors=colors;
    //---
    posX=new float[size];
    posY=new float[size];
    //--
    velX=new float[size];
    velY=new float[size];
    //---
    r=Range.create(size);
    rSquare=Range.create(size*size);
    //---
    updater=new CellUpdater2D(
      posX,posY,
      velX,velY,
      f,
      -boxR,-boxR,-boxR,
      boxR,boxR,boxR);
    squareUpdater=new CellSquareUpdater2D(
      posX,posY,
      velX,velY,
      DIST,DIST/4,
      size,type,core,
      boxR/2,boxR/2);
    //---
    try {
      updater.compile(bestDevice);
      squareUpdater.compile(bestDevice);
    }catch(CompileFailedException e) {
      e.printStackTrace();
    }
  }
  public void update() {
    squareUpdater.execute(rSquare);
    updater.execute(r);
  }
  @Override
  public void display() {}
  @Override
  public void dispose() {
    squareUpdater.dispose();
    updater.dispose();
  }
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  //---
  public float x(int p) {
    return posX[p];
  }
  public float y(int p) {
    return posY[p];
  }
  public int color(int p) {
    return colors[type[p]];
  }
}