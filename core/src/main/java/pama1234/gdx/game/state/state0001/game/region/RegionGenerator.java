package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.math.UtilMath;
import pama1234.math.hash.HashNoise2f;
import pama1234.math.hash.PerlinNoise2f;

public class RegionGenerator{
  public Screen0011 p;
  public RegionCenter pr;
  public PerlinNoise2f noise;
  public RegionGenerator(Screen0011 p,RegionCenter pr,float seed) {
    this.p=p;
    this.pr=pr;
    noise=new PerlinNoise2f(new HashNoise2f(seed));//TODO
  }
  public Region get(int x,int y) {
    Region region=new Region(p,pr,Gdx.files.local("data/saved/regions/"+x+" "+y+".bin"));
    region.x=x;
    region.y=y;
    get(region);
    return region;
  }
  public void get(Region region) {
    region.data=new Chunk[pr.regionWidth][pr.regionHeight];
    Chunk[][] data=region.data;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j]=new Chunk(region);
        Block[][] blockData=chunk.data=new Block[pr.chunkWidth][pr.chunkHeight];
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            // float random=p.random(2);
            // float random=noise.get(((region.x*pr.regionWidth+i)*pr.chunkWidth+n)/32f,((region.y*pr.regionHeight+j)*pr.chunkHeight+m)/32f);
            float tx=x(region.x,i,n)/32f,ty=y(region.y,j,m)/32f;
            float tx2=tx>0?doPow(tx):-doPow(-tx),ty2=ty>0?doPow(ty):-doPow(-ty);
            float random=noise.get(tx2,ty2);
            // float random=noise.get(x(region.x,i,n)/32f,y(region.y,j,m)/32f);
            // System.out.println(random+" "+i+" "+j+" "+n+" "+m);
            // p.println(random,tx2,ty2);
            if(random>0.6f) blockData[n][m]=new Block(pr.pw.metaBlocks.stone);
            else if(random>0.3f) blockData[n][m]=new Block(pr.pw.metaBlocks.dirt);
            else blockData[n][m]=new Block(pr.pw.metaBlocks.air);
          }
        }
      }
    }
  }
  public float doPow(float tx) {
    return UtilMath.pow(tx,0.8f);
  }
  public int y(int y1,int y2,int y3) {
    return (y1*pr.regionHeight+y2)*pr.chunkHeight+y3;
  }
  public int x(int x1,int x2,int x3) {
    return (x1*pr.regionWidth+x2)*pr.chunkWidth+x3;
  }
}
