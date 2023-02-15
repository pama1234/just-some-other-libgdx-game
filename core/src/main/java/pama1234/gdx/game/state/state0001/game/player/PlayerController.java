package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.player.ControlBindUtil.GetKeyPressedBoolean;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.Block.BlockUi;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class PlayerController extends Entity<Screen0011>{
  public MainPlayer player;
  public boolean walking;
  public boolean left,right,jump,shift;
  public int walkCool,jumpCool;
  public float speed=1f,shiftSpeedMult=2f;
  public float slowDownSpeed=1/4f;
  public float jumpForceMult=1.5f,jumpHeight=0;
  public MovementLimitBox limitBox;
  public RectF[] cullRects;
  public EntityPointer selectEntity;
  public ControllerBlockPointer selectBlock;
  public float camScale=2;
  public float itemPickDist=18,itemPickMoveDist=72;
  public boolean pInAir;
  public PlayerController(Screen0011 p,MainPlayer player) {
    super(p);
    this.player=player;
    if(p.isAndroid) {
      cullRects=new RectF[] {
        new RectF(()->p.bu*1.5f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.75f+p.pus*4,()->p.bu+p.pus),
        // new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*2.5f-p.pus,()->p.pu*3.75f+p.pus,()->p.bu*2+p.pus),
        new RectF(()->p.width-p.bu*4f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.5f+p.pus*2,()->p.bu+p.pus),
        new RectF(()->p.width-p.bu*2.5f-p.pus,()->p.height-p.bu*2.5f-p.pus,()->p.bu+p.pus*2,()->p.bu+p.pus),
        //---
        new RectF(()->p.width-p.bu*3.5f-p.pus,()->p.bu*0.5f-p.pus,()->p.bu*2.75f+p.pus*4,()->p.bu+p.pus),
        new RectF(()->p.bu*0.5f-p.pus,()->p.bu*1.5f,()->p.bu+p.pus*2,()->p.bu*2+p.pus),
        //---
        exitButton(),
      };
    }else cullRects=new RectF[] {
      exitButton(),
    };
    limitBox=new MovementLimitBox(player);
    selectEntity=new EntityPointer(player.pw,()->player.inventory.select().data);
    selectBlock=new ControllerBlockPointer(player.pw,()->player.inventory.select().data);
    player.outerBox=limitBox;
  }
  public RectF exitButton() {
    return new RectF(()->p.bu*0.2f-p.pus,()->p.bu*0.2f-p.pus,()->p.bu+p.pus*2,()->p.bu+p.pus*2);
  }
  @Override
  public void display() {
    if(selectEntity.entity!=null) drawSelectEntity();
    if(selectBlock.active) drawSelectBlock();
  }
  public void drawSelectEntity() {
    LivingEntity entity=selectEntity.entity;
    float tl=UtilMath.mag(entity.type.w,entity.type.h)/2f+2;
    float tcx=entity.cx(),tcy=entity.cy();
    p.tint(255,127);
    p.image(ImageAsset.select,tcx-tl,tcy-tl,tl,tl);
    p.image(ImageAsset.select,tcx+tl,tcy-tl,-tl,tl);
    p.image(ImageAsset.select,tcx-tl,tcy+tl,tl,-tl);
    p.image(ImageAsset.select,tcx+tl,tcy+tl,-tl,-tl);
    p.noTint();
  }
  public void drawSelectBlock() {
    int tw=player.pw.settings.blockWidth,
      th=player.pw.settings.blockHeight;
    switch(selectBlock.task) {
      case BlockPointer.idle: {
        p.fill(0,127);
        float r=1;
        float tx1=selectBlock.x*tw-r;
        float ty1=selectBlock.y*th-r;
        float tx2=(selectBlock.x+1)*tw+r;
        float ty2=(selectBlock.y+1)*th+r;
        float tw1=tw+r*2;
        float th1=th+r*2;
        p.rect(tx1,ty1,1,th1);
        p.rect(tx1,ty1,tw1,1);
        p.rect(tx2-1,ty1,1,th1);
        p.rect(tx1,ty2-1,tw1,1);
      }
        break;
      case BlockPointer.destroy: {
        p.tint(255,191);
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,
              0,ti.type.blockType.buildTime+selectBlock.block.type.destroyTime,0,7)],
            selectBlock.x*tw,selectBlock.y*th);
        }
      }
        break;
      case BlockPointer.use: {
        p.tint(255,191);
        p.image(
          ImageAsset.tiles[7][8],
          selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      default:
        break;
    }
    p.noTint();
  }
  public void preUpdate() {
    for(TouchInfo e:p.touches) if(e.active) touchUpdate(e);
    updateCtrlInfo();
    // pInAir=limitBox.inAir;
    limitBox.preCtrlUpdate();
    if(pInAir!=limitBox.inAir) {
      pInAir=limitBox.inAir;
      moveEvent();
    }
    doWalkAndJump();
    // limitBox.prePointUpdate();
  }
  public void postUpdate() {
    // limitBox.postPointUpdate();
    updatePickItem();
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
      moveEvent();
    }
    if(!player.pw.p.isAndroid) updateMouseInfo();
    selectEntity.updateTask();//TODO
  }
  public void moveEvent() {
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
    // boolean[] tba=player.inventory.hotSlotKeyData;
    // int tl=UtilMath.min(tba.length,10);
    // for(int i=0;i<tl;i++) tba[i]=ControlBindUtil.isKeyPressed(ControlBindUtil.hotSlotStart+i,f);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
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
    if(p.isAndroid&&selectBlock.task!=BlockPointer.use) selectBlock.active=false;
    selectBlock.testStopTask(info);
    testWorkStationUiTouchEnded(info);
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
    if(player.inventory.displayState==Inventory.displayFullInventory) for(DisplaySlot[] i:player.inventory.displaySlots) for(DisplaySlot e:i) if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) return true;
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
  public boolean inPlayerOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,limitBox.x1,limitBox.y1,limitBox.w,limitBox.h);
  }
  @Override
  public void mouseWheel(float x,float y) {
    selectHotSlot(player.inventory.selectSlot+(int)y);
    player.inventory.testSelectSlot();
    player.inventory.displayState(Inventory.displayHotSlot);
  }
  public void selectHotSlot(int in) {
    player.inventory.select(in);
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
  public void shift(boolean in) {
    shift=in;
    float speedMult=shift?shiftSpeedMult:1;
    if(walking) player.timeStep=(1/8f)/speedMult;
    else player.timeStep=(1/2f)/speedMult;
  }
  public void doWalkAndJump() {
    if(walkCool>0) {
      walkCool--;
      walkSlowDown();
    }else if(walking) {
      float speedMult=shift?shiftSpeedMult:1;
      if(left) {
        player.point.vel.x-=speed*speedMult;
        player.flipX=true;
      }else {
        player.point.vel.x+=speed*speedMult;
        player.flipX=false;
      }
    }else walkSlowDown();
    // limitBox.testInAir();
    if(limitBox.inAir) {
      if(jump&&jumpHeight<-player.pw.settings.jumpForce*jumpForceMult) {
        player.point.vel.y-=player.pw.settings.g*2;
        jumpHeight+=player.pw.settings.g*2;
      }else player.point.vel.y+=player.pw.settings.g;
    }else {
      if(player.point.pos.y!=limitBox.floor) {
        player.point.vel.y=0;
        player.point.pos.y=limitBox.floor;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        player.point.vel.y=player.pw.settings.jumpForce*jumpForceMult*.5f;
        jumpCool=2;
        jumpHeight=-player.point.vel.y;
      }
    }
  }
  public void walkSlowDown() {
    player.point.vel.x*=slowDownSpeed;
  }
  public void updatePickItem() {
    for(DroppedItem e:player.pw.entities.items.list) {
      float td=UtilMath.dist(player.x(),player.y(),e.x(),e.y());
      if(td<itemPickDist) {
        player.inventory.accept(e.data);
        player.pw.entities.items.remove.add(e);
      }else if(td<itemPickMoveDist) {
        e.point.vel.set((player.x()-e.x())*p.random(0.1f,0.2f),(player.y()-e.y())*p.random(0.1f,0.2f));
      }
    }
  }
  public static class ControllerBlockPointer extends BlockPointer{
    public TouchInfo info;
    // public float moveX,moveY;
    public ControllerBlockPointer(World0001 in,GetItemSlot slot) {
      super(in,slot);
    }
    public void info(TouchInfo in) {
      info=in;
    }
    public void testStopTask(TouchInfo in) {
      if(info==in) stopTask();
    }
    // @Override
    // public void taskComplete() {
    //   super.taskComplete();
    //   if(info!=null) info.x+=pw.blockWidth();
    // }
  }
}