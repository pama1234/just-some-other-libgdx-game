package pama1234.gdx.game.state.state0001.game.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.region.LoadAndSave;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.physics.MassPoint;

public class MainPlayer extends Player implements LoadAndSave{
  public FileHandle dataLocation;
  //---
  public CameraController2D cam;
  public PlayerControllerFull ctrl;
  @Deprecated
  public MainPlayer() {//kryo only
    super(null,null,0,0,null);
  }
  public MainPlayer(Screen0011 p,World0001 pw,float x,float y,FileHandle dataLocation) {
    super(p,pw,x,y,pw.metaEntitys.player);
    innerInit();
    this.dataLocation=dataLocation;
  }
  public void innerInit() {
    this.cam=p.cam2d;
    ctrlCore=ctrl=new PlayerControllerFull(p,this);
  }
  @Override
  public void init() {
    if(inventory==null) {
      inventory=new Inventory(this,52,9);
      inventory.data[5].item=pw.metaItems.workbench.createItem(16);
    }
  }
  // @Override
  // public void update() {
  //   ctrl.preUpdate();
  //   super.update();
  //   ctrl.postUpdate();
  //   //---
  //   inventory.update();
  //   if(life.pos<=0) respawn();
  // }
  @Override
  public void display() {
    super.display();
    ctrl.display();
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
    MetaItem[] mitem=pw.metaItems.list.toArray(new MetaItem[pw.metaItems.list.size()]);
    if(dataLocation.exists()) try(Input input=new Input(new FileInputStream(dataLocation.file()))) {
      MassPoint tp=WorldKryoUtil.kryo.readObject(input,MassPoint.class);
      point.cloneFrom(tp);
      ctrl.limitBox.prePointUpdate();
      if(pw.pg.debug) System.out.println(point.pos);
      inventory=WorldKryoUtil.kryo.readObject(input,Inventory.class);
      for(ItemSlot e:inventory.data) {
        Item ti=e.item;
        if(ti!=null) ti.type=mitem[ti.typeId];
      }
      inventory.pc=this;
      inventory.innerInit();
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
    // WorldKryoUtil.playerInstance=this;
    // KryoUtil.load(WorldKryoUtil.kryo,dataLocation,MainPlayer.class);
    // WorldKryoUtil.playerInstance=null;
  }
  @Override
  public void save() {
    if(inventory!=null) try(Output output=new Output(new FileOutputStream(dataLocation.file()))) {
      if(pw.pg.debug) System.out.println(point.pos);
      WorldKryoUtil.kryo.writeObject(output,point);
      WorldKryoUtil.kryo.writeObject(output,inventory);
      output.close();
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
  }
}