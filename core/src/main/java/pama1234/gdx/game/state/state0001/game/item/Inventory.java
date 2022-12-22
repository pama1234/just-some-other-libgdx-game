package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.Creature;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class Inventory<T extends Item>{
  public Creature pc;
  public InventorySlot<T>[] data;
  // private boolean displayHotSlot;
  public boolean displayHotSlot;
  public HotSlot<T>[] hotSlots;
  public float rSize=36;
  public PathVar r;
  // public int hotSlotSize=9;
  public int selectSlot;
  public Inventory(Creature pc,int size,int hotSlotSize) {
    this.pc=pc;
    data=new InventorySlot[size];
    hotSlots=new HotSlot[hotSlotSize];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot<T>();
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new HotSlot<T>(data[i]);
    r=new PathVar(rSize);
  }
  public void displayHotSlot(boolean in) {
    if(displayHotSlot==in) return;
    if(in) {
      r.des=rSize;
      r.pos=0;
    }
    displayHotSlot=in;
  }
  public void update() {
    if(displayHotSlot) r.update();
    else return;
    Screen0011 p=pc.p;
    for(int i=0;i<hotSlots.length;i++) {
      // T ti=hotSlots[i].data.item;
      // hotSlots[i].update(pc,ti.type.tiles[ti.displayType[0]],i/hotSlots.length+(float)(p.frameCount%timeF)/timeF);
      hotSlots[i].update(pc,hotSlots[i].data.item,(float)i/hotSlots.length+(float)(p.frameCount%timeF)/timeF,r.pos);
    }
  }
  public void displayHotSlot() {
    if(!displayHotSlot) return;
    Screen0011 p=pc.p;
    p.beginBlend();
    // p.fill(1);
    p.tint(255,191);
    for(int i=0;i<hotSlots.length;i++) {//TODO change to class HotSlot
      // if(data[i]==null) continue;
      HotSlot<T> ths=hotSlots[i];
      Item ti=ths.data.item;
      TextureRegion tr;
      if(ti!=null) tr=ti.type.tiles[ti.displayType[0]];
      else tr=pc.pw.itemC.empty.tiles[0];
      // p.endDraw();
      // p.fill(127,127);
      // p.rect(ths.x1,ths.y1,ths.w,ths.h);
      p.image(tr,ths.x1,ths.y1);
      // displayInInventory(pc.p,ti,pc.cx(),pc.cy(),36,(float)i/hotSlots.length);//TODO tint image
    }
    p.noTint();
    // p.endBlend();
    p.fill(14,229,234,127);
    float r=2;
    // float tcx=hotSlots[selectSlot].cx;
    // float tcy=hotSlots[selectSlot].cy;
    float tx1=hotSlots[selectSlot].x1-r;
    float ty1=hotSlots[selectSlot].y1-r;
    float tx2=hotSlots[selectSlot].x2+r;
    float ty2=hotSlots[selectSlot].y2+r;
    float tw=hotSlots[selectSlot].w+r*2;
    float th=hotSlots[selectSlot].h+r*2;
    p.rect(tx1,ty1,1,th);
    p.rect(tx1,ty1,tw,1);
    p.rect(tx2-1,ty1,1,th);
    p.rect(tx1,ty2-1,tw,1);
    // drawSelectSlotFrame();
    p.endBlend();
  }
  // public void drawSelectSlotFrame() {
  //   Screen0011 p=pc.p;
  //   // p.beginBlend();
  //   p.fill(14,229,234,127);
  //   drawSlotFrame(p,(float)selectSlot/hotSlots.length+(float)(p.frameCount%timeF)/timeF,data[selectSlot].item.type.tiles[data[selectSlot].item.displayType[0]]);
  //   // p.endBlend();
  // }
  // public void drawSlotFrame(Screen0011 p,float i,TextureRegion tr) {
  //   float r=2;
  //   float tcx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*(float)36;
  //   float tcy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*36;
  //   float tx1=tcx-tr.getRegionWidth()/2f-r;
  //   float ty1=tcy-tr.getRegionHeight()/2f-r;
  //   float tx2=tcx+tr.getRegionWidth()/2f+r;
  //   float ty2=tcy+tr.getRegionHeight()/2f+r;
  //   float tw=tr.getRegionWidth()+r*2;
  //   float th=tr.getRegionHeight()+r*2;
  //   p.rect(tx1,ty1,1,th);
  //   p.rect(tx1,ty1,tw,1);
  //   p.rect(tx2-1,ty1,1,th);
  //   p.rect(tx1,ty2-1,tw,1);
  // }
  public static int timeF=7200;
  // public static void displayInInventory(Screen0011 p,Item in,float x,float y,float r,float i) {
  //   i+=(float)(p.frameCount%timeF)/timeF;
  //   TextureRegion tr=in.type.tiles[in.displayType[0]];
  //   p.image(tr,
  //     x+UtilMath.sin(i*UtilMath.PI2)*r-tr.getRegionWidth()/2f,
  //     y+UtilMath.cos(i*UtilMath.PI2)*r-tr.getRegionHeight()/2f);
  // }
  public static class InventorySlot<T extends Item>{
    public T item;
  }
  public static class HotSlot<T extends Item>{
    public InventorySlot<T> data;
    public float cx,cy,x1,y1,x2,y2,w,h;
    public HotSlot(InventorySlot<T> pos) {
      this.data=pos;
    }
    // public void update(Creature pc,TextureRegion tr,float i,float r) {
    //   cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
    //   cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
    //   x1=cx-tr.getRegionWidth()/2f;
    //   y1=cy-tr.getRegionHeight()/2f;
    //   x2=cx+tr.getRegionWidth()/2f;
    //   y2=cy+tr.getRegionHeight()/2f;
    //   w=tr.getRegionWidth();
    //   h=tr.getRegionHeight();
    // }
    public void update(Creature pc,T in,float i,float r) {
      cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
      cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
      // float tw,th;
      if(in==null) {
        // tw=th=18;//TODO
        w=h=18;
      }else {
        TextureRegion tr=in.type.tiles[in.displayType[0]];
        w=tr.getRegionWidth();
        h=in.type.tiles[in.displayType[0]].getRegionHeight();
      }
      x1=cx-w/2f;
      y1=cy-h/2f;
      x2=cx+w/2f;
      y2=cy+h/2f;
      // w=tw;
      // h=th;
    }
  }
}