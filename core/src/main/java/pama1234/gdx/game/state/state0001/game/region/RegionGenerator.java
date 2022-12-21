package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
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
    region.data=new Chunk[pr.regionWidth][pr.regionHeight];
    Chunk[][] data=region.data;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j]=new Chunk(region);
        Block[][] blockData=chunk.data=new Block[pr.chunkWidth][pr.chunkHeight];
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            // float random=p.random(2);
            float random=noise.get(((region.x*pr.regionWidth+i)*pr.chunkWidth+n)/32f,((region.y*pr.regionHeight+j)*pr.chunkHeight+m)/32f);
            // System.out.println(random+" "+i+" "+j+" "+n+" "+m);
            if(random>0.3f) blockData[n][m]=new Block(pr.pw.blockC.dirt);
            else blockData[n][m]=new Block(pr.pw.blockC.air);
          }
        }
      }
    }
    return region;
  }
}
