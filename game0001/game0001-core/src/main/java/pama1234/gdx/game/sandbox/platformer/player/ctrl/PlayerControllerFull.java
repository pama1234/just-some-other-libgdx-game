package pama1234.gdx.game.sandbox.platformer.player.ctrl;

import com.badlogic.gdx.Input.Buttons;

import pama1234.Tools;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.item.DisplaySlot;
import pama1234.gdx.game.sandbox.platformer.item.Inventory;
import pama1234.gdx.game.sandbox.platformer.item.InventoryCore;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.player.BlockPointer;
import pama1234.gdx.game.sandbox.platformer.player.GameMode;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.player.ctrl.ControllerUtil.ControllerBlockPointer;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.region.block.Block.BlockUi;
import pama1234.gdx.game.sandbox.platformer.world.WorldSettings;
import pama1234.gdx.game.ui.GameController;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.AbstractControlBindUtil;
import pama1234.gdx.util.AbstractControlBindUtil.GetKeyPressedBoolean;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectF;

/**
 * <pre>
 * 术语：
 * 1. 物品栏 按下E键后第一行物品格子
 * 2. 背包 按下E键后第一行以下的格子
 * 3. 物品格子 灰色正方形，缺四个角，中间可以存放物品
 * </pre>
 * 
 * @see Inventory 背包和物品栏
 * @see AbstractControlBindUtil 键位系统
 * 
 */
public class PlayerControllerFull extends PlayerControllerCore{

  public MainPlayer player;
  public RectF[] cullRects;
  // public float camScale=2;
  public ControllerBlockPointer selectBlock;
  public float zoomSpeed=0.0625f;
  public boolean zoomIn,zoomOut;

  /** 范围摇杆 */
  public GameController ctrlVertex;

  public PlayerControllerFull(Screen0011 p,MainPlayer player) {
    super(p,player,true);
    this.player=player;
    cullRects=ControlUiUtil.createRectF(p);
    coreSelectBlock=selectBlock=new ControllerBlockPointer(player.pw,()->player.inventory.selectSlot().data);

    if(p.isAndroid) {
      if(!p.settings.ctrlButton) ctrlVertex=new GameController(p) {
        @Override
        public boolean inActiveRect(float x,float y) {
          return Tools.inRect(x,y,0,p.u*2,p.width/3f,p.height);
        }
      };
    }
  }
  /** 绘制生物和方块选择框 */
  @Override
  public void display() {
    if(selectEntity.entity!=null) ControllerDisplayUtil.drawSelectEntity(p,selectEntity.entity);
    if(selectBlock.active) {
      if(p.isAndroid) {
        WorldSettings ts=player.pw.settings;
        ControllerDisplayUtil.drawSelectBlockTouchScreen(p,selectBlock,ts.blockWidth,ts.blockHeight,p.cam2d.scale.pos);
      }else {
        WorldSettings ts=player.pw.settings;
        ControllerDisplayUtil.drawSelectBlock(p,selectBlock,ts.blockWidth,ts.blockHeight);
      }
    }
  }
  @Override
  public void preUpdate() {
    for(TouchInfo e:p.touches) if(e.active&&e.state==0) touchUpdate(e);
    updateCtrlInfo();
    super.preUpdate();
  }
  @Override
  public void postUpdate() {
    super.postUpdate();
    p.cam.point.des.set(player.cx(),player.cy());
    if(zoomIn!=zoomOut) camScale(zoomIn?zoomSpeed:-zoomSpeed);
  }
  public void updateCtrlInfo() {
    updateKeyInfo();
    if(!player.pw.p.isAndroid) updateMouseInfo();
    selectEntity.updateTask();//TODO
  }
  public void updateMouseInfo() {
    selectBlock.active=false;
    if(testPosInButtons(p.mouse.ox,p.mouse.oy)) return;
    if(testPosInInventorySlot(p.mouse.x,p.mouse.y)) return;
    int tx=player.xToBlockCordInt(p.mouse.x),
      ty=player.xToBlockCordInt(p.mouse.y);
    if(inPlayerOuterBox(tx,ty)) return;
    float tr=selectBlock.dist(tx,ty);
    float maxDist=selectBlock.maxDist;
    if(tr>maxDist) {
      float tx_2=(tx-selectBlock.ox-0.5f)*(maxDist-0.5f)/tr;
      float ty_2=(ty-selectBlock.oy-0.5f)*(maxDist-0.5f)/tr;
      tx=UtilMath.round(selectBlock.ox+tx_2);
      ty=UtilMath.round(selectBlock.oy+ty_2);
    }
    selectBlock.active=true;
    Block block=player.getBlock(tx,ty);
    selectBlock.update(block,tx,ty);
  }
  /** 根据键位设置刷新玩家的按键，具体来说是上下左右移动状态 */
  public void updateKeyInfo() {
    GetKeyPressedBoolean f=p::isKeyPressed;
    left=p.controlBind.isKeyPressed(ControlBindUtil.moveLeft,f);
    right=p.controlBind.isKeyPressed(ControlBindUtil.moveRight,f);
    jump=p.controlBind.isKeyPressed(ControlBindUtil.jumpUp,f);
    jumpDown=p.controlBind.isKeyPressed(ControlBindUtil.jumpDown,f);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(p.controlBind.isKey(ControlBindUtil.shift,keyCode)) shift(true);
    else if(p.controlBind.isKey(ControlBindUtil.openInventory,keyCode)) inventoryDisplayStateChange();
    else if(p.controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=true;
    else if(p.controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=true;
    else if(testIsHotSlotKey(keyCode,true)) player.inventory.selectSlotWithKeyEvent();
  }

  public void inventoryDisplayStateChange() {
    player.inventory.displayStateChange();
    if(ctrlVertex!=null) ctrlVertex.active=player.inventory.displayState!=InventoryCore.displayFullInventory;
    //    System.out.println(ctrlVertex.active);
  }

  @Override
  public void keyReleased(char key,int keyCode) {
    if(p.controlBind.isKey(ControlBindUtil.shift,keyCode)) shift(false);
    else if(testIsHotSlotKey(keyCode,false)) player.inventory.selectSlotWithKeyEvent();
    else if(p.controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=false;
    else if(p.controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=false;
  }
  /**
   * 检查按键是否是用来控制物品栏了，默认按键是123456这些数字键。
   * </p>
   * 如果是，就根据二进制判断将要选定的物品栏，例如同时按下134就指向第七个格子，124就指向第六个
   */
  public boolean testIsHotSlotKey(int keyCode,boolean bIn) {
    boolean[] tba=player.inventory.hotSlotKeyData;
    int tl=UtilMath.min(tba.length,10);
    boolean flag=false;
    if(!bIn&&!tba[0]) return flag;
    boolean t_tba_0=tba[0];
    for(int i=0;i<tl;i++) if(p.controlBind.isKey(ControlBindUtil.hotSlotStart+i,keyCode)) {
      tba[i]=bIn;
      flag=true;
    }
    if(!t_tba_0&&tba[0]) for(int i=1;i<tba.length;i++) tba[i]=false;
    return flag;
  }
  public void camScale(float in) {
    p.cam2d.scaleAdd(in);
    player.camScale=p.cam2d.scale.des;
  }
  /** 当鼠标按下或触碰发生时，就会调用这个方法 */
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    if(player.p.touchCount>1) {
      selectBlock.active=false;
      return;
    }

    int tx=player.xToBlockCordInt(info.x),
      ty=player.xToBlockCordInt(info.y);
    if(p.isAndroid
      ?updateAndTestInventorySlot(info.ox,info.oy,info.button)
      :updateAndTestInventorySlot(info.x,info.y,info.button)) return;
    if(updateAndTestWorkStationUi(info)) return;
    if(p.isAndroid&&inDisplayInventoryButton(info.x,info.y)) {
      inventoryDisplayStateChange();
      return;
    }
    if(updateAndTestSelectEntity(tx,ty,info.button)) return;
    float tr=selectBlock.dist(tx,ty);
    float maxDist=selectBlock.maxDist;
    if(tr>maxDist) {
      float tx_2=(tx-selectBlock.ox-0.5f)*(maxDist-0.5f)/tr;
      float ty_2=(ty-selectBlock.oy-0.5f)*(maxDist-0.5f)/tr;
      tx=UtilMath.round(selectBlock.ox+tx_2);
      ty=UtilMath.round(selectBlock.oy+ty_2);
    }
    Block block=player.getBlock(tx,ty);
    selectBlock.active=true;
    selectBlock.update(block,tx,ty);
    selectBlock.info(info);
    selectBlock.startTaskButtonInfo(getBlockTaskButton(block,selectBlock.slot.get().item,info.button));
  }
  /**
   * <pre>
   * 兼容电脑端和手机端的物品使用操作，手机端的判断逻辑如下：
   * 1. 如果手中持有工具，则优先左键使用工具
   * 2. 如果手中没有工具，并且目标方块是空的，优先右键放置物品
   * 3. 如果方块是工作站，那么优先右键选中工作站
   * </pre>
   * 
   * @param block 方块
   * @param item  手持物品
   * @param in    传入的按键
   * @return 传出的按键
   */
  @Deprecated
  public int getBlockTaskButton(Block block,Item item,int in) {
    if(p.isAndroid) {
      // return player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT;
      boolean toolItem=item!=null&&(item.type.attr.toolType!=MetaItem.notTool);
      boolean blockEmpty=Block.isEmpty(block);
      boolean workStation=block.type.attr.workStation;
      return toolItem?Buttons.LEFT:(blockEmpty?Buttons.RIGHT:(workStation?Buttons.RIGHT:Buttons.LEFT));
    }else {
      return in;
    }
  }
  /**
   * 鼠标是否在玩家角色的中心圆圈范围内（这里用来判断是否会显示或隐藏玩家的物品栏）
   * 
   * @param tx 鼠标x
   * @param ty 鼠标y
   * @return 是否在范围内
   */
  public boolean inDisplayInventoryButton(float tx,float ty) {
    return UtilMath.dist(tx,ty,player.cx(),player.cy())<player.circleSize()/2f;
  }
  /**
   * 对所有触碰信息进行刷新事件
   * 
   * @param info
   */
  public void touchUpdate(TouchInfo info) {
    if(!player.pw.p.isAndroid) return;
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    if(testPosInInventorySlot(info.x,info.y)) return;

    int tx=player.xToBlockCordInt(info.x),
      ty=player.xToBlockCordInt(info.y);
    if(inPlayerOuterBox(tx,ty)) return;
    float tr=selectBlock.dist(tx,ty);
    float maxDist=selectBlock.maxDist;
    if(tr>maxDist) {
      float tx_2=(tx-selectBlock.ox-0.5f)*(maxDist-0.5f)/tr;
      float ty_2=(ty-selectBlock.oy-0.5f)*(maxDist-0.5f)/tr;
      tx=UtilMath.round(selectBlock.ox+tx_2);
      ty=UtilMath.round(selectBlock.oy+ty_2);
    }
    Block block=player.getBlock(tx,ty);
    selectBlock.update(block,tx,ty);
    if(player.gameMode!=GameMode.creative) return;
    if(testPosInOtherEntity(tx,ty)) return;
    creativeModeUpdateSelectBlock(info,tx,ty,block);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    testWorkStationUiTouchEnded(info);
    if(p.isAndroid&&selectBlock.task!=BlockPointer.use) selectBlock.active=false;
    if(player.p.touchCount==1&&selectBlock.task==BlockPointer.use) {
      selectBlock.active=true;
      return;
    }
    selectBlock.testStopTask(info);
  }
  /**
   * 测试输入的鼠标信息是否在背包的物品格子上，返回布尔值
   * 
   * @param x
   * @param y
   * @param button
   * @return
   */
  public boolean updateAndTestInventorySlot(float x,float y,int button) {
    if(player.inventory.displayState==Inventory.displayFullInventory) {
      DisplaySlot[] slots=player.inventory.hotSlots;
      for(int i=0;i<slots.length;i++) if(testAndSelectSlot(x,y,button,slots,i)) return true;
      slots=player.inventory.backpackSlots;
      for(DisplaySlot e:slots) if(testSlot(x,y,button,e)) return true;
      doDropItem(button);
    }
    return false;
  }
  /**
   * 测试输入的鼠标信息是否选中了生物实体，返回布尔值
   * 
   * @param tx
   * @param ty
   * @param button
   * @return
   */
  public boolean updateAndTestSelectEntity(int tx,int ty,int button) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) {
        if(e instanceof LivingEntity live) {
          if(live.inOuterBox(tx,ty)) {
            if(selectEntity.entity!=live) selectEntity.entity=live;
            else selectEntity.startTaskButtonInfo(button);
            return true;
          }
        }
      }
    }
    selectEntity.entity=null;
    return false;
  }
  /**
   * 检查输入的屏幕触碰信息是否和工作站方块的UI有关
   * 
   * @param info
   * @return
   */
  public boolean updateAndTestWorkStationUi(TouchInfo info) {
    if(selectBlock.task==BlockPointer.use) {
      BlockUi ui=selectBlock.block.ui;
      DisplaySlot[] slots=ui.displaySlot;
      float x=info.x;
      float y=info.y;
      int button=info.button;
      for(DisplaySlot e:slots) if(testSlot(x,y,button,e)) return true;
      TextButtonCam<?>[] camButton=ui.camButton;
      for(TextButtonCam<?> e:camButton) {
        TouchInfo temp=e.touch;
        if(info==temp||!info.active||info.state!=0) continue;
        e.touch=info;
        if(e.inButton(e.nx.get(),e.ny.get())) {
          e.clickStart();
          return true;
        }
        e.touch=temp;
      }
    }
    return false;
  }
  /**
   * 当鼠标松开时，调用此方法，删除工作站UI的按钮中的全部残留的touch引用
   * 
   * @param info
   */
  public void testWorkStationUiTouchEnded(TouchInfo info) {
    if(selectBlock.task==BlockPointer.use) {
      BlockUi ui=selectBlock.block.ui;
      // if(ui==null) return;
      TextButtonCam<?>[] camButton=ui.camButton;
      for(TextButtonCam<?> e:camButton) if(info==e.touch) e.clickEnd();
    }
  }
  public void doDropItem(int button) {
    switch(getTouchInfoButton(button)) {
      case Buttons.LEFT: {
        player.inventory.drop(Inventory.leftButton);
      }
        break;
      case Buttons.RIGHT: {
        player.inventory.drop(Inventory.rightButton);
      }
        break;
    }
  }
  /**
   * 测试鼠标是否在单个物品格子内，并根据按钮类型做出对应的操作，一般是从物品格子将物品转移到手上
   * 
   * @param x
   * @param y
   * @param button
   * @param e
   * @return
   */
  public boolean testSlot(float x,float y,int button,DisplaySlot e) {
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          player.inventory.switchHold(e,Inventory.leftButton);
        }
          break;
        case Buttons.RIGHT: {
          player.inventory.switchHold(e,Inventory.rightButton);
        }
          break;
      }
      return true;
    }
    return false;
  }
  /**
   * 测试鼠标是否在物品栏的单个物品格子上，如果是，则选中此物品格子，被选中的物品格子里的东西，可以被玩家放置到世界或进行使用
   * 
   * @param x
   * @param y
   * @param button
   * @param slots
   * @param i
   * @return
   */
  public boolean testAndSelectSlot(float x,float y,int button,DisplaySlot[] slots,int i) {
    DisplaySlot e=slots[i];
    //    p.println(x,y,e.x1,e.y1,e.w1,e.h1);
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          player.inventory.switchHold(e,Inventory.leftButton);
        }
          break;
        case Buttons.RIGHT: {
          selectHotSlot(i);
        }
          break;
      }
      return true;
    }
    return false;
  }
  /**
   * 未维护
   * 
   * @param info
   * @param tx
   * @param ty
   * @param block
   */
  @Deprecated
  public void creativeModeUpdateSelectBlock(TouchInfo info,int tx,int ty,Block block) {
    if(block!=null) switch(getTouchInfoButton(info.button)) {
      case Buttons.LEFT: {
        if(block.type!=player.pw.type.metaBlocks.air) player.pw.r.destroyBlock(player,block,tx,ty);
      }
        break;
      case Buttons.RIGHT: {
        ItemSlot td=player.inventory.selectSlot().data;
        Item ti=td.item;
        if(ti!=null) {
          MetaBlock<?,?> tb=ti.type.rttr.blockType;
          if(tb!=null&&block.type!=tb) {
            player.pw.r.placeBlock(player,block,tb,tx,ty);
          }
        }
      }
        break;
    }
  }
  /**
   * 一种比较生硬的兼容电脑端和手机端的方式，通过在手机端重载触碰的按钮（Button，鼠标左键右键）信息来兼容手机
   * 
   * @param in
   * @return
   */
  @Deprecated
  public int getTouchInfoButton(int in) {
    return p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):in;
  }
  /**
   * 测试输入的坐标是否在任意物品格子中
   * 
   * @param x
   * @param y
   * @return
   */
  public boolean testPosInInventorySlot(float x,float y) {
    if(player.inventory.displayState==Inventory.displayFullInventory) {
      for(DisplaySlot[] i:player.inventory.displaySlots) for(DisplaySlot e:i) if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) return true;
    }
    return false;
  }
  /**
   * 测试输入的坐标是否在主角以外的其他生物实体中
   * 
   * @param x
   * @param y
   * @return
   */
  public boolean testPosInOtherEntity(int x,int y) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) if(e instanceof LivingEntity live) if(live.inOuterBox(x,y)) return true;
    }
    return false;
  }
  /**
   * 测试输入的坐标是否在按钮上
   * 
   * @param x
   * @param y
   * @return
   */
  public boolean testPosInButtons(float x,float y) {
    for(RectF e:cullRects) if(Tools.inBox(x,y,e.x(),e.y(),e.w(),e.h())) return true;
    return false;
  }
  @Override
  public void mouseWheel(float x,float y) {
    selectHotSlot(player.inventory.selectSlotPos+(int)y);
    player.inventory.testSelectSlot();
    player.inventory.displayState(Inventory.displayHotSlot);
  }
}