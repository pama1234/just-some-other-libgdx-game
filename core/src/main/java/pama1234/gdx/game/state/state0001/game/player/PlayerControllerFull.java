package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.player.ControlBindUtil.GetKeyPressedBoolean;
import pama1234.gdx.game.state.state0001.game.player.ControllerUtil.ControllerBlockPointer;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.Block.BlockUi;
import pama1234.gdx.game.state.state0001.game.world.WorldSettings;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class PlayerControllerFull extends PlayerControllerCore{
  public MainPlayer player;
  public boolean pInAir;
  public RectF[] cullRects;
  public float camScale=2;
  public ControllerBlockPointer selectBlock;
  public PlayerControllerFull(Screen0011 p,MainPlayer player) {
    super(p,player,true);
    this.player=player;
    cullRects=ControlBindUtil.createRectF(p);
    coreSelectBlock=selectBlock=new ControllerBlockPointer(player.pw,()->player.inventory.select().data);
  }
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
    for(TouchInfo e:p.touches) if(e.active) touchUpdate(e);
    updateCtrlInfo();
    // limitBox.preCtrlUpdate();
    super.preUpdate();
    if(pInAir!=limitBox.inAir) {
      pInAir=limitBox.inAir;
      displayStateChange();
    }
    // doWalkAndJump();
    // limitBox.prePointUpdate();
  }
  @Override
  public void postUpdate() {
    super.postUpdate();
    p.cam.point.des.set(player.cx(),player.cy());
    float ty=player.point.vel.y;
    if(player.displayState==1) {
      player.timeStep=1/UtilMath.abs(player.point.vel.x);
    }else if(player.displayState==2) {
      if(ty<0) player.frameTime=0;
      else if(ty<8) player.frameTime=1;
      else player.frameTime=2;
    }
  }
  public void updateCtrlInfo() {
    updateKeyInfo();
    boolean tb=left!=right;
    if(walking!=tb) {
      walking=tb;
      displayStateChange();
    }
    if(!player.pw.p.isAndroid) updateMouseInfo();
    selectEntity.updateTask();//TODO
  }
  public void displayStateChange() {
    float speedMult=shift?shiftSpeedMult:1;
    if(limitBox.inAir) {
      player.timeStep=-1;
      player.frameTime=0;
      player.displayState=2;
    }else if(walking) {
      player.timeStep=-1;
      player.frameTime=0;
      player.displayState=1;
    }else {
      player.timeStep=(1/2f)/speedMult;
      player.frameTime=0;
      player.displayState=0;
    }
    player.testFrameTime();
  }
  public void updateMouseInfo() {
    selectBlock.active=false;
    if(testPosInButtons(p.mouse.ox,p.mouse.oy)) return;
    if(testPosInInventorySlot(p.mouse.x,p.mouse.y)) return;
    int tx=player.xToBlockCord(p.mouse.x),
      ty=player.xToBlockCord(p.mouse.y);
    if(inPlayerOuterBox(tx,ty)) return;
    selectBlock.active=true;
    Block block=player.getBlock(tx,ty);
    selectBlock.update(block,tx,ty);
  }
  public void updateKeyInfo() {
    GetKeyPressedBoolean f=p::isKeyPressed;
    left=ControlBindUtil.isKeyPressed(ControlBindUtil.moveLeft,f);
    right=ControlBindUtil.isKeyPressed(ControlBindUtil.moveRight,f);
    jump=ControlBindUtil.isKeyPressed(ControlBindUtil.jumpUp,f);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    if(player.p.touchCount>1) {
      selectBlock.active=false;
      return;
    }
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(updateAndTestInventorySlot(info.x,info.y,info.button)) return;
    if(updateAndTestWorkStationUi(info)) return;
    if(p.isAndroid&&inPlayerOuterBox(tx,ty)) {
      player.inventory.displayStateChange();
      return;
    }
    if(updateAndTestSelectEntity(tx,ty,info.button)) return;
    Block block=player.getBlock(tx,ty);
    selectBlock.active=true;
    selectBlock.update(block,tx,ty);
    selectBlock.info(info);
    selectBlock.startTaskButtonInfo(getTouchInfoButton(info.button));
  }
  public void touchUpdate(TouchInfo info) {
    if(!player.pw.p.isAndroid) return;
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    if(testPosInInventorySlot(info.x,info.y)) return;
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(inPlayerOuterBox(tx,ty)) return;
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
        if(info==temp||!info.active) continue;
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
  public void testWorkStationUiTouchEnded(TouchInfo info) {
    if(selectBlock.task==BlockPointer.use) {
      BlockUi ui=selectBlock.block.ui;
      TextButtonCam<?>[] camButton=ui.camButton;
      for(TextButtonCam<?> e:camButton) if(info==e.touch) e.clickEnd();
    }
  }
  public boolean updateAndTestInventorySlot(float x,float y,int button) {
    if(player.inventory.displayState==Inventory.displayFullInventory) {
      DisplaySlot[] slots=player.inventory.hotSlots;
      for(int i=0;i<slots.length;i++) if(testAndSelectSlot(x,y,button,slots,i)) return true;
      slots=player.inventory.backpackSlots;
      for(DisplaySlot e:slots) if(testSlot(x,y,button,e)) return true;
    }
    return false;
  }
  public boolean testSlot(float x,float y,int button,DisplaySlot e) {
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          player.inventory.switchHold(e,Inventory.moveAll);
        }
          break;
        case Buttons.RIGHT: {
          player.inventory.switchHold(e,Inventory.moveOne);
        }
          break;
      }
      return true;
    }
    return false;
  }
  public boolean testAndSelectSlot(float x,float y,int button,DisplaySlot[] slots,int i) {
    DisplaySlot e=slots[i];
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          player.inventory.switchHold(e,Inventory.moveAll);
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
  public void creativeModeUpdateSelectBlock(TouchInfo info,int tx,int ty,Block block) {
    if(block!=null) switch(getTouchInfoButton(info.button)) {
      case Buttons.LEFT: {
        if(block.type!=player.pw.metaBlocks.air) player.pw.destroyBlock(player,block,tx,ty);
      }
        break;
      case Buttons.RIGHT: {
        ItemSlot td=player.inventory.select().data;
        Item ti=td.item;
        if(ti!=null) {
          MetaBlock tb=ti.type.blockType;
          if(tb!=null&&block.type!=tb) {
            player.pw.placeBlock(player,block,tb,tx,ty);
          }
        }
      }
        break;
    }
  }
  public int getTouchInfoButton(int in) {
    return p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):in;
  }
  public boolean testPosInInventorySlot(float x,float y) {
    if(player.inventory.displayState==Inventory.displayFullInventory) {
      for(DisplaySlot[] i:player.inventory.displaySlots) for(DisplaySlot e:i) if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) return true;
    }
    return false;
  }
  public boolean testPosInOtherEntity(int x,int y) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) if(e instanceof LivingEntity live) if(live.inOuterBox(x,y)) return true;
    }
    return false;
  }
  public boolean testPosInButtons(float x,float y) {
    for(RectF e:cullRects) if(Tools.inBox(x,y,e.x(),e.y(),e.w(),e.h())) return true;
    return false;
  }
  @Override
  public void mouseWheel(float x,float y) {
    selectHotSlot(player.inventory.selectSlot+(int)y);
    player.inventory.testSelectSlot();
    player.inventory.displayState(Inventory.displayHotSlot);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift(true);
    else if(keyCode==Keys.E) player.inventory.displayStateChange();
    else if(keyCode==Keys.V) shift(!shift);
    else if(keyCode==Keys.EQUALS) camScale(0.5f);
    else if(keyCode==Keys.MINUS) camScale(-0.5f);
    else if(testIsHotSlotKey(keyCode,true)) player.inventory.selectSlotWithKeyEvent();
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift(false);
    else if(testIsHotSlotKey(keyCode,false)) player.inventory.selectSlotWithKeyEvent();
  }
  public boolean testIsHotSlotKey(int keyCode,boolean bIn) {
    boolean[] tba=player.inventory.hotSlotKeyData;
    int tl=UtilMath.min(tba.length,10);
    boolean flag=false;
    if(!bIn&&!tba[0]) return flag;
    boolean t_tba_0=tba[0];
    for(int i=0;i<tl;i++) if(ControlBindUtil.isKey(ControlBindUtil.hotSlotStart+i,keyCode)) {
      tba[i]=bIn;
      flag=true;
    }
    if(!t_tba_0&&tba[0]) for(int i=1;i<tba.length;i++) tba[i]=false;
    return flag;
  }
  public void camScale(float in) {
    p.cam2d.scaleAdd(in);
    camScale=p.cam2d.scale.des;
  }
}