package pama1234.gdx.game.sandbox.platformer.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.Tools;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class Inventory{
  @Deprecated
  public static final int noDisplay=0;
  public static final int displayHoldSlot=1,displayFullInventory=2,displayHotSlot=3;
  public static final int leftButton=0,rightButton=1;
  public static int timeF=7200;
  public LivingEntity pe;
  @Tag(0)
  public ItemSlot[] data;
  @Tag(1)
  int hotSlotSize;
  public int displayState=displayHoldSlot;
  public DisplaySlot[][] displaySlots;
  public DisplaySlot[] hotSlots,backpackSlots;
  public DisplaySlot selectSlot;
  public float rSize;
  public PathVar r;
  @Tag(2)
  public int selectSlotPos;
  public int hotSlotDisplayCooling;
  public boolean[] hotSlotKeyData;
  public float circleDeg;
  public boolean gridUi=true;
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
    selectSlot=new DisplaySlot(data[data.length-1]);
    backpackSlots=new DisplaySlot[data.length-hotSlotSize-1];
    for(int i=0;i<backpackSlots.length;i++) backpackSlots[i]=new DisplaySlot(data[i+hotSlots.length]);
    displaySlots=new DisplaySlot[][] {hotSlots,backpackSlots};
    r=new PathVar(rSize=UtilMath.min(pe.type.attr.w,pe.type.attr.h));
  }
  public void drop(int type) {
    Item item=selectSlot.data.item;
    switch(type) {
      case leftButton: {
        if(item!=null) {
          selectSlot.data.item=null;
          pe.pw.dropItem(pe,item,pe.cx(),pe.cy());
        }
      }
        break;
      case rightButton: {
        if(item!=null) {
          pe.pw.dropItem(pe,item.type.createItem(1),pe.cx(),pe.cy());
          item.count-=1;
          if(item.count==0) selectSlot.data.item=null;
        }
      }
        break;
    }
  }
  public void switchHold(DisplaySlot in,int type) {
    Item slotItem=in.data.item;
    Item holdItem=selectSlot.data.item;
    switch(type) {
      case leftButton: {
        if(holdItem!=null&&slotItem!=null&&slotItem.type==holdItem.type) {
          slotItem.count+=holdItem.count;
          holdItem.count=0;
          selectSlot.data.item=null;
        }else {
          selectSlot.data.item=slotItem;
          in.data.item=holdItem;
        }
      }
        break;
      case rightButton: {
        if(holdItem!=null) {
          if(slotItem==null) {
            in.data.item=holdItem.type.createItem(1);
            holdItem.count-=1;
          }else if(slotItem.type==holdItem.type) {
            slotItem.count-=1;
            holdItem.count+=1;
            if(slotItem.count==0) in.data.item=null;
          }
          if(holdItem.count==0) selectSlot.data.item=null;
        }else {
          if(slotItem!=null) {
            selectSlot.data.item=slotItem.type.createItem(1);
            slotItem.count-=1;
            if(slotItem.count==0) in.data.item=null;
          }
        }
      }
        break;
    }
  }
  public DisplaySlot selectSlot() {
    return hotSlots[selectSlotPos];
  }
  public void selectSlot(int in) {
    selectSlotPos=in;
  }
  public void testSelectSlot() {
    selectSlotPos=Tools.moveInRange(selectSlotPos,0,hotSlots.length);
  }
  public void selectSlotWithKeyEvent() {
    selectSlotPos=getSelectPos(hotSlotKeyData);
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
        selectSlot.centerUpdate(pe,0,-12);
        selectSlot().centerUpdate(pe,0,12);
      }
        break;
      case displayHotSlot: {
        if(hotSlotDisplayCooling>0) {
          hotSlotDisplayCooling-=1;
          if(hotSlotDisplayCooling==0) displayState(displayHoldSlot);
        }
        if(gridUi) {
          slotGridUpdate(pe.p,hotSlots,-90,60,9);
        }else {
          slotCircleUpdate(pe.p,hotSlots,1);
        }
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
    int ts=18;
    if(gridUi) {
      // 以下数字并不应当是魔数，得改，使得在“游戏UI调节界面”可以修改
      int tx=-80;
      int ty=60;
      slotGridUpdate(p,hotSlots,tx,ty,9);
      slotGridUpdate(p,backpackSlots,tx,ty+20,9);
      selectSlot.centerUpdate(pe,0,12);
    }else {
      slotCircleUpdate(p,hotSlots,1);
      if(backpackSlots.length>ts) {
        for(int i=0;i<ts;i++) backpackSlots[i].circleUpdate(pe,
          (float)i/ts+(float)(p.frameCount%timeF)/timeF+circleDeg,
          r.pos*2);
        for(int i=ts;i<backpackSlots.length;i++) backpackSlots[i].circleUpdate(pe,
          (float)(i-ts)/(backpackSlots.length-ts)+(float)(p.frameCount%timeF)/timeF+circleDeg,
          r.pos*3);
      }else {
        slotCircleUpdate(p,backpackSlots,2);
      }
      selectSlot.centerUpdate(pe,0,12);
    }
  }
  public void slotCircleUpdate(Screen0011 p,DisplaySlot[] slots,float rPosMult) {
    for(int i=0;i<slots.length;i++) slots[i].circleUpdate(pe,
      (float)i/slots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
      r.pos*rPosMult);
  }
  public void slotGridUpdate(Screen0011 p,DisplaySlot[] slots,float x,float y,int lineWidth) {
    //TODO 20并不是魔数，得改
    for(int i=0;i<slots.length;i++) slots[i].gridUpdate(pe,x+(i%lineWidth)*20,y+(i/lineWidth)*20);
  }
  public void display() {
    Screen0011 p=pe.p;
    p.textScale(0.5f);
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldSlot: {
        displaySlotItem(pe.p,selectSlot);
        displaySlotItem(pe.p,selectSlot());
      }
        break;
      case displayHotSlot: {
        drawSelectRect(p);
        displayAllHotSlot();
      }
        break;
      case displayFullInventory: {
        drawSelectRect(p);
        displayAllHotSlot();
        displayBackpackSlot();
        displaySlotWhenNotNull(p,selectSlot);
        drawMouseHold(p,selectSlot);
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
  public void displayAllHotSlot() {
    Screen0011 p=pe.p;
    for(int i=0;i<hotSlots.length;i++) displaySlot(p,hotSlots[i]);
    p.noTint();
  }
  public void drawMouseHold(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
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
    TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
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
    DisplaySlot ths=selectSlotPos>=hotSlots.length?backpackSlots[selectSlotPos-hotSlots.length]:selectSlot();
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