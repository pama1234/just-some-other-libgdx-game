package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class EntityPointer extends PointerBase{
  public static final int idle=0,attack=1;
  public LivingEntity entity;
  public EntityPointer(WorldBase2D<? extends WorldType0001Base<?>> in) {
    super(in);
  }
  public EntityPointer(WorldBase2D<? extends WorldType0001Base<?>> in,GetItemSlot slot) {
    super(in,slot);
  }
  public void startTask(int type) {
    task=type;
  }
  public void startTaskButtonInfo(int button) {
    switch(button) {
      case Buttons.LEFT: {
        startTask(attack);
      }
        break;
      case Buttons.RIGHT: {}
        break;
    }
  }
  @Override
  public void updateTask() {
    if(entity==null) task=idle;
    else if(task==attack) {
      Item item=slot().item;
      if(item!=null) entity.life.des-=item.type.damage;
      else entity.life.des-=1/60f;
    }
    testTaskComplete();
  }
  public static float getSpeed(Item item) {
    if(item==null) return 1;
    MetaItem mi=item.type;
    int itemType=mi.toolType;
    if(itemType==MetaItem.notWeapon) return 1;
    else return mi.speed;
  }
  @Override
  public void testTaskComplete() {
    switch(task) {
      case attack: {
        if(entity.life.des<=0) stopTask();
      }
        break;
    }
  }
  @Override
  public void stopTask() {
    task=idle;
    entity=null;
  }
}