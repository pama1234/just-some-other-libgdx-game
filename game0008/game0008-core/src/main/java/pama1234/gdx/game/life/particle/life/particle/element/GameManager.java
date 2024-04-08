package pama1234.gdx.game.life.particle.life.particle.element;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.app.Screen0045.GameMode;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.CellCenterRenderer;
import pama1234.gdx.game.life.particle.life.particle.MetaCellCenter;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectF;
import pama1234.math.physics.PathPoint3D;
import pama1234.math.vec.Vec2f;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.element.GameManagerServer;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

// TODO 改为继承自GameAvatarManager
public class GameManager extends Entity<Screen0045>{
  public CellCenterRenderer cellCenter;
  public MetaCellCenter metaCenter;
  public ParticleSandbox parent;

  public GameManagerServer core;

  public int[] state;
  public final int up=0,down=1,left=2,right=3;
  public final boolean[] keys=new boolean[4];
  public final float cellViewSpeed=CellServer.dist/24;

  public Cell select;
  public RectF excludeRect;

  public Color strokeColor;

  public float dx,dy;
  public int cellMetaTypeShift;

  public boolean inClient;

  public GameManager(Screen0045 p,ParticleSandbox parent) {
    super(p);
    this.parent=parent;
    core=parent.core.gameManager;

    state=new int[2];
    state[1]=1;
    excludeRect=new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu*4f,()->p.bu*1.5f);

    strokeColor=UtilScreenColor.newColorFromInt(0x80ffffff);

