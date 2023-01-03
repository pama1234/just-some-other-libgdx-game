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
      TextureRegion tr=pc.pw.metaItems.inventoryConfig.tiles[0];
      p.tint(255,127);
      p.image(tr,ths.x1,ths.y1);
      if(ti!=null) {
        tr=ti.type.tiles[ti.displayType[0]];
        p.noTint();
        p.image(tr,ths.x1+ths.w3(),ths.y1+ths.h3(),ths.w2,ths.h2);
      }
    }
    drawSelectRect(p);
    p.noTint();
    p.endBlend();
  }
  public void drawSelectRect(Screen0011 p) {
    HotSlot ths=hotSlots[selectSlot];
    TextureRegion tr=pc.pw.metaItems.inventoryConfig.tiles[1];
    p.tint(255,127);
    p.image(tr,ths.x1,ths.y1);
  }
  public void displayHotSlot() {}
  public void displayInventoryCircle() {}
  //------------------------------------------------------------------------------------
  public static class InventorySlot{
    public Item item;
  }
  public static class HotSlot{
    public InventorySlot data;
    public float cx,cy,x1,y1,x2,y2,w1,h1,w2,h2;
    public HotSlot(InventorySlot pos) {
      this.data=pos;
    }
    public void update(LivingEntity pc,Item in,float i,float r) {
      cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
      cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
      if(in==null) {
        w1=h1=18;
        w2=h2=0;
      }else {
        TextureRegion tr=in.type.tiles[in.displayType[0]];
        w1=tr.getRegionWidth();
        h1=tr.getRegionHeight();
        w2=tr.getRegionWidth()/3f*2;
        h2=tr.getRegionHeight()/3f*2;
      }
      x1=cx-w1/2f;
      y1=cy-h1/2f;
      x2=cx+w1/2f;
      y2=cy+h1/2f;
    }
    public float w3() {
      return (w1-w2)/2f;
    }
    public float h3() {
      return (h1-h2)/2f;
    }
  }
}