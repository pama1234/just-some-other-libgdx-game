package pama1234.game.app.server.particle.with2d;

import com.aparapi.Kernel;

public class CellUpdater2D extends Kernel{
  public final float[] posX,posY,velX,velY;
  public final float f;
  public final float x1,y1,z1,x2,y2,z2;
  public CellUpdater2D(
    float[] posX,float[] posY,
    float[] velX,float[] velY,
    float f,
    float x1,float y1,float z1,
    float x2,float y2,float z2) {
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
    this.f=f;
    this.x1=x1;
    this.y1=y1;
    this.z1=z1;
    this.x2=x2;
    this.y2=y2;
    this.z2=z2;
  }
  @Override
  public void run() {
    int i=getGlobalId();
    //--- 计算位移
    posX[i]+=velX[i];
    posY[i]+=velY[i];
    //--- 计算摩擦力
    velX[i]*=f;
    velY[i]*=f;
    //--- 限制位置到立方体中
    if(posX[i]<x1) {
      posX[i]=x1;
      velX[i]*=-1;
    }else if(posX[i]>x2) {
      posX[i]=x2;
      velX[i]*=-1;
    }
    if(posY[i]<y1) {
      posY[i]=y1;
      velY[i]*=-1;
    }else if(posY[i]>y2) {
      posY[i]=y2;
      velY[i]*=-1;
    }
    //--- 位置和速度实数化
    if(!isFinite(posX[i])) posX[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(posY[i])) posY[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(velX[i])) velX[i]=(i-getGlobalSize()/2f)/getGlobalSize();
    if(!isFinite(velY[i])) velY[i]=(i-getGlobalSize()/2f)/getGlobalSize();
  }
  public boolean isFinite(float f) {
    return abs(f)<=Float.MAX_VALUE;
  }
}
