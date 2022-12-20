package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.util.Mutex;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.hash.HashNoise2f;
import pama1234.math.hash.PerlinNoise2f;

public class RegionCenter extends EntityCenter<Screen0011,Region> implements LoadAndSave{
  public World0001 pw;
  public FileHandle metadata;
  public int regionWidth=4,regionHeight=4;
  public int chunkWidth=64,chunkHeight=64;
  public Mutex doUpdate;
  public Thread updateLoop;
  public RegionCenter(Screen0011 p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
    createTest(p);
    doUpdate=new Mutex(true);
    updateLoop=new Thread(()-> {
      long beforeM,milis;
      while(!p.stop) {
        doUpdate.step();
        // System.out.println("RegionCenter.RegionCenter()");
        beforeM=System.currentTimeMillis();
        super.update();
        milis=System.currentTimeMillis()-beforeM;
        if(milis<50) p.sleep(50-milis);
      }
    });
    updateLoop.start();
  }
  public void createTest(Screen0011 p) {
    PerlinNoise2f noise=new PerlinNoise2f(new HashNoise2f(0));
    Region region=new Region(p,this,null);
    region.x=-1;
    region.data=new Chunk[regionWidth][regionHeight];
    Chunk[][] data=region.data;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j]=new Chunk(region);
        Block[][] blockData=chunk.data=new Block[chunkWidth][chunkHeight];
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            // float random=p.random(2);
            float random=noise.get(((region.x*regionWidth+i)*chunkWidth+n)/32f,((region.y*regionHeight+j)*chunkHeight+m)/32f);
            // System.out.println(random+" "+i+" "+j+" "+n+" "+m);
            if(random>0.3f) blockData[n][m]=new Block(pw.metaBlockCenter.dirt);
            else blockData[n][m]=new Block(pw.metaBlockCenter.air);
          }
        }
      }
    }
    add.add(region);
  }
  @Override
  public void init() {
    doUpdate.unlock();
  }
  @Override
  public void dispose() {
    doUpdate.lock();
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
  public Block getBlock(int x,int y) {
    int cx=UtilMath.floor((float)x/chunkWidth),cy=UtilMath.floor((float)y/chunkHeight);
    int tx=UtilMath.floor((float)cx/regionWidth),ty=UtilMath.floor((float)cy/regionHeight);
    int prx=Tools.moveInRange(cx,0,regionWidth),pry=Tools.moveInRange(cy,0,regionHeight);
    int px=Tools.moveInRange(x,0,chunkWidth),py=Tools.moveInRange(y,0,chunkHeight);
    for(Region r:list) if(r.x==tx&&r.y==ty) return r.data[prx][pry].data[px][py];
    return null;
  }
  @Override
  public void update() {
    // super.update();
  }
}
