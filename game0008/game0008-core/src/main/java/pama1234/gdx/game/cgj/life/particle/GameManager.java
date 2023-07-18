package pama1234.gdx.game.cgj.life.particle;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.app.app0002.RealGame0002.GameMode;
import pama1234.gdx.game.cgj.util.legacy.TabCenter;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectF;
import pama1234.math.vec.Vec2f;

public class GameManager extends Entity<RealGame0002>{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public int[] state;
  public static final int up=0,down=1,left=2,right=3;
  public final boolean[] keys=new boolean[4];
  public static final float cellViewSpeed=Cell.dist/24;
  public static Cell select;
  public LinkedList<Cell> near=new LinkedList<Cell>();
  public float dist=Cell.dist*6;
  public int originalId;
  public RectF excludeRect;
  public boolean firstInit=true;
  //---
  public Color background;
  public Color strokeColor;
  public Color buttonColor_1,buttonColor_2;
  public GameManager(RealGame0002 p,TabCenter parent) {
    super(p);
    this.parent=parent;
    state=new int[2];
    state[1]=1;
    excludeRect=new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu*4f,()->p.bu*1.5f);
    //---
    background=UtilScreenColor.newColorFromInt(0xffF66104);
    strokeColor=UtilScreenColor.newColorFromInt(0x80ffffff);
    buttonColor_1=UtilScreenColor.newColorFromInt(0xff6FEDFB);
    buttonColor_2=UtilScreenColor.newColorFromInt(0xffDDF4C4);
  }
  @Override
  public void init() {
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  public void show() {
    if(!firstInit) {
      if(p.gameMode==GameMode.Survival) select=cellCenter.list.getFirst();
      return;
    }
    select=cellCenter.add.getFirst();
    originalId=select.meta;
    firstInit=false;
  }
  public void hide() {
    if(select!=null) {
      select.meta=originalId;
      select=null;
    }
  }
  @Override
  public void update() {
    final int ti=parent.pointer;
    if(ti==1) {
      if(state[ti]==0) {
        if(select!=null) {
          if(keys[left]!=keys[right]) {
            if(keys[left]) select.point.vel.x-=cellViewSpeed;
            else select.point.vel.x+=cellViewSpeed;
          }
          if(keys[up]!=keys[down]) {
            if(keys[up]) select.point.vel.y-=cellViewSpeed;
            else select.point.vel.y+=cellViewSpeed;
          }
          p.cam.point.des.set(select.point.pos);
        }
      }else if(state[ti]==1) moveInCircle();
    }
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
      select.point.vel.add(tdx,tdy);
      final Vec2f pos2=select.point.pos;
      near.clear();
      for(Cell i:cellCenter.list) {
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
      p.cam.point.des.set(select.point.pos);
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
    final float tx=select.point.pos.x,
      ty=select.point.pos.y;
    final float ts_m2=Cell.size*2;
    final boolean flag=parent.pointer==1&&state[parent.pointer]==1;
    UITools.cross(p,tx,ty,ts_m2/4);
    p.circle(tx,ty,ts_m2/2);
    if(flag) {
      p.stroke(metaCenter.list.get(select.meta).colorCache,0x80);
      p.circle(tx,ty,dist);
      p.stroke(255,0x60);
      for(Cell i:near) {
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
      final int ti=parent.pointer;
      if(ti==1) {
        if(state[ti]==0||state[ti]==1) {
          if(!p.isAndroid||!Tools.inBox(info.ox,info.oy,excludeRect)&&(p.actrl.active?info.ox>p.width/3f:true)) {//TODO
            if(info.button==Buttons.LEFT) {
              if(select!=null) {
                select.meta=originalId;
                select=null;
              }
              float minDist=Cell.size;
              for(Cell i:cellCenter.list) {
                final float td=UtilMath.dist(i.point.pos.x,i.point.pos.y,info.x,info.y);
                if(td<minDist) {
                  select=i;
                  minDist=td;
                }
              }
              if(select!=null) {
                p.activeActrl(true);
                originalId=select.meta;
              }else {
                p.activeActrl(false);
                originalId=-1;
              }
            }
          }
        }
      }
    }
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(key==' ') {
      parent.get().update=!parent.get().update;
    }
    final int ti=parent.pointer;
    if(ti==1) {
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
            int index=select.meta-1;
            if(index<0) index+=select.parent.meta.list.size();
            select.meta=index;
          }
            break;
          case 'x':
          case 'q': {
            int index=select.meta+1;
            final int ts=select.parent.meta.list.size();
            if(index>=ts) index-=ts;
            select.meta=index;
          }
            break;
        }
      }
    }
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    final int ti=parent.pointer;
    if(ti==1) {
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
}
