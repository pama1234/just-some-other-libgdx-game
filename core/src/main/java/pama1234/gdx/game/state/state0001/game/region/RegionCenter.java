package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.EntityCenter;

public class RegionCenter extends EntityCenter<Screen0011,Region> implements LoadAndSave{
  public World0001 pw;
  public FileHandle metadata;
  public int regionWidth=4,regionHeight=4;
  public RegionCenter(Screen0011 p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
    createTest(p);
  }
  private void createTest(Screen0011 p) {
    Region region=new Region(p,this,null);
    region.data=new Chunk[regionWidth][regionHeight];
    Chunk[][] data=region.data;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j]=new Chunk(region);
        Block[][] blockData=chunk.data=new Block[region.chunkWidth][region.chunkHeight];
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            //Block block=
            float random=p.random(2);
            // System.out.println(random+" "+i+" "+j+" "+n+" "+m);
            if(random>0.5f) blockData[n][m]=new Block(pw.metaBlockCenter.air);
            else blockData[n][m]=new Block(pw.metaBlockCenter.dirt);
          }
        }
      }
    }
    add.add(region);
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
}
