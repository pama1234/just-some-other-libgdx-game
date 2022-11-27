package pama1234.gdx.game.app.server.particle.with2d;

import com.aparapi.Kernel;

public class CellSquareUpdater2D extends Kernel{
  public static final int G=0,MIN=1,MAX=2;
  public final float[] posX,posY,velX,velY;
  public final float dist,g;
  public final int size;
  public final int[] type;
  public final float[][][] forceMatrix;
  public CellSquareUpdater2D(
    float[] posX,float[] posY,
    float[] velX,float[] velY,
    float dist,float g,
    int size,int[] type,float[][][] forceMatrix) {
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
    this.dist=dist;
    this.g=g;
    this.size=size;
    this.type=type;
    this.forceMatrix=forceMatrix;
  }
  @Override
  public void run() {
    //--- 计算弹力与矩阵存储力
    int i=getGlobalId();
    int j=i/size;
    i%=size;
    if(i==j) return;
    float dx=posX[j]-posX[i];
    float dy=posY[j]-posY[i];
    final float r=mag(dx,dy);
    dx/=r;
    dy/=r;
    if(r<dist) {
      float f=-g/r;
      velX[i]+=dx*f;
      velY[i]+=dy*f;
      return;
    }
    //    if(r>dist) {
    if(r>forceMatrix[type[i]][type[j]][MAX]||r<forceMatrix[type[i]][type[j]][MIN]) return;
    //    dx/=r;
    //    dy/=r;
    //    dz/=r;
    float f=forceMatrix[type[i]][type[j]][G]/r;
    velX[i]+=dx*f;
    velY[i]+=dy*f;
    //    }else {
    //      float f=-g/r;
    //      dx/=r;
    //      dy/=r;
    //      dz/=r;
    //      velX[i]+=dx*f;
    //      velY[i]+=dy*f;
    //      velZ[i]+=dz*f;
    //    }
  }
  private float mag(final float x,final float y) {
    return sqrt(x*x+y*y);
  }
}
