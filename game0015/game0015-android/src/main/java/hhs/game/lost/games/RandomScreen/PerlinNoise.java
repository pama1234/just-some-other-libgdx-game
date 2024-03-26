package hhs.game.lost.games.RandomScreen;

import com.badlogic.gdx.math.MathUtils;

public class PerlinNoise{

  public float map[][],noise[][];
  float maxNoise=0;
  int qSize=0,w,h,qn;

  public PerlinNoise(int sqNum,float _maxNoise,int sq) {
    maxNoise=_maxNoise;
    qn=sqNum;
    qSize=sq;
    this.w=sqNum*sq;
    this.h=w;
    map=new float[w][h];
    noise=new float[sqNum+1][sqNum+1];
    addNoise();
    lerpMap();
  }
  public void addNoise() {
    for(int i=0;i<noise.length;i++) {
      for(int j=0;j<noise[i].length;j++) {
        float nub=MathUtils.random(0,maxNoise);
        noise[i][j]=nub;
      }
    }
  }
  public void lerpMap() {
    int i,j,t,z;
    for(z=0;z<qn;z++) {
      for(t=0;t<qn;t++) {
        for(i=0;i<qSize;i++) {
          for(j=0;j<qSize;j++) {
            float interX=MathUtils.lerp(noise[z][t],noise[z+1][t],lerpValue(1f*i/qSize));
            float interY=MathUtils.lerp(noise[z][t+1],noise[z+1][t+1],lerpValue(1f*i/qSize));
            float inter=MathUtils.lerp(interX,interY,lerpValue(1f*j/qSize));
            if(checkSafe(i+z*qSize,j+t*qSize)) map[i+z*qSize][j+t*qSize]=inter;
          }
        }
      }
    }
  }
  boolean checkSafe(int i,int j) {
    return i>0&&j>0&&i<map.length&&j<map.length;
  }
  public float lerpValue(float p) {
    return MathUtils.lerp(0,1,p);
  }
}
