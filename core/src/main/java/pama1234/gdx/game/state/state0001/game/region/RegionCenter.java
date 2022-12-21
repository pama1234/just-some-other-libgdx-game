package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.util.Mutex;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class RegionCenter extends EntityCenter<Screen0011,Region> implements LoadAndSave{
  public World0001 pw;
  public FileHandle metadata;
  public int regionWidth=4,regionHeight=4;
  public int chunkWidth=64,chunkHeight=64;
  public RegionGenerator generator;
  public Mutex doUpdate;
  public Thread updateLoop;
  public RegionCenter(Screen0011 p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
    // createTest(p);
    generator=new RegionGenerator(p,this,0);//TODO
    add.add(generator.get(0,-1));
    add.add(generator.get(-1,-1));
    add.add(generator.get(-1,0));
    add.add(generator.get(0,0));
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
  @Override
  public void init() {
    doUpdate.unlock();
  }
  @Override
  public void dispose() {
    // System.out.println(p.stop);
    if(!p.stop) doUpdate.lock();
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
  public void exit() {
    // System.out.println("RegionCenter.exit()");
    doUpdate.unlock();
  }
}
