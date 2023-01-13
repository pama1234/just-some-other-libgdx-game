package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
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
    Region region=new Region(p,pr,x,y,Gdx.files.local("data/saved/regions/"+x+"."+y+".bin"));
    if(region.dataLocation.exists()) {
      region.load();
      // return region;
    }
    get(region);
    return region;
  }
  public void get(Region region) {
    MetaBlock[] types=pr.pw.metaBlocks.list.toArray(new MetaBlock[pr.pw.metaBlocks.list.size()]);
    // System.out.println(types.length);
    Chunk[][] data=region.data;
    if(data==null) region.data=new Chunk[pr.regionWidth][pr.regionHeight];
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk tc=data[i][j];
        if(tc!=null) {
          // p.println(tc,i,j);
          tc.innerInit(region);
        }else {
          tc=data[i][j]=new Chunk(region);
          tc.data=new Block[pr.chunkWidth][pr.chunkHeight];
        }
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block tb=blockData[n][m];
            if(tb!=null) {
              tb.innerInit(types[tb.typeId]);
              // p.println(i,j,n,m,tb.type.name);
            }else {
              float tx=x(region.x,i,n)/64f,ty=y(region.y,j,m)/64f;
              // float tx2=tx>0?doPow(tx):-doPow(-tx),ty2=ty>0?doPow(ty):-doPow(-ty);
              // float random=noise.get(tx2,ty2);
              float random=noise.get(tx,ty);
              // Block tb;
              if(random>0.6f) tb=new Block(pr.pw.metaBlocks.stone);
              else if(random>0.3f) tb=new Block(pr.pw.metaBlocks.dirt);
              else tb=new Block(pr.pw.metaBlocks.air);
              blockData[n][m]=tb;
            }
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
