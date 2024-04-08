package pama1234.gdx.game.sandbox.platformer.item;

import pama1234.Tools;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.player.ctrl.PlayerControllerFull;
import pama1234.math.physics.PathVar;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

/**
 * 
 * <pre>
 * 玩家的物品存储器的核心部分
 * 这个类用于放置绘制以外的内容
 * </pre>
 * 
 * @see PlayerControllerFull
 */
public class InventoryCore{

  @Deprecated
  public static final int noDisplay=0;
  public static final int displayHoldItem=1,displayFullInventory=2,displayHotSlot=3;
  public static final int leftButton=0,rightButton=1;
  public static int timeF=7200;

  public Screen0011 p;

  /** parnet entity */
  public LivingEntity pe;
  @Tag(0)
  public ItemSlot[] data;
  @Tag(1)
  int hotSlotSize;
  public int displayState=displayHoldItem;
  public DisplaySlot[][] displaySlots;
  public DisplaySlot[] hotSlots,backpackSlots;
  /** 手持物品，当鼠标在物品框是按下时转移手持和（或）物品框中的物品 */
  public DisplaySlot handSlot;

  /** 绘制圆形或网格形式的物品栏 */
  public boolean gridUi=true;

  // 用来绘制圆形物品栏
  public float rSize;
  public PathVar r;
  public float circleDeg;

  @Tag(2)
  public int selectSlotPos;
  public int hotSlotDisplayCooling;
  public boolean[] hotSlotKeyData;

  public int slotDx=-90,slotDy=60;

