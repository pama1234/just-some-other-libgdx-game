package pama1234.gdx.game.life.particle.life.particle;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.util.Mutex;
import pama1234.gdx.util.wrapper.EntityCenterSync;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectD;
import pama1234.server.game.life.particle.core.Box;
import pama1234.server.game.life.particle.core.CellCenterServer;
import pama1234.server.game.life.particle.core.CellServer;

public class CellCenter extends EntityCenterSync<Screen0045,Cell>{
  public int layer_cell_size=(int)CellServer.size;

  public final MetaCellCenter meta;
  public ParticleSandbox particleSandbox;

  public CellCenterServer core;

  public boolean render=true;
  public boolean netMode;

  // 用于联机的时候的操作
  public Mutex mutex=new Mutex(false);

  public CellCenter(final Screen0045 p,ParticleSandbox particleSandbox,final MetaCellCenter parent) {
    super(p);
    this.particleSandbox=particleSandbox;
    core=particleSandbox.core.cellCenter;
    this.meta=parent;
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    key=Character.toLowerCase(key);
  }
  @Override
  public void update() {
    if(core.box.spawnBox) {
      if(p.frameCount%30==0) {
        if(particleSandbox.scoreboard.gameLevel>0) {
          if(p.random(1/(particleSandbox.scoreboard.gameLevel+0.1f))<3f) {
            if(core.box.boxCenter.list.size()<core.box.maxBoxCount) {
              RectD rect=new RectD(p.random(core.x1,core.x2-core.box.boxSize),p.random(core.y1,core.y2-core.box.boxSize),core.box.boxSize,core.box.boxSize);
              if(particleSandbox.gameManager.select.core.point.pos.dist(rect.cx(),rect.cy())<core.box.boxSize*1.5f) return;
              core.box.boxCenter.add.add(new Box(rect));
            }
          }
        }else {
          if(!core.box.boxCenter.list.isEmpty()) {
            if(p.random(10)<3f) {
              core.box.boxCenter.remove.add(core.box.boxCenter.list.getFirst());
            }
          }
        }
      }
      core.box.boxCenter.refresh();
    }
    updateCell(1);
  }
  public void updateCell() {
    updateCell(1);
  }
  public void updateCell(float step) {
    core.updateCell(step);
    for(Box i:core.box.boxCenter.list) {
      i.time++;
      i.displayAlpha=255-(int)UtilMath.clamp(particleSandbox.gameManager.select.core.point.pos.dist(i.rect.cx(),i.rect.cy())*2.5f,0,255);
      if(i.time>core.box.boxLifeTime) core.box.boxCenter.remove.add(i);
    }
  }
  @Override
  public synchronized void refresh() {
    mutex.step();
    mutex.lock();
    for(Cell e:add) core.add.add(e.core);
    for(Cell e:remove) core.remove.add(e.core);
    super.refresh();
    mutex.unlock();
    core.refresh();
  }
  public void basicRefresh() {
    super.refresh();
  }

  @Override
  public void dispose() {
    super.dispose();
    core.dispose();
//    System.out.println("CellCenter.dispose");
//    System.out.flush();
  }
}
