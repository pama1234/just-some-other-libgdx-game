package pama1234.game.app.server.server0001.particle.with2d;

import com.aparapi.Kernel;

public class CellSquareUpdater2D extends Kernel{
  public static final int G=0,MIN=1,MAX=2;
  public final float[] posX,posY,velX,velY;
  public final float dist,g;
  public final int size;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final float w,h,w2,h2;
  public CellSquareUpdater2D(
    float[] posX,float[] posY,
    float[] velX,float[] velY,
    float dist,float g,
    int size,int[] type,float[][][] forceMatrix,float w,float h) {
    this.posX=posX;
    this.posY=posY;
    this.velX=velX;
    this.velY=velY;
    this.dist=dist;
    this.g=g;
    this.size=size;
    this.type=type;
    this.forceMatrix=forceMatrix;
    this.w=w;
    this.h=h;
    this.w2=w*2;
    this.h2=h*2;
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
    final boolean a=dx>w||dx<-w,b=dy>h||dy<-h;
    if(a) dx=dx<0?w2-dx:dx-w2;
    if(b) dy=dy<0?h2-dy:dy-h2;
    final float r=mag(dx,dy);
    dx/=r;
    dy/=r;
    if(r<dist) {
      float f=g/r;
      // velX[i]+=dx*f;
      // velY[i]+=dy*f;
      // return;
      if(a) velX[i]+=dx*f;
      else velX[i]-=dx*f;
      if(b) velY[i]+=dy*f;
      else velY[i]-=dy*f;
      return;
    }
    if(r>forceMatrix[type[i]][type[j]][MAX]||r<forceMatrix[type[i]][type[j]][MIN]) return;
    float f=forceMatrix[type[i]][type[j]][G]/r;
    // velX[i]+=dx*f;
    // velY[i]+=dy*f;
    if(a) velX[i]-=dx*f;
    else velX[i]+=dx*f;
    if(b) velY[i]-=dy*f;
    else velY[i]+=dy*f;
  }
  private float mag(final float x,final float y) {
    return sqrt(x*x+y*y);
  }
}
