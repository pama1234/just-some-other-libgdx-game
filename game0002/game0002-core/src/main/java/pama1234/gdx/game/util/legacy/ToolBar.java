package pama1234.gdx.game.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;

public class ToolBar extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public String[][] names;
  public int[] state;
  public static final int up=0,down=1,left=2,right=3;
  public final boolean[] keys=new boolean[4];
  public static final float cellViewSpeed=Cell.dist/24;
  public Cell select;
  public LinkedList<Cell> near=new LinkedList<Cell>();
  public float dist=Cell.dist*6;
  public int originalId;
  public ToolBar(RealGame p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    //    layerInit();
    this.parent=parent;
    names=new String[][] {{"暂无操作",},{"移动粒子","批量移动"},};
    state=new int[2];
    state[1]=1;
  }
  @Override
  public void init() {
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void beforeDraw() {
    int tw=w;
    w=1;
    final String[] tsa=names[parent.index];
    for(String i:tsa) {
      final int t=(int)Math.ceil(p.textWidthNoScale(i)+textConst);
      if(t>w) w=t;
    }
    int th=h;
    h=(int)(textConst*(tsa.length+0.25f));
    if(tw!=w||th!=h) graphics(new Graphics(p,w,h));
  }
  public void draw() {
    p.background(UtilScreenColor.colorFromInt(0xffF66104));
    p.textColor(0);
    // p.doFill();
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textConst/2;
    final String[] tsa=names[parent.index];
    for(int i=0;i<tsa.length;i++) {
      String ts=tsa[i];
      final float tby=ty;
      p.fill(UtilScreenColor.colorFromInt(i==state[parent.index]?0xff6FEDFB:0xffDDF4C4));
      p.rect(textConst/2,tby,w-textConst/2,textConst);
      UITools.border(g,textConst/2,tby,w-textConst/2,textConst);
      p.fill(0);
      p.text(ts,ts_d2,ty);
      ty+=textConst;
    }
  }
  @Override
  public void update() {
    point.update();
    final int ti=parent.index;
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
        tdx+=p.currentInput.dx;
        tdy+=p.currentInput.dy;
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
    p.image(g.texture,point.pos.x,point.pos.y);
    if(select!=null) drawSelect();
  }
  public void drawSelect() {
    p.strokeWeight(0.5f/p.cam2d.ocam.zoom);
    p.noFill();
    p.doStroke();
    p.beginBlend();
    p.stroke(p.colorFromInt(0x80ffffff));
    final float tx=select.point.pos.x,
      ty=select.point.pos.y,
      ts_m2=Cell.size*2,
      ts_d2=Cell.size/2;
    // ts_d2m3=ts_d2*3;
    final boolean flag=parent.index==1&&state[parent.index]==1;
    final float ts_m2_2=flag?ts_m2+dist:ts_m2,
      ts_d2m3=flag?ts_d2*2+dist:ts_d2*3,
      ts_d2m4=flag?ts_d2*2:ts_d2;
    p.circle(tx,ty,ts_m2/2);
    if(flag) {
      p.circle(tx,ty,dist);
      for(Cell i:near) {
        Vec2f pos=i.point.pos;
        p.circle(pos.x,pos.y,ts_m2/2);
      }
    }
    if(keys[up]) {
      p.line(tx,ty-ts_m2_2,tx-ts_d2m4,ty-ts_d2m3);
      p.line(tx,ty-ts_m2_2,tx+ts_d2m4,ty-ts_d2m3);
    }
    if(keys[down]) {
      p.line(tx,ty+ts_m2_2,tx-ts_d2m4,ty+ts_d2m3);
      p.line(tx,ty+ts_m2_2,tx+ts_d2m4,ty+ts_d2m3);
    }
    if(keys[left]) {
      p.line(tx-ts_m2_2,ty,tx-ts_d2m3,ty-ts_d2m4);
      p.line(tx-ts_m2_2,ty,tx-ts_d2m3,ty+ts_d2m4);
    }
    if(keys[right]) {
      p.line(tx+ts_m2_2,ty,tx+ts_d2m3,ty-ts_d2m4);
      p.line(tx+ts_m2_2,ty,tx+ts_d2m3,ty+ts_d2m4);
    }
    p.noStroke();
    p.doFill();
    p.endBlend();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    final Vec2f pos=point.pos;
    final int ti=parent.index;
    if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
      if(info.button==Buttons.LEFT) {
        final int index=(int)Math.floor((p.mouse.y-pos.y)/textConst);
        if(index>=0&&index<names[ti].length) {
          if(p.mouse.x>pos.x+textConst*0.5) {
            state[ti]=index;
            refresh();
          }
        }
      }
    }else {
      if(ti==1) {
        if(state[ti]==0||state[ti]==1) {
          // System.out.println(info.state);
          if(info.button==Buttons.LEFT&&info.state==0) {
            if(select!=null) {
              select.meta=originalId;
              select=null;
            }
            float minDist=Cell.size;
            for(Cell i:cellCenter.list) {
              final float td=UtilMath.dist(i.point.pos.x,i.point.pos.y,p.mouse.x,p.mouse.y);
              if(td<minDist) {
                select=i;
                minDist=td;
              }
            }
            if(select!=null) originalId=select.meta;
            else originalId=-1;
          }
        }
      }
    }
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(key==' ') {
      parent.select.update=!parent.select.update;
      parent.refresh();
    }
    final int ti=parent.index;
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
    final int ti=parent.index;
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
      }
    }
  }
}
