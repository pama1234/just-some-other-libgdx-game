package pama1234.gdx.game.app.server.game.particle;

import com.aparapi.Kernel;

public class CellSquareUpdater3D extends Kernel{
  public static final int G=0,MIN=1,MAX=2;
  public final float[] posX,posY,posZ,velX,velY,velZ;
  public final float dist,g;
  public final int size;
  public final int[] type;
  public final float[][][] forceMatrix;
  public final float w,h,l,w2,h2,l2;
  public CellSquareUpdater3D(
    float[] posX,float[] posY,float[] posZ,
    float[] velX,float[] velY,float[] velZ,
    float dist,float g,
    int size,int[] type,float[][][] forceMatrix,float w,float h,float l) {
    this.posX=posX;
    this.posY=posY;
    this.posZ=posZ;
    this.velX=velX;
    this.velY=velY;
    this.velZ=velZ;
    this.dist=dist;
    this.g=g;
    this.size=size;
    this.type=type;
    this.forceMatrix=forceMatrix;
    this.w=w;
    this.h=h;
    this.l=l;
    this.w2=w*2;
    this.h2=h*2;
    this.l2=l*2;
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
    float dz=posZ[j]-posZ[i];
    final boolean a=dx>w||dx<-w,b=dy>h||dy<-h,c=dz>l||dz<-l;
    if(a) dx=dx<0?w2-dx:dx-w2;
    if(b) dy=dy<0?h2-dy:dy-h2;
    if(c) dz=dz<0?l2-dz:dz-l2;
    final float r=mag(dx,dy,dz);
    dx/=r;
    dy/=r;
    dz/=r;
    if(r<dist) {
      float f=g/r;
      if(a) velX[i]+=dx*f;
      else velX[i]-=dx*f;
      if(b) velY[i]+=dy*f;
      else velY[i]-=dy*f;
      if(c) velZ[i]+=dz*f;
      else velZ[i]-=dz*f;
      return;
    }
    if(r>forceMatrix[type[i]][type[j]][MAX]||r<forceMatrix[type[i]][type[j]][MIN]) return;
    float f=forceMatrix[type[i]][type[j]][G]/r;
    if(a) velX[i]-=dx*f;
    else velX[i]+=dx*f;
    if(b) velY[i]-=dy*f;
    else velY[i]+=dy*f;
    if(c) velZ[i]-=dz*f;
    else velZ[i]+=dz*f;
  }
  private float mag(final float x,final float y,final float z) {
    return sqrt(x*x+y*y+z*z);
  }
}
