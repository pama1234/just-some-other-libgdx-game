package pama1234.gdx.game.state.state0001.game.region;

import java.util.stream.Stream;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
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
  public RegionLoadAndSaveCtrl pool;
  public LoopThread[] loops;
  public LoopThread updateLoop,fullMapUpdateDisplayLoop,updateDisplayLoop;
  public RegionCenter(Screen0011 p,World0001 pw,FileHandle metadata) {
    super(p);
    this.pw=pw;
    this.metadata=metadata;
    pool=new RegionLoadAndSaveCtrl(p,this,0);//TODO
    loops=new LoopThread[3];
    updateLoop=loops[0]=createUpdateLoop();
    updateLoop.start();
    fullMapUpdateDisplayLoop=loops[1]=createFullMapUpdateDisplayLoop();
    fullMapUpdateDisplayLoop.start();
    updateDisplayLoop=loops[2]=createUpdateDisplayLoop();
    updateDisplayLoop.start();
  }
  @Override
  public void resume() {
    unlockAllLoop();
  }
  @Override
  public void pause() {
    if(!p.stop) lockAllLoop();
  }
  @Override
  public void load() {
    add.add(pool.get(0,-1));
    add.add(pool.get(-1,-1));
    add.add(pool.get(-1,0));
    add.add(pool.get(0,0));
    // new Thread(()-> {
    //   p.sleep(1000);
    //   add.add(generator.get(-1,0));
    //   p.sleep(3000);
    //   // System.out.println("RegionCenter.load() add.add");
    //   add.add(generator.get(0,0));
    // }).start();
  }
  @Override
  public void save() {
    refresh();
    for(Region e:list) e.save();
  }
  @Override
  public void refresh() {
    // System.out.println("RegionCenter.refresh()");
    // for(int i=1;i<loops.length;i++) loops[i].lock.lock();
    testloadChunk();
    super.refresh();
    // for(int i=1;i<loops.length;i++) loops[i].lock.unlock();
  }
  public void testloadChunk() {}
  @Override
  public void update() {
    // super.update();
  }
  @Override
  public void display() {
    // super.display();
    fourPointDisplay();
  }
  public void fourPointDisplay() {
    p.imageBatch.begin();
    int x1=pw.xToBlockCord(p.cam2d.x1()),
      y1=pw.xToBlockCord(p.cam2d.y1()),
      x2=pw.xToBlockCord(p.cam2d.x2()),
      y2=pw.xToBlockCord(p.cam2d.y2());
    for(int i=x1;i<=x2;i++) {
      for(int j=y1;j<=y2;j++) {
        int tx=i*pw.blockWidth,
          ty=j*pw.blockHeight;
        Block block=getBlock(i,j);
        if(block==null) continue;//TODO
        MetaBlock blockType=block.type;
        // blockType.updateDisplay(block,i,j);
        if(!blockType.display) continue;
        blockType.display(p,block,tx,ty);
      }
    }
    p.imageBatch.end();
    p.noTint();
  }
  public void dispose() {
    unlockAllLoop();
    for(LoopThread e:loops) e.interrupt();
    // updateDisplayLoop.interrupt();//TODO
  }
  public void unlockAllLoop() {
    for(LoopThread e:loops) e.lock.unlock();
  }
  public void lockAllLoop() {
    for(LoopThread e:loops) e.lock.lock();
  }
  public LoopThread createUpdateLoop() {
    return new LoopThread("RegionsUpdateLoop") {//刷新全世界的方块数据
      @Override
      public void doUpdate() {
        // refresh();
        // Stream<Region> stream=list.stream().parallel();
        // stream.forEach(r->r.update());
        RegionCenter.super.update();
        // refresh();
        // for(Region e:list) e.update();
      }
    };
  }
  public LoopThread createFullMapUpdateDisplayLoop() {//刷新全世界的方块显示
    return new LoopThread("RegionsFullMapUpdateDisplayLoop",30000) {
      @Override
      public void doUpdate() {
        // for(Region e:list) e.updateDisplay();
        // refresh();
        Stream<Region> stream=list.stream().parallel();
        stream.forEach(r->r.updateDisplay());
      }
    };
  }
  public LoopThread createUpdateDisplayLoop() {//刷新视角内的方块显示
    return new LoopThread("RegionsUpdateDisplayLoop") {
      @Override
      public void doUpdate() {
        // for(Region e:list) e.updateDisplay();
        // refresh();
        // Stream<Region> stream=list.stream().parallel();
        // stream.forEach(r->r.updateDisplay());
        // fourPointDisplay();
        int x1=pw.xToBlockCord(p.cam2d.x1()),
          y1=pw.xToBlockCord(p.cam2d.y1()),
          x2=pw.xToBlockCord(p.cam2d.x2()),
          y2=pw.xToBlockCord(p.cam2d.y2());
        for(int i=x1;i<=x2;i++) {
          for(int j=y1;j<=y2;j++) {
            // int tx=i*pw.blockWidth,
            //   ty=j*pw.blockHeight;
            Block block=getBlock(i,j);
            if(block==null) continue;//TODO
            MetaBlock blockType=block.type;
            blockType.updateDisplay(block,i,j);
          }
        }
      }
    };
  }
  public Block getBlock(int x,int y) {
    int cx=UtilMath.floor((float)x/chunkWidth),cy=UtilMath.floor((float)y/chunkHeight);
    int tx=UtilMath.floor((float)cx/regionWidth),ty=UtilMath.floor((float)cy/regionHeight);
    int prx=Tools.moveInRange(cx,0,regionWidth),pry=Tools.moveInRange(cy,0,regionHeight);
    int px=Tools.moveInRange(x,0,chunkWidth),py=Tools.moveInRange(y,0,chunkHeight);
    // synchronized(list) {
    for(Region r:list) if(r.x==tx&&r.y==ty) return r.data[prx][pry].data[px][py];
    // }
    return null;
  }
  public abstract class LoopThread extends Thread{
    public Mutex lock;
    public long sleepSize=50;//set to negative for no sleep
    public long millis,stepMillis;
    public LoopThread(String name) {
      super(name);
      lock=new Mutex(true);
    }
    public LoopThread(String name,long sleepSize) {
      this(name);
      this.sleepSize=sleepSize;
    }
    @Override
    public void run() {
      long beforeM=System.currentTimeMillis();
      while(!p.stop) {
        lock.step();
        long tl=System.currentTimeMillis();
        stepMillis=tl-beforeM;
        beforeM=tl;
        doUpdate();
        millis=System.currentTimeMillis()-beforeM;
        doSleep();
      }
    }
    public abstract void doUpdate();
    public void doSleep() {
      if(millis<sleepSize) try {
        sleep(sleepSize-millis);
      }catch(InterruptedException e) {
        // e.printStackTrace();
        // System.out.println(e);
        // System.out.println(p.stop);
      }
    }
  }
}