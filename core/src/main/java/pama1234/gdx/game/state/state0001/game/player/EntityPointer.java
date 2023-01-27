package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot.GetInventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class EntityPointer{
  public static final int idle=0,attack=1;
  public World0001 pw;
  public GetInventorySlot slot;
  // public List<LivingEntity> entityList;
  public LivingEntity selectEntity;
  public boolean active;
  public int task;
  // public float progress;
  public EntityPointer(World0001 in) {
    pw=in;
  }
  public EntityPointer(World0001 in,GetInventorySlot slot) {
    pw=in;
    this.slot=slot;
  }
  public InventorySlot slot() {
    return slot.get();
  }
  public void startTask(int type) {
    task=type;
    // progress=0;
  }
  public void startTaskButtonInfo(int button) {
    switch(button) {
      case Buttons.LEFT: {
        startTask(attack);
      }
        break;
      case Buttons.RIGHT: {
        // startTask(attack);//TODO
      }
        break;
    }
  }
  public void updateTask() {
    // progress+=getSpeed(slot.get().item);
    // System.out.println(getSpeed(slot.get().item,block));
    testTaskComplete();
  }
  public static float getSpeed(Item item) {
    if(item==null) return 1;
    MetaItem mi=item.type;
    int itemType=mi.toolType;
    // System.out.println(itemType+" "+blockType);
    if(itemType==MetaItem.notWeapon) return 1;
    else return mi.speed;
  }
  public void testTaskComplete() {
    switch(task) {
      case attack: {
        if(selectEntity.life.des<=0) stopTask();
      }
        break;
    }
  }
  public void stopTask() {
    task=idle;
    // progress=0;
  }
}