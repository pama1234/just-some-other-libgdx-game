package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class Inventory{
  public static final int noDisplay=0,displayHotSlot=1,displayFullInventory=2;
  public static int timeF=7200;
  public LivingEntity pc;
  public InventorySlot[] data;
  // public boolean displayInventory;
  public int displayState=displayHotSlot;
  public HotSlot[] hotSlots;
  public float rSize=36;
  public PathVar r;
  // public int hotSlotSize=9;
  public int selectSlot;
  public Inventory(LivingEntity pc,int size,int hotSlotSize) {
    this.pc=pc;
    data=new InventorySlot[size];
    hotSlots=new HotSlot[hotSlotSize];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot();
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new HotSlot(data[i]);
    r=new PathVar(rSize);
  }
  public HotSlot select() {
    return hotSlots[selectSlot];
  }
  public void select(HotSlot in) {
    hotSlots[selectSlot]=in;
  }
  public void displayStateChange() {
    if(displayState==displayFullInventory) safeDisplayState(displayHotSlot);
    else safeDisplayState(displayFullInventory);
  }
  public void displayState(int in) {
    if(displayState==in) return;
    safeDisplayState(in);
  }
  private void safeDisplayState(int in) {
    if(in==displayFullInventory) {
      r.des=rSize;
      r.pos=0;
    }
    displayState=in;
  }
  public void display() {
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHotSlot: {}
        break;
      case displayFullInventory: {
        displayHotSlotCircle();
      }
        break;
    }
    if(displayState==displayFullInventory) displayHotSlotCircle();
  }
  public void update() {
    if(displayState==displayFullInventory) r.update();
    else return;
    Screen0011 p=pc.p;
    for(int i=0;i<hotSlots.length;i++) {
      hotSlots[i].update(pc,hotSlots[i].data.item,(float)i/hotSlots.length+(float)(p.frameCount%timeF)/timeF,r.pos);
    }
  }
  public void displayHotSlotCircle() {
    Screen0011 p=pc.p;
    p.beginBlend();
    for(int i=0;i<hotSlots.length;i++) {
      HotSlot ths=hotSlots[i];
      Item ti=ths.data.item;
      TextureRegion tr;
      // if(ti!=null) {
      //   tr=ti.type.tiles[ti.displayType[0]];
      //   p.noTint();
      // }else {
      //   tr=pc.pw.metaItems.empty.tiles[0];
      //   p.tint(255,127);
      // }
      tr=pc.pw.metaItems.empty.tiles[0];
      p.tint(255,127);
      p.image(tr,ths.x1,ths.y1);
      if(ti!=null) {
        tr=ti.type.tiles[ti.displayType[0]];
        p.noTint();
        p.image(tr,ths.x1,ths.y1,ths.w,ths.h);
      }
    }
    p.noTint();
    // p.endBlend();
    drawSelectRect(p);
    p.endBlend();
  }
  private void drawSelectRect(Screen0011 p) {
    p.fill(14,229,234,127);
    float r=2;
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
  }
  public void displayHotSlot() {}
  public void displayInventoryCircle() {}
  //------------------------------------------------------------------------------------
  public static class InventorySlot{
    public Item item;
  }
  public static class HotSlot{
    public InventorySlot data;
    public float cx,cy,x1,y1,x2,y2,w,h;
    public HotSlot(InventorySlot pos) {
      this.data=pos;
    }
    public void update(LivingEntity pc,Item in,float i,float r) {
      cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
      cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
      if(in==null) w=h=18;
      else {
        TextureRegion tr=in.type.tiles[in.displayType[0]];
        w=tr.getRegionWidth();
        h=tr.getRegionHeight();
        // w=tr.getRegionWidth()/3f*2;
        // h=tr.getRegionHeight()/3f*2;
      }
      x1=cx-w/2f;
      y1=cy-h/2f;
      x2=cx+w/2f;
      y2=cy+h/2f;
    }
  }
}