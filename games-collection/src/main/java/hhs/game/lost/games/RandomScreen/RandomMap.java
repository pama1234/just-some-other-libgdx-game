package hhs.game.lost.games.RandomScreen;

import com.badlogic.gdx.math.MathUtils;

public class RandomMap{

  float sw,sh;
  int w,h;
  public float[][] map;
  float height;
  int seedSize;

  public RandomMap(float kw,float kh,int w,int h) {
    this(kw,kh,w,h,30,5);
  }

  public RandomMap(float kw,float kh,int w,int h,float height,int seedSize) {
    sw=kw;
    sh=kh;

    map=new float[w][h];
    this.w=w;
    this.h=h;
    this.height=height;
    this.seedSize=seedSize;

    setNoise();

  }
  protected void setNoise() {
    int i,j;
    for(i=1;i<h;i+=seedSize) {
      for(j=1;j<w;j+=seedSize) {
        if(inMap(j,i)) {
          map[j][i]=MathUtils.random(height);
        }
      }
    }
    for(i=1;i<h;i+=seedSize) {
      for(j=1;j<w;j+=seedSize) {
        if(inMap(j,i)) {
          for(int b=1;b<=seedSize-2;b++) {
            initMap(j,i,-b,0);
            initMap(j,i,-b,-b);
            initMap(j,i,0,-b);
            initMap(j,i,b,-b);
            initMap(j,i,b,0);
            initMap(j,i,b,b);
            initMap(j,i,0,b);
            initMap(j,i,-b,0);
            initMap(j,i,-b,b);
          }
        }
      }
    }
  }
  protected void initMap(int nx,int ny,int px,int py) {
    int ix=nx+px;
    int iy=ny+py;
    if(!inMap(ix,iy)) return;
    map[ix][iy]=map[nx][ny]*((seedSize-converAuto(px,py))/seedSize)+safeMap(nx+px*(seedSize-1),ny+py*(seedSize-1))*converAuto(px,py)/seedSize;
  }

  float converAuto(int px,int py) {
    return (Math.abs(px)+Math.abs(py))/2;
  }

  public float safeMap(int x,int y) {
    if(inMap(x,y)) {
      return map[x][y];
    }
    return 0;
  }

  boolean inMap(int x,int y) {
    return x>=0&&y>=0&&x<w&&y<h;
  }

}
