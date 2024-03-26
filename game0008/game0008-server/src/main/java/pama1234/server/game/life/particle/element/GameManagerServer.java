package pama1234.server.game.life.particle.element;

import java.util.LinkedList;

import pama1234.Tools;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;
import pama1234.server.app.DedicatedServer;
import pama1234.server.game.life.particle.core.CellCenterServer;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.core.MetaCellCenterServer;
import pama1234.server.game.life.particle.net.message.GameCtrlCache;
import pama1234.util.entity.ServerEntity;

public class GameManagerServer extends ServerEntity<DedicatedServer>{

  public ParticleSandboxServer parent;

  public CellCenterServer cellCenter;
  public MetaCellCenterServer metaCenter;

  public CellServer select;
  public LinkedList<CellServer> near=new LinkedList<>();

  public float dist=CellServer.dist*6;
  public int originalCellType;
  public boolean firstInit=true;

  public GameCtrlCache gameCtrlCache;

  public GameManagerServer(DedicatedServer p,ParticleSandboxServer parent) {
    super(p);
    this.parent=parent;

    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }

  @Override
  public void display() {}

  @Override
  public void dispose() {}

  @Override
  public void init() {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void update() {
    moveInCircle();
  }

  public void doSelect(CellServer in) {
    select=in;
    select.isPlayer=true;
    originalCellType=select.meta;
    cellCenter.playerList.add(in);
  }
  public void doSelect(int in) {
    doSelect(cellCenter.list.get(in));
  }
  public void undoSelect() {
    select.meta=originalCellType;
    select.isPlayer=false;
    cellCenter.playerList.remove(select);
    select=null;
  }
  public void update(GameCtrlCache gameCtrlCache) {
    this.gameCtrlCache=gameCtrlCache;
    select.meta=gameCtrlCache.cellMetaType;
    select.meta=Tools.moveInRange(select.meta,0,metaCenter.list.size());
    update();
  }
  public void moveInCircle() {
    if(select!=null) {
      float tdx=gameCtrlCache.dx,tdy=gameCtrlCache.dy;

      select.point.vel.add(tdx,tdy);
      final Vec2f pos2=select.point.pos;
      near.clear();
      for(CellServer i:cellCenter.list) {
        if(i==select||i.meta!=select.meta) continue;
        final Vec2f pos1=i.point.pos;
        final float td;
        if(!parent.cellCenter.boxed) {
          float dx=pos1.x-pos2.x;
          float dy=pos1.y-pos2.y;
          if(dx>parent.cellCenter.w/2) dx=parent.cellCenter.w-dx;
          if(dy>parent.cellCenter.h/2) dy=parent.cellCenter.h-dy;
          td=UtilMath.mag(dx,dy);
        }else {
          td=UtilMath.dist(
            pos1.x,
            pos1.y,
            pos2.x,
            pos2.y);
        }
        if(td<dist) {
          near.add(i);
          i.point.vel.add(tdx,tdy);
          float tdx2=pos2.x-pos1.x,
            tdy2=pos2.y-pos1.y;
          if(tdx2>0) {
            tdx2-=dist/2;
            if(tdx2<0) tdx2=0;
          }else {
            tdx2+=dist/2;
            if(tdx2>0) tdx2=0;
          }
          if(tdy2>0) {
            tdy2-=dist/2;
            if(tdy2<0) tdy2=0;
          }else {
            tdy2+=dist/2;
            if(tdy2>0) tdy2=0;
          }
          i.point.vel.add(tdx2*0.2f,tdy2*0.2f);
        }
      }
    }
  }
}
