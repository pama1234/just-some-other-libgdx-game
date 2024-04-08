package pama1234.gdx.game.life.particle.life.particle;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.net.message.CellCache;

import com.badlogic.gdx.graphics.Color;

public class Cell extends Entity<Screen0045> implements Comparable<Cell>{
  public CellCenter parent;

  public CellServer core;

  public Cell(Screen0045 p,CellCenter parent,CellServer core) {
    super(p);
    this.parent=parent;
    this.core=core;
  }
  public Cell(Screen0045 p,CellCenter parent,int listPos,int meta,float x,float y) {
    this(p,parent,new CellServer(p.serverCore,parent.core,listPos,meta,x,y));
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    core.update();
  }
  @Override
  public void display() {
    Color colorCache=parent.meta.list.get(core.meta).colorCache;
    Cell player=parent.particleSandbox.gameManager.select;
    if(player==this) p.fill(colorCache);
    else {
      int cellAlpha=cellAlpha(colorCache,player);
      if(cellAlpha<=0) return;
      p.fill(colorCache,cellAlpha);
    }

    float circleRadius=CellServer.size/2f;

    float tx=core.point.pos.x;
    float ty=core.point.pos.y;

    if(parent.core.boxed) {
      p.circle(
        tx,
        ty,
        circleRadius);

      if(parent.netMode) {
        if(core.isPlayer) {
          p.fill(255);
          p.circle(
            tx,
            ty,
            CellServer.size/4f);
        }
      }
    }else {
      p.circle(
        tx,
        ty,
        circleRadius);

      int coreW=parent.core.w;
      int coreH=parent.core.h;
      Vec2f mainPlayerPos=player.core.point.pos;
      float viewRange=parent.core.viewRange;
      float tx1=mainPlayerPos.x-viewRange;
      float tx2=mainPlayerPos.x+viewRange;
      float ty1=mainPlayerPos.y-viewRange;
      float ty2=mainPlayerPos.y+viewRange;

      tx=Tools.moveInRange(tx,tx1,tx2,coreW);
      ty=Tools.moveInRange(ty,ty1,ty2,coreH);

      p.circle(
        tx,
        ty,
        circleRadius);

      if(parent.netMode) {
        if(core.isPlayer) {
          p.fill(255);
          p.circle(
            tx,
            ty,
            CellServer.size/4f);
        }
      }
    }
  }
  public int cellAlpha(Color colorCache,Cell player) {
    float dist=distOf(player);
    float byViewRange=(UtilMath.constrain(dist,0,parent.core.viewRange)/parent.core.viewRange)*255;
    // colorCache.a的区间为0-1
    return (int)(colorCache.a*(255-byViewRange));
  }
  public float distOf(Cell player) {
    if(parent.core.boxed) return player.core.point.pos.dist(core.point.pos);
    else {
      float tx=core.point.pos.x;
      float ty=core.point.pos.y;

      int coreW=parent.core.w;
      int coreH=parent.core.h;
      Vec2f mainPlayerPos=player.core.point.pos;
      float viewRange=parent.core.viewRange;
      float tx1=mainPlayerPos.x-viewRange;
      float tx2=mainPlayerPos.x+viewRange;
      float ty1=mainPlayerPos.y-viewRange;
      float ty2=mainPlayerPos.y+viewRange;

      tx=Tools.moveInRange(tx,tx1,tx2,coreW);
      ty=Tools.moveInRange(ty,ty1,ty2,coreH);

      return UtilMath.dist(mainPlayerPos.x,mainPlayerPos.y,tx,ty);
    }
  }
  public void set(CellCache in) {
    core.set(in);
  }
  public boolean visibleFromCell(Cell avatar) {
    return core.visibleFromCell(avatar.core);
  }
  @Override
  public int compareTo(Cell o) {
    return core.id.listPos-o.core.id.listPos;
  }
}