    cellCenter=(CellCenterRenderer)parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  public void doSelect() {
    doSelect(0);
  }
  public void doSelect(Cell in) {
    select=in;
    core.doSelect(in.core);
  }
  public void doSelect(int in) {
    // Cell cell=null;
    // for(Cell i:cellCenter.list) {
    //   if(i.core.id.listPos==in) {
    //     cell=i;
    //     break;
    //   }
    // }
    // doSelect(cell);
    doSelect(cellCenter.list.get(in));
  }
  public void undoSelect() {
    core.undoSelect();
    select=null;
  }
  @Override
  public void update() {
    final int ti=1;
    if(state[ti]==0) {
      if(select!=null) {
        Vec2f vel=select.core.point.vel;
        if(keys[left]!=keys[right]) {
          if(keys[left]) vel.x+=-cellViewSpeed;
          else vel.x+=cellViewSpeed;
        }
        if(keys[up]!=keys[down]) {
          if(keys[up]) vel.y+=-cellViewSpeed;
          else vel.y+=cellViewSpeed;
        }
        moveCamDes();

      }
    }else if(state[ti]==1) moveInCircle();
  }
  public void moveCamDes() {
    PathPoint3D cam=p.cam.point;
    cam.des.set(select.core.point.pos);
    // float tx=cam.des.x;
    // float ty=cam.des.y;
    // tx=Tools.moveInRange(tx,cellCenter.core.x1,cellCenter.core.x2);
    // ty=Tools.moveInRange(ty,cellCenter.core.y1,cellCenter.core.y2);
    // cam.des.set(tx,ty);
  }
  public void moveInCircle() {
    if(select!=null) {
      float tdx=0,tdy=0;
      if(p.isAndroid) {
        float dx=p.currentInput.dx,
          dy=p.currentInput.dy;
        if(UtilMath.abs(dx)>0.01f||UtilMath.abs(dy)>0.01f) {
          float tf=cellViewSpeed/UtilMath.mag(dx,dy);
          tdx+=dx*tf;
          tdy+=dy*tf;
        }
      }
      if(keys[left]!=keys[right]) {
        if(keys[left]) tdx=-cellViewSpeed;
        else tdx=cellViewSpeed;
      }
      if(keys[up]!=keys[down]) {
        if(keys[up]) tdy=-cellViewSpeed;
        else tdy=cellViewSpeed;
      }

      dx=tdx;
      dy=tdy;

      select.core.point.vel.add(tdx,tdy);
      // if(!inClient)
      updateNearCellList(true);
      moveCamDes();
    }
  }
  public void updateNearCellList(boolean moveNearCellToAvatar) {
    Vec2f avatarPos=select.core.point.pos;
    core.near.clear();
    // TODO 避免synchronized满天飞
    // synchronized(cellCenter.list) {
    for(Cell iCell:cellCenter.list) {
      if(iCell==select||iCell.core.meta!=select.core.meta) continue;
      final Vec2f nearCellPos=iCell.core.point.pos;
      final float tempDist;
      if(!parent.cellCenter.core.boxed) {
        float dx=nearCellPos.x-avatarPos.x;
        float dy=nearCellPos.y-avatarPos.y;
        if(dx>parent.cellCenter.core.w/2) dx=parent.cellCenter.core.w-dx;
        if(dy>parent.cellCenter.core.h/2) dy=parent.cellCenter.core.h-dy;
        tempDist=UtilMath.mag(dx,dy);
      }else {
        tempDist=UtilMath.dist(
          nearCellPos.x,
          nearCellPos.y,
          avatarPos.x,
          avatarPos.y);
      }
      moveNearCellToAvatar(avatarPos,iCell,nearCellPos,tempDist,moveNearCellToAvatar);
    }
    // }
  }
  public void moveNearCellToAvatar(
    Vec2f avatarPos,Cell iCell,final Vec2f nearCellPos,final float tempDist,
    boolean moveNearCellToAvatar) {
    if(tempDist<core.dist) {
      core.near.add(iCell.core);
      if(moveNearCellToAvatar) {
        iCell.core.point.vel.add(dx,dy);
        float tdx2=avatarPos.x-nearCellPos.x,
          tdy2=avatarPos.y-nearCellPos.y;
        if(tdx2>0) {
          tdx2-=core.dist/2;
          if(tdx2<0) tdx2=0;
        }else {
          tdx2+=core.dist/2;
          if(tdx2>0) tdx2=0;
        }
        if(tdy2>0) {
          tdy2-=core.dist/2;
          if(tdy2<0) tdy2=0;
        }else {
          tdy2+=core.dist/2;
          if(tdy2>0) tdy2=0;
        }
        iCell.core.point.vel.add(tdx2*0.2f,tdy2*0.2f);
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
    p.stroke(strokeColor,0x80);
    final float tx=select.core.point.pos.x,
      ty=select.core.point.pos.y;
    final float ts_m2=CellServer.size*2;
    final boolean flag=state[1]==1;
    UITools.cross(p,tx,ty,ts_m2/4);
    p.circle(tx,ty,ts_m2/2);
    if(flag) {
      p.stroke(metaCenter.list.get(select.core.meta).colorCache,0x80);
      p.circle(tx,ty,core.dist);
      p.stroke(255,0x60);
      for(CellServer i:core.near) {
        Vec2f pos=i.point.pos;
        p.circle(pos.x,pos.y,ts_m2/2);
      }
    }
    p.noStroke();
    p.doFill();
    p.endBlend();
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(p.gameMode==GameMode.God) {
      final int ti=1;
      if(state[ti]==0||state[ti]==1) {
        if(!p.isAndroid||!Tools.inBox(info.ox,info.oy,excludeRect)&&(p.actrl.active?info.ox>p.width/3f:true)) {//TODO
          if(info.button==Buttons.LEFT) {
            if(select!=null) {
              undoSelect();
            }
            float minDist=CellServer.size;
            for(Cell i:cellCenter.list) {
              final float td=UtilMath.dist(i.core.point.pos.x,i.core.point.pos.y,info.x,info.y);
              if(td<minDist) {
                select=i;
                minDist=td;
              }
            }
            if(select!=null) {
              p.activeACtrl(true);
              core.originalCellType=select.core.meta;
            }else {
              p.activeACtrl(false);
              core.originalCellType=-1;
            }
          }
        }
      }
    }
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    final int ti=1;
    if(state[ti]==0||state[ti]==1) {
      key=Character.toLowerCase(key);
      switch(key) {
        case 'w':
          keys[up]=true;
          break;
        case 's':
          keys[down]=true;
          break;
        case 'a':
          keys[left]=true;
          break;
        case 'd':
          keys[right]=true;
          break;
      }
      switch(keyCode) {
        case Keys.UP:
          keys[up]=true;
          break;
        case Keys.DOWN:
          keys[down]=true;
          break;
        case Keys.LEFT:
          keys[left]=true;
          break;
        case Keys.RIGHT:
          keys[right]=true;
          break;
      }
      if(select!=null) switch(key) {
        case 'z':
        case 'e': {
          int index=select.core.meta-1;
          if(index<0) index+=select.parent.meta.list.size();
          cellMetaTypeShift+=-1;
          // cellMetaTypeShift=index-select.meta;
          select.core.meta=index;
        }
          break;
        case 'x':
        case 'q': {
          int index=select.core.meta+1;
          final int ts=select.parent.meta.list.size();
          if(index>=ts) index-=ts;
          cellMetaTypeShift+=1;
          // cellMetaTypeShift=index-select.meta;
          select.core.meta=index;
        }
          break;
      }
    }
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    final int ti=1;
    if(state[ti]==0||state[ti]==1) {
      key=Character.toLowerCase(key);
      switch(key) {
        case 'w':
          keys[up]=false;
          break;
        case 's':
          keys[down]=false;
          break;
        case 'a':
          keys[left]=false;
          break;
        case 'd':
          keys[right]=false;
          break;
      }
      switch(keyCode) {
        case Keys.UP:
          keys[up]=false;
          break;
        case Keys.DOWN:
          keys[down]=false;
          break;
        case Keys.LEFT:
          keys[left]=false;
          break;
        case Keys.RIGHT:
          keys[right]=false;
          break;
      }
    }
  }
}
