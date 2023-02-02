package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class Inventory{
  public static final int noDisplay=0,displayHoldSlot=1,displayFullInventory=2;
  public static int timeF=7200;
  public LivingEntity pc;
  public InventorySlot[] data;
  public int displayState=displayHoldSlot;
  public DisplaySlot[][] displaySlots;
  public DisplaySlot[] hotSlots,backpackSlots;
  public DisplaySlot holdSlot;
  // public DisplaySlot[] workStationSlots;
  public float rSize;
  public PathVar r;
  public int selectSlot;
  public float circleDeg;
  public Inventory(LivingEntity pc,int size,int hotSlotSize) {
    this.pc=pc;
    data=new InventorySlot[size];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot();
    hotSlots=new DisplaySlot[hotSlotSize];
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new DisplaySlot(data[i]);
    holdSlot=new DisplaySlot(data[data.length-1]);
    backpackSlots=new DisplaySlot[size-hotSlotSize-1];
    for(int i=0;i<backpackSlots.length;i++) backpackSlots[i]=new DisplaySlot(data[i+hotSlots.length]);
    displaySlots=new DisplaySlot[][] {hotSlots,backpackSlots};
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
  public void testSelectSlot() {
    selectSlot=Tools.moveInRange(selectSlot,0,hotSlots.length);
  }
  public void displayStateChange() {
    if(displayState==displayFullInventory) safeDisplayState(displayHoldSlot);
    else safeDisplayState(displayFullInventory);
  }
  public void displayState(int in) {
    if(displayState==in) return;
    safeDisplayState(in);
  }
  public void safeDisplayState(int in) {
    if(in==displayFullInventory) {
      r.des=rSize;
      r.pos=0;
    }
    displayState=in;
  }
  public void update() {
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldSlot: {
        holdSlot.centerUpdate(pc,0,12);
      }
        break;
      case displayFullInventory: {
        updateFullInventory();
      }
        break;
    }
  }
  public void updateFullInventory() {
    // if(displayState!=displayFullInventory) return;
    r.update();
    Screen0011 p=pc.p;
    for(int i=0;i<hotSlots.length;i++) hotSlots[i].circleUpdate(pc,
      (float)i/hotSlots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
      r.pos);
    int ts=18;
    if(backpackSlots.length>ts) {
      for(int i=0;i<ts;i++) backpackSlots[i].circleUpdate(pc,
        (float)i/ts+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*2);
      for(int i=ts;i<backpackSlots.length;i++) backpackSlots[i].circleUpdate(pc,
        (float)(i-ts)/(backpackSlots.length-ts)+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*3);
    }else {
      for(int i=0;i<backpackSlots.length;i++) backpackSlots[i].circleUpdate(pc,
        (float)i/backpackSlots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*2);
    }
    holdSlot.centerUpdate(pc,0,12);
    // for(DisplaySlot e:backpackSlots) e.centerUpdate(pc,0,0);
  }
  public void display() {
    Screen0011 p=pc.p;
    p.textScale(0.5f);
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldSlot: {
        displaySlotItem(pc.p,holdSlot);
      }
        break;
      case displayFullInventory: {
        drawSelectRect(p);
        displayHotSlotCircle();
        displayBackpackSlot();
        displaySlotWhenNotNull(p,holdSlot);
        drawMouseHold(p,holdSlot);
      }
        break;
    }
    p.textScale(1);
    // if(displayState==displayFullInventory) displayHotSlotCircle();
  }
  public void displayBackpackSlot() {
    Screen0011 p=pc.p;
    for(int i=0;i<backpackSlots.length;i++) displaySlot(p,backpackSlots[i]);
    p.noTint();
  }
  public void displayHotSlotCircle() {
    Screen0011 p=pc.p;
    for(int i=0;i<hotSlots.length;i++) displaySlot(p,hotSlots[i]);
    p.noTint();
  }
  public void drawMouseHold(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      TextureRegion tr=ti.type.tiles[ti.displayType[0]];
      p.image(tr,p.mouse.x-ths.w2/2f,p.mouse.y-ths.h2/2f,ths.w2,ths.h2);
      displayItemCount(p,ti,p.mouse.x-ths.w1/2f,p.mouse.y-ths.h1/2f);
    }
  }
  public void displaySlotWhenNotNull(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      // drawSlotBackground(p,ths.x1,ths.y1);
      drawSlotBackground(p,ths);
      drawSlotItem(p,ths,ti);
    }
  }
  public void drawSlotBackground(Screen0011 p,DisplaySlot ths) {
    // public void drawSlotBackground(Screen0011 p,float x,float y) {
    p.tint(255,127);
    p.image(pc.pw.metaItems.inventoryConfig.tiles[0],ths.x1,ths.y1);
    // p.image(pc.pw.metaItems.inventoryConfig.tiles[0],x,y);
    p.noTint();
  }
  public void displaySlot(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    drawSlotBackground(p,ths);
    // drawSlotBackground(p,ths.x1,ths.y1);
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  public void drawSlotItem(Screen0011 p,DisplaySlot ths,Item ti) {
    TextureRegion tr=ti.type.tiles[ti.displayType[0]];
    p.image(tr,ths.x1+ths.w3(),ths.y1+ths.h3(),ths.w2,ths.h2);
    displayItemCount(p,ti,ths.x1,ths.y1);
  }
  public void displaySlotItem(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  private void displayItemCount(Screen0011 p,Item ti,float x,float y) {
    p.textColor(255,191);
    p.text(Integer.toString(ti.count),x,y);
  }
  public void drawSelectRect(Screen0011 p) {
    p.tint(255,191);
    DisplaySlot ths=selectSlot>=hotSlots.length?backpackSlots[selectSlot-hotSlots.length]:hotSlots[selectSlot];
    TextureRegion tr=pc.pw.metaItems.inventoryConfig.tiles[1];
    // p.tint(255,127);
    p.image(tr,ths.x1,ths.y1);
  }
  // public void displayHotSlot() {}
  // public void displayInventoryCircle() {}
  //------------------------------------------------------------------------------------
  public static class InventorySlot{
    public Item item;
    public InventorySlot() {}
    public InventorySlot(Item item) {
      this.item=item;
    }
    @FunctionalInterface
    public interface GetInventorySlot{
      public InventorySlot get();
    }
  }
  public static class DisplaySlot{
    public InventorySlot data;
    public float cx,cy,x1,y1,x2,y2,w1,h1,w2,h2;
    public DisplaySlot(InventorySlot pos) {
      this.data=pos;
    }
    public void centerUpdate(LivingEntity pc,float x,float y) {
      cx=pc.cx()+x;
      cy=pc.cy()+y;
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
  public void accept(Item in) {
    for(InventorySlot e:data) if(e.item!=null&&e.item.type==in.type) {
      e.item.count+=in.count;
      in.count=0;
      return;
    }
    for(InventorySlot e:data) if(e.item==null) {
      e.item=in;
      return;
    }
  }
}