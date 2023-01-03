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
  public DisplaySlot[] hotSlots;
  public DisplaySlot holdSlot;
  public float rSize;
  public PathVar r;
  // public int hotSlotSize=9;
  public int selectSlot;
  public Inventory(LivingEntity pc,int size,int hotSlotSize) {
    this.pc=pc;
    data=new InventorySlot[size];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot();
    hotSlots=new DisplaySlot[hotSlotSize];
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new DisplaySlot(data[i]);
    holdSlot=new DisplaySlot(data[data.length-1]);
    r=new PathVar(rSize=UtilMath.min(pc.type.w,pc.type.h));
  }
  public void switchHold(DisplaySlot in) {
    Item ti=holdSlot.data.item;
    holdSlot.data.item=in.data.item;
    in.data.item=ti;
  }
  public DisplaySlot select() {
    return hotSlots[selectSlot];
  }
  public void select(int in) {
    selectSlot=in;
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
    if(displayState!=displayFullInventory) return;
    r.update();
    Screen0011 p=pc.p;
    for(int i=0;i<hotSlots.length;i++) hotSlots[i].circleUpdate(pc,(float)i/hotSlots.length+(float)(p.frameCount%timeF)/timeF,r.pos);
    holdSlot.centerUpdate(pc);
  }
  public void displayHotSlotCircle() {
    Screen0011 p=pc.p;
    p.beginBlend();
    for(int i=0;i<hotSlots.length;i++) displayHotSlot(p,hotSlots[i]);
    displayHotSlot(p,holdSlot);
    drawSelectRect(p);
    p.noTint();
    p.endBlend();
  }
  public void displayHotSlot(Screen0011 p,DisplaySlot ths) {
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
  public void drawSelectRect(Screen0011 p) {
    p.tint(255,191);
    DisplaySlot ths=hotSlots[selectSlot];
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
  public static class DisplaySlot{
    public InventorySlot data;
    public float cx,cy,x1,y1,x2,y2,w1,h1,w2,h2;
    public DisplaySlot(InventorySlot pos) {
      this.data=pos;
    }
    public void centerUpdate(LivingEntity pc) {
      cx=pc.cx();
      cy=pc.cy()+12;
      setDisplaySize(data.item);
      updatePosition();
    }
    public void circleUpdate(LivingEntity pc,float i,float r) {
      cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
      cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
      setDisplaySize(data.item);
      updatePosition();
    }
    public void updatePosition() {
      x1=cx-w1/2f;
      y1=cy-h1/2f;
      x2=cx+w1/2f;
      y2=cy+h1/2f;
    }
    public void setDisplaySize(Item in) {
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
    }
    public float w3() {
      return (w1-w2)/2f;
    }
    public float h3() {
      return (h1-h2)/2f;
    }
  }
}