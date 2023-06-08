package pama1234.game.app.server.server0001.game.particle;

import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.exception.CompileFailedException;
import com.aparapi.internal.kernel.KernelManager;

import pama1234.game.app.server.server0001.particle.Var;
import pama1234.util.entity.ServerEntity;

public class CellGroup3D extends ServerEntity{
  @Deprecated
  public static final int SIZE=Var.SIZE,DIST=Var.DIST;
  public final int size;
  public final float[] posX,posY,posZ,velX,velY,velZ;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final int[] colors;
  public final Range r,rSquare;
  public final CellUpdater3D updater;
  public final CellSquareUpdater3D squareUpdater;
  // public boolean completeUpdate;
  public CellGroup3D(int size,float boxR,int[] type,float[][][] core,int[] colors) {
    this(0.8f,boxR,size,type,core,colors);
  }
  public CellGroup3D(int size,int[] type,float[][][] core,int[] colors) {
    this(0.8f,160,size,type,core,colors);
  }
  public CellGroup3D(float f,float boxR,int size,int[] type,float[][][] core,int[] colors) {
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
    posZ=new float[size];
    //--
    velX=new float[size];
    velY=new float[size];
    velZ=new float[size];
    //---
    r=Range.create(size);
    rSquare=Range.create(size*size);
    //---
    updater=new CellUpdater3D(
      posX,posY,posZ,
      velX,velY,velZ,
      f,
      -boxR,-boxR,-boxR,
      boxR,boxR,boxR);
    squareUpdater=new CellSquareUpdater3D(
      posX,posY,posZ,
      velX,velY,velZ,
      DIST,DIST/3,//TODO
      size,type,core,
      boxR/2,boxR/2,boxR/2);
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
  public float z(int p) {
    return posZ[p];
  }
  public int color(int p) {
    return colors[type[p]];
  }
}