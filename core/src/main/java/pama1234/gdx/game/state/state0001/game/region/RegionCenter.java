package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
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
  public Mutex doUpdate,doUpdateDisplay;
  public Thread updateLoop,updateDisplayLoop;
  public RegionCenter(Screen0011 p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
    generator=new RegionGenerator(p,this,0);//TODO
    doUpdate=new Mutex(true);
    doUpdateDisplay=new Mutex(true);
    updateLoop=createUpdateLoop();
    updateLoop.start();
  }
  @Override
  public void resume() {
    doUpdate.unlock();
    doUpdateDisplay.unlock();
  }
  @Override
  public void pause() {
    if(!p.stop) {
      doUpdate.lock();
      doUpdateDisplay.lock();
    }
  }
  @Override
  public void load() {
    add.add(generator.get(0,-1));
    add.add(generator.get(-1,-1));
    add.add(generator.get(-1,0));
    add.add(generator.get(0,0));
  }
  @Override
  public void save() {
    refresh();
    for(Region e:list) e.save();
  }
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
  public void dispose() {
    doUpdate.unlock();
    doUpdateDisplay.unlock();
  }
  public Thread createUpdateLoop() {
    return new Thread(()-> {
      long beforeM,milis;
      while(!p.stop) {
        doUpdate.step();
        beforeM=System.currentTimeMillis();
        // refresh();
        // Stream<Region> stream=list.stream().parallel();
        // stream.forEach(r->r.update());
        super.update();
        milis=System.currentTimeMillis()-beforeM;
        if(milis<50) p.sleep(50-milis);
      }
    });
  }
  public Thread createUpdateDisplayLoop() {
    return new Thread(()-> {
      while(!p.stop) {
        doUpdateDisplay.step();
        for(Region e:list) e.updateDisplay();
      }
    });
  }
}
