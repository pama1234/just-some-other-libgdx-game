package pama1234.gdx.game.life.particle.life.particle.element;

import java.util.LinkedList;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.CellCenter;
import pama1234.gdx.game.life.particle.life.particle.MetaCellCenter;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.element.GameManagerServer;
import pama1234.server.game.life.particle.net.message.GameCtrlCache;

public class GameAvatarManager extends Entity<Screen0045>{
  public GameManagerServer core;

  public ParticleSandbox parent;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;

  public Cell select;
  public LinkedList<Cell> near=new LinkedList<>();

  public float dist=CellServer.dist*6;
  // public int originalCellType;

  // public Color strokeColor;

  public GameCtrlCache gameCtrlCache;

  public GameAvatarManager(Screen0045 p,ParticleSandbox parent) {
    super(p);
    this.parent=parent;

    core=new GameManagerServer(null,parent.core);

    // strokeColor=UtilScreenColor.newColorFromInt(0x80ffffff);

    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  public void doSelect(int in) {
    core.doSelect(in);
    select=cellCenter.list.get(in);
    // select.core.isPlayer=true;
    // core.originalCellType=select.core.meta;
  }
  public void undoSelect() {
    core.undoSelect();
    // select.core.meta=core.originalCellType;
    // select.core.isPlayer=false;
    select=null;
  }
  public void update(GameCtrlCache gameCtrlCache) {
    this.gameCtrlCache=gameCtrlCache;
    select.core.meta=gameCtrlCache.cellMetaType;
    select.core.meta=Tools.moveInRange(select.core.meta,0,metaCenter.list.size());
    update();
  }
  @Override
  public void update() {
    moveInCircle();
  }
  public void moveInCircle() {
    if(select!=null) {
      float tdx=gameCtrlCache.dx,tdy=gameCtrlCache.dy;

      select.core.point.vel.add(tdx,tdy);
      final Vec2f pos2=select.core.point.pos;
      near.clear();
      for(Cell i:cellCenter.list) {
        if(i==select||i.core.meta!=select.core.meta) continue;
        final Vec2f pos1=i.core.point.pos;
        final float td;
        if(!parent.cellCenter.core.boxed) {
          float dx=pos1.x-pos2.x;
          float dy=pos1.y-pos2.y;
          if(dx>parent.cellCenter.core.w/2) dx=parent.cellCenter.core.w-dx;
          if(dy>parent.cellCenter.core.h/2) dy=parent.cellCenter.core.h-dy;
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
          i.core.point.vel.add(tdx,tdy);
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
          i.core.point.vel.add(tdx2*0.2f,tdy2*0.2f);
        }
      }
    }
  }
  @Override
  public void display() {
    if(select!=null) drawSelect();
  }
  /**
   * 绘制玩家角色（红色圆形）的额外UI
   */
  public void drawSelect() {
    p.strokeWeight(0.5f/p.cam2d.ocam.zoom);
    p.noFill();
    p.doStroke();
    p.beginBlend();
    p.stroke(255,0x80);
    final float tx=select.core.point.pos.x,
      ty=select.core.point.pos.y;
    final float ts_m2=CellServer.size*2;
    UITools.cross(p,tx,ty,ts_m2/4);
    p.circle(tx,ty,ts_m2/2);
    p.stroke(metaCenter.list.get(select.core.meta).colorCache,0x80);
    p.circle(tx,ty,dist);
    p.stroke(255,0x60);
    for(Cell i:near) {
      Vec2f pos=i.core.point.pos;
      p.circle(pos.x,pos.y,ts_m2/2);
    }
    p.noStroke();
    p.doFill();
    p.endBlend();
  }
}
