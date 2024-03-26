package pama1234.server.game.life.particle.core;

import java.util.LinkedList;

import pama1234.math.UtilMath;
import pama1234.server.app.DedicatedServer;
import pama1234.server.game.life.particle.element.ParticleSandboxServer;
import pama1234.util.Mutex;
import pama1234.util.wrapper.ServerEntityCenter;

public class CellCenterServer extends ServerEntityCenter<DedicatedServer,CellServer>{
  public float r_const=CellServer.dist/4f;
  // public static final int fadeStep=16;

  public ParticleSandboxServer parent;
  public MetaCellCenterServer meta;

  // public final int boxR;
  public boolean boxed;
  public final int x1,y1,x2,y2;
  public final int w,h;

  public BoxExtension box;

  public boolean updateScore;

  /** 可视范围的半径 */
  public int viewRange=255;
  // public int viewRange=480;
  /** 对于此范围以内的粒子来说时间是正常流速的，越远离玩家时间流速越慢 */
  public float timeRangeMin;
  /** 对于此范围以外的粒子来说时间是静止的，越靠近玩家时间流速越快 */
  public float timeRangeMax;
  /** timeRangeMax-timeRangeMin的缓存 */
  public float timeRange=timeRangeMax-timeRangeMin;

  public LinkedList<CellServer> playerList=new LinkedList<>();

  public Mutex exitLock=new Mutex(false);
  public boolean stoped;

  {
    boxed=true;
    timeRangeMin=240;
    timeRangeMax=320;
    // timeRangeMin=80;
    // timeRangeMax=160;
  }

  public CellCenterServer(DedicatedServer p,ParticleSandboxServer parent,int areaWidth,int areaHeight,MetaCellCenterServer meta) {
    super(p);
    this.parent=parent;
    this.meta=meta;

    w=areaWidth;
    h=areaHeight;
    x1=0;
    y1=0;
    x2=areaWidth;
    y2=areaHeight;

    box=new BoxExtension();
  }

  @Override
  public void update() {
    exitLock.lock();
    {
      updateCell(1);
    }
    exitLock.unlock();
  }

  public void updateCell(float step) {
    list.parallelStream().forEach(i-> {
      i.point.vel.toNumber();
      i.point.pos.toNumber();
      i.back.clear();
      i.score.toNumber();
    });
    list.parallelStream().forEach(CellServer::update);

    cpuParallelScanUpdate(step);

    list.parallelStream().forEach(i-> {
      i.point.vel.toNumber();
      if(boxed) i.point.setInBox(x1,y1,x2,y2);
      else {
        if(i.isPlayer) i.testMoveInBox(x1,y1,x2,y2);
        else i.point.moveInBox(x1,y1,x2,y2);
      }
      for(Box j:box.boxCenter.list) {
        if(i.point.pos.inBox(j.rect)) {
          i.point.setOutBox(j.rect);
        }
      }
      i.point.pos.toNumber();
      if(updateScore) {
        i.back.parallelStream().forEach(t-> {
          final float scoreG=meta.list.get(t.meta).list.get(i.meta).scoreG;
          if(scoreG!=0) i.score.vel+=t.score.pos*scoreG;
        });
      }
    });
  }

  /**
   * 使用CPU的，并行的，线性扫描的，执行粒子系统的刷新
   */
  public void cpuParallelScanUpdate(float step) {
    // CellServer mainPlayer=parent.gameManager.select;

    list.parallelStream().forEach(i-> {
      float minDist=Integer.MAX_VALUE;
      for(CellServer j:playerList) {
        float dist=j.point.pos.dist(i.point.pos);
        if(dist<minDist) minDist=dist;
      }
      final float tv=UtilMath.constrain(1-(minDist-timeRangeMin)/timeRange,0,1);
      if(tv<=0) return;

      list.parallelStream().forEach(j-> {
        if(i==j) return;
        final MetaInfoUnit info=meta.list.get(i.meta).list.get(j.meta);
        float dx=j.point.pos.x-i.point.pos.x;
        float dy=j.point.pos.y-i.point.pos.y;
        if(!boxed) {
          if(dx>w/2f) dx=w-dx;
          if(dy>h/2f) dy=h-dy;
        }
        final float r=UtilMath.sqrt(dx*dx+dy*dy);
        if(r>info.max) return;
        else if(r<info.scoreR&&info.scoreG>0) j.back.add(i);

        dx/=r;
        dy/=r;
        final float f;
        if(r<CellServer.dist) f=r(r)*step*tv;
        else if(info.g!=0&&r>info.min) f=f(r,info.g)*step*tv;
        else return;
        i.point.vel.add(dx*f,dy*f);
      });
    });
  }

  public float f(final float r,final float g) {
    return g/r;
  }
  public float r(final float r) {
    return -r_const/r;
  }

  @Override
  public void dispose() {
    super.dispose();
//    stop();
  }
  public void stop() {
    exitLock.step();
    stoped=true;
  }
}