  public void update() {
    if(p.isAndroid) androidUpdate();
    else desktopUpdate();
  }
  /** 用于刷新手机端的物品存储器UI */
  public void androidUpdate() {
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldItem: {
        handSlot.centerUpdate(pe,0,-12);
        selectSlot().centerUpdate(pe,0,12);
      }
        break;
      case displayHotSlot: {
        if(hotSlotDisplayCooling>0) {
          hotSlotDisplayCooling-=1;
          if(hotSlotDisplayCooling==0) displayState(displayHoldItem);
        }
        float tx=p.bu/2f;
        float ty=p.bu*1.5f;
        slotGridUpdateAndroid(pe.p,hotSlots,tx,ty,9);
        // if(gridUi) slotGridUpdateAndroid(pe.p,hotSlots,-90,60,9);
        // else slotCircleUpdate(pe.p,hotSlots,1);

      }
        break;
      case displayFullInventory: {
        updateFullInventoryAndroid();
      }
        break;
    }
  }
  /** 用于刷新电脑端的物品存储器UI */
  public void desktopUpdate() {
    switch(displayState) {
      case noDisplay: {}
        break;
      case displayHoldItem: {
        handSlot.centerUpdate(pe,0,-12);
        selectSlot().centerUpdate(pe,0,12);
      }
        break;
      case displayHotSlot: {
        if(hotSlotDisplayCooling>0) {
          hotSlotDisplayCooling-=1;
          if(hotSlotDisplayCooling==0) displayState(displayHoldItem);
        }
        if(gridUi) slotGridUpdate(pe.p,hotSlots,slotDx,slotDy,9);
        else slotCircleUpdate(pe.p,hotSlots,1);

      }
        break;
      case displayFullInventory: {
        updateFullInventoryDesktop();
      }
        break;
    }
  }
  public void updateFullInventoryAndroid() {
    // 以下数字并不应当是魔数，得改，使得在“游戏UI调节界面”可以修改
    float tx=p.bu/2f;
    float ty=p.bu*1.5f;
    slotGridUpdateAndroid(p,hotSlots,tx,ty,9);
    slotGridUpdateAndroid(p,backpackSlots,tx,ty+p.bu*0.6f,9);
    //    handSlot.centerUpdate(pe,0,12);
    handSlot.setDisplaySizeScreen(p.bu);
  }
  public void updateFullInventoryDesktop() {
    updateFullInventory();
  }
  public void updateFullInventory() {
    Screen0011 p=pe.p;
    int ts=18;
    if(gridUi) {
      // 以下数字应当使得在“游戏UI调节界面”可以修改
      int tx=slotDx+10;
      int ty=slotDy;
      slotGridUpdate(p,hotSlots,tx,ty,9);
      slotGridUpdate(p,backpackSlots,tx,ty+20,9);
    }else {
      r.update();
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
    }
    handSlot.centerUpdate(pe,0,12);
  }
  public void slotCircleUpdate(Screen0011 p,DisplaySlot[] slots,float rPosMult) {
    for(int i=0;i<slots.length;i++) slots[i].circleUpdate(pe,
      (float)i/slots.length+(float)(p.frameCount%timeF)/timeF+circleDeg,
      r.pos*rPosMult);
  }
  public void slotGridUpdate(Screen0011 p,DisplaySlot[] slots,float x,float y,int lineWidth) {
    //TODO 20并不是魔数，得改
    int ts=20;
    for(int i=0;i<slots.length;i++) slots[i].gridUpdate(pe,x+(i%lineWidth)*ts,y+(i/lineWidth)*ts);
  }
  public void slotGridUpdateAndroid(Screen0011 p,DisplaySlot[] slots,float x,float y,int lineWidth) {
    //TODO 20并不是魔数，得改
    for(int i=0;i<slots.length;i++) slots[i].gridUpdateScreen(
      x+(i%lineWidth)*p.bu*0.6f,y+(i/lineWidth)*p.bu*0.6f,
      p.bu);
  }

  //---------------------------------------------------------------------------

  public DisplaySlot selectSlot() {
    return hotSlots[selectSlotPos];
  }
  public void selectSlot(int in) {
    selectSlotPos=in;
  }

  //---------------------------------------------------------------------------

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

  //---------------------------------------------------------------------------

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
  public void drop(int type) {
    Item item=handSlot.data.item;
    switch(type) {
      case leftButton: {
        if(item!=null) {
          handSlot.data.item=null;
          pe.pw.dropItem(pe,item,pe.cx(),pe.cy());
        }
      }
        break;
      case rightButton: {
        if(item!=null) {
          pe.pw.dropItem(pe,item.type.createItem(1),pe.cx(),pe.cy());
          item.count-=1;
          if(item.count==0) handSlot.data.item=null;
        }
      }
        break;
    }
  }

  /**
   * (代码由AI生成)
   * 
   * <pre>
   * 将物品在物品格子和玩家的手上之间进行转移
   * 1. 如果玩家空手，左键拿取全部物品，右键拿取一半物品 
   * 2. 如果玩家手上有东西，左键放回全部物品，右键放回一个物品
   * 3. 如果玩家手上和格子中都有物品，如果物品可堆叠，左键把手中物品放回格子中，右键放回一个物品  
   * 4. 如果物品不可堆叠，左右键效果一样，交换手中和格子中的物品
   * </pre>
   *
   * @param in   物品格子
   * @param type 转移类型
   */
  public void switchHold(DisplaySlot in,int type) {
    // 格子上的物品
    Item slotItem=in.data.item;

    // 手上的物品
    Item holdItem=handSlot.data.item;

    switch(type) {
      case leftButton: {
        // 1. 如果玩家空手,左键将拿取全部物品
        if(holdItem==null) {
          handSlot.data.item=slotItem;
          slotItem=in.data.item=null;
        }
        // 2. 如果玩家手上有东西,左键放回全部物品
        else {
          if(slotItem==null) {
            slotItem=in.data.item=holdItem.type.createItem(holdItem.count);
            holdItem.count=0;
            handSlot.data.item=null;
          }else {
            if(slotItem.type==holdItem.type) {
              slotItem.count+=holdItem.count;
              holdItem.count=0;
              handSlot.data.item=null;
            }else {
              //              Item temp=slotItem;
              in.data.item=holdItem;
              handSlot.data.item=slotItem;
            }
          }
        }
      }
        break;

      case rightButton: {
        // 1. 如果玩家空手,右键拿取一半物品
        if(holdItem==null) {
          if(slotItem!=null) {
            holdItem=handSlot.data.item=slotItem.type.createItem(slotItem.count/2);
            slotItem.count-=holdItem.count;
          }
        }
        // 2. 如果玩家手上有东西,右键放回一个物品  
        else {
          if(slotItem==null) {
            slotItem=in.data.item=holdItem.type.createItem(1);
          }else {
            if(slotItem.type==holdItem.type) {
              slotItem.count+=1;
            }else {
              //              Item temp=slotItem;
              in.data.item=holdItem;
              handSlot.data.item=slotItem;
            }
          }
          //          slotItem.count+=1;
          holdItem.count-=1;
          if(holdItem.count==0) {
            handSlot.data.item=null;
          }
        }
      }
        break;
    }

    // 如果格子物品数量为0,设置为null
    if(slotItem!=null&&slotItem.count==0) {
      in.data.item=null;
    }
    // 如果手持物品数量为0,设置为null
    if(holdItem!=null&&holdItem.count==0) {
      handSlot.data.item=null;
    }
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
    if(displayState==displayFullInventory) safeDisplayState(displayHoldItem);
    else safeDisplayState(displayFullInventory);

  }
  public static int getSelectPos(boolean[] in) {
    int out=0;
    for(int i=1;i<in.length;i++) if(in[i]) out+=1<<(i-1);
    return out;
  }
}
