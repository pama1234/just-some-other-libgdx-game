package pama1234.gdx.game.sandbox.platformer.player;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.KryoUtil;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.player.ctrl.PlayerControllerFull;
import pama1234.gdx.game.sandbox.platformer.region.LoadAndSave;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.WorldKryoUtil;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.info.TouchInfo;

public class MainPlayer extends Player implements LoadAndSave{
  public FileHandle dataLocation;

  public CameraController2D cam;
  public PlayerControllerFull ctrl;
  @Deprecated
  public MainPlayer() {//kryo only
    super(null,null,0,0,null);
  }
  public MainPlayer(Screen0011 p,WorldBase2D<? extends WorldType0001Base<?>> pw,float x,float y,FileHandle dataLocation) {
    super(p,pw,x,y,pw.type.metaEntitys.player);
    innerInit();
    this.dataLocation=dataLocation;
  }
  @Override
  public void innerInit() {
    this.cam=p.cam2d;
    ctrlCore=ctrl=new PlayerControllerFull(p,this);
  }
  @Override
  public void display() {
    super.display();
    ctrl.display();
    p.noTint();
    inventory.display();
    p.noTint();
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    ctrl.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    ctrl.keyReleased(key,keyCode);
  }
  @Override
  public void mouseWheel(float x,float y) {
    ctrl.mouseWheel(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    ctrl.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    ctrl.touchEnded(info);
  }
  @Override
  public void load() {
    WorldKryoUtil.playerInstance=this;
    KryoUtil.load(WorldKryoUtil.kryo,dataLocation,MainPlayer.class);
    WorldKryoUtil.playerInstance=null;
    p.cam2d.scale.des=camScale;
    ctrl.limitBox.prePointUpdate();
    if(p.settings.printLog) System.out.println("MainPlayer.load() "+point.pos);
    if(inventory==null) createInventory();
    else {
      MetaItem[] mitem=pw.type.metaItems.array();
      for(ItemSlot e:inventory.data) {
        Item ti=e.item;
        if(ti!=null) ti.type=mitem[ti.typeId];
      }
      inventory.pe=this;
      inventory.innerInit();
    }
  }
  @Override
  public void save() {
    if(p.settings.printLog) System.out.println("MainPlayer.save() "+point.pos);
    KryoUtil.save(WorldKryoUtil.kryo,dataLocation,this);
  }
  public float circleSize() {//TODO
    return type.attr.w;
  }
}