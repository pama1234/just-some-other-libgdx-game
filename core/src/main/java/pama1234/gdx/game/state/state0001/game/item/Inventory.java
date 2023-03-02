package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class Inventory{
  @Deprecated
  public static final int noDisplay=0;
  public static final int displayHoldSlot=1,displayFullInventory=2,displayHotSlot=3;
  public static final int allItem=0,oneItem=1;
  public static int timeF=7200;
  public LivingEntity pe;
  @Tag(0)
  public ItemSlot[] data;
  @Tag(1)
  int hotSlotSize;
  public int displayState=displayHoldSlot;
  public DisplaySlot[][] displaySlots;
  public DisplaySlot[] hotSlots,backpackSlots;
  public DisplaySlot holdSlot;
  public float rSize;
  public PathVar r;
  @Tag(2)
  public int selectSlot;
  public int hotSlotDisplayCooling;
  public boolean[] hotSlotKeyData;
  public float circleDeg;
  public Inventory() {}
  public Inventory(LivingEntity pe,int size,int hotSlotSize) {
    this.pe=pe;
    this.hotSlotSize=hotSlotSize;
    data=new ItemSlot[size];
    for(int i=0;i<data.length;i++) data[i]=new ItemSlot();
    innerInit();
  }
  public void innerInit() {
    hotSlots=new DisplaySlot[hotSlotSize];
    hotSlotKeyData=new boolean[hotSlotSize];
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new DisplaySlot(data[i]);
    holdSlot=new DisplaySlot(data[data.length-1]);
    backpackSlots=new DisplaySlot[data.length-hotSlotSize-1];
    for(int i=0;i<backpackSlots.length;i++) backpackSlots[i]=new DisplaySlot(data[i+hotSlots.length]);
    displaySlots=new DisplaySlot[][] {hotSlots,backpackSlots};
    r=new PathVar(rSize=UtilMath.min(pe.type.w,pe.type.h));
  }
  public void drop(int type) {
    // Item itemIn=in.data.item;
    Item item=holdSlot.data.item;
    switch(type) {
      case allItem: {
        if(item!=null) {
          holdSlot.data.item=null;
          pe.pw.dropItem(item,pe.cx(),pe.cy());
        }
      }
        break;
      case oneItem: {
        if(item!=null) {
          pe.pw.dropItem(item.type.createItem(1),pe.cx(),pe.cy());
          item.count-=1;
          if(item.count==0) holdSlot.data.item=null;
        }
      }
        break;
    }
  }
  public void switchHold(DisplaySlot in,int type) {
    Item itemIn=in.data.item;
    Item item=holdSlot.data.item;
    switch(type) {
      case allItem: {
        if(item!=null&&itemIn!=null&&itemIn.type==item.type) {
          item.count+=itemIn.count;
          itemIn.count=0;
          in.data.item=null;
        }else {
          holdSlot.data.item=itemIn;
          in.data.item=item;
        }
      }
        break;
      case oneItem: {
        if(item!=null) {
          if(itemIn==null) {
            in.data.item=item.type.createItem(1);
            item.count-=1;
          }else if(itemIn.type==item.type) {
            in.data.item.count+=1;
            item.count-=1;
          }
          if(item.count==0) holdSlot.data.item=null;
        }
      }
        break;
    }
  }
  public DisplaySlot selectSlot() {
    return hotSlots[selectSlot];
  }
  public void selectSlot(int in) {
    selectSlot=in;
  }
  public void testSelectSlot() {
    selectSlot=Tools.moveInRange(selectSlot,0,hotSlots.length);
  }
  public void selectSlotWithKeyEvent() {
    selectSlot=getSelectPos(hotSlotKeyData);
    testSelectSlot();
    if(displayState!=displayFullInventory) displayState(displayHotSlot);
  }
  public void displayStateChange() {
    if(displayState==displayFullInventory) safeDisplayState(displayHoldSlot);
    else safeDisplayState(displayFullInventory);
  }
  public void displayState(int in) {
    if(in==displayHotSlot) {
      if(displayState==displayFullInventory) return;
      hotSlotDisplayCooling=120;
    }
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
  public static int getSelectPos(boolean[] in) {
    int out=0;
    for(int i=1;i<in.length;i++) if(in[i]) out+=1<<(i-1);
    return out;
  }
  public void update() {
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldSlot: {
        holdSlot.centerUpdate(pe,0,-12);
        selectSlot().centerUpdate(pe,0,12);
      }
        break;
      case displayHotSlot: {
        if(hotSlotDisplayCooling>0) {
          hotSlotDisplayCooling-=1;
          if(hotSlotDisplayCooling==0) displayState(displayHoldSlot);
        }
        updateHotSlot(pe.p);
      }
        break;
      case displayFullInventory: {
        updateFullInventory();
      }
        break;
    }
  }
  public void updateFullInventory() {
    r.update();
    Screen0011 p=pe.p;
    updateHotSlot(p);
    int ts=18;
    if(backpackSlots.length>ts) {
      for(int i=0;i<ts;i++) backpackSlots[i].circleUpdate(pe,
        (float)i/ts+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*2);
      for(int i=ts;i<backpackSlots.length;i++) backpackSlots[i].circleUpdate(pe,
        (float)(i-ts)/(backpackSlots.length-ts)+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*3);
    }else {
      for(int i=0;i<backpackSlots.length;i++) backpackSlots[i].circleUpdate(pe,
        (float)i/backpackSlots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
        r.pos*2);
    }
    holdSlot.centerUpdate(pe,0,12);
  }
  public void updateHotSlot(Screen0011 p) {
    for(int i=0;i<hotSlots.length;i++) hotSlots[i].circleUpdate(pe,
      (float)i/hotSlots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
      r.pos);
  }
  public void display() {
    Screen0011 p=pe.p;
    p.textScale(0.5f);
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldSlot: {
        displaySlotItem(pe.p,holdSlot);
        displaySlotItem(pe.p,selectSlot());
      }
        break;
      case displayHotSlot: {
        drawSelectRect(p);
        displayHotSlotCircle();
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
  }
  public void displayBackpackSlot() {
    Screen0011 p=pe.p;
    for(int i=0;i<backpackSlots.length;i++) displaySlot(p,backpackSlots[i]);
    p.noTint();
  }
  public void displayHotSlotCircle() {
    Screen0011 p=pe.p;
    for(int i=0;i<hotSlots.length;i++) displaySlot(p,hotSlots[i]);
    p.noTint();
  }
  public void drawMouseHold(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      TextureRegion tr=ti.type.tiles[ti.displayType()];
      p.image(tr,p.mouse.x-ths.w2/2f,p.mouse.y-ths.h2/2f,ths.w2,ths.h2);
      displayItemCount(p,ti,p.mouse.x-ths.w1/2f,p.mouse.y-ths.h1/2f);
    }
  }
  public void displaySlotWhenNotNull(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      drawSlotBackground(p,ths);
      drawSlotItem(p,ths,ti);
    }
  }
  public static void displaySlot(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    drawSlotBackground(p,ths);
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  public static void drawSlotBackground(Screen0011 p,DisplaySlot ths) {
    p.tint(255,127);
    p.image(ImageAsset.items[0][0],ths.x1,ths.y1);
    p.noTint();
  }
  public static void drawSlotItem(Screen0011 p,DisplaySlot ths,Item ti) {
    TextureRegion tr=ti.type.tiles[ti.displayType()];
    p.image(tr,ths.x1+ths.w3(),ths.y1+ths.h3(),ths.w2,ths.h2);
    displayItemCount(p,ti,ths.x1,ths.y1);
  }
  public static void displaySlotItem(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  public static void displayItemCount(Screen0011 p,Item ti,float x,float y) {
    p.textColor(255,191);
    p.text(Integer.toString(ti.count),x,y);
  }
  public void drawSelectRect(Screen0011 p) {
    DisplaySlot ths=selectSlot>=hotSlots.length?backpackSlots[selectSlot-hotSlots.length]:selectSlot();
    TextureRegion tr=ImageAsset.items[0][1];
    p.tint(255,191);
    p.image(tr,ths.x1,ths.y1);
  }
  public void accept(Item in) {
    for(ItemSlot e:data) if(e.item!=null&&e.item.type==in.type) {
      e.item.count+=in.count;
      in.count=0;
      return;
    }
    for(ItemSlot e:data) if(e.item==null) {
      e.item=in;
      return;
    }
  }
}