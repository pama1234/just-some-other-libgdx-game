package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.math.physics.MassPoint;

public class DroppedItem extends GamePointEntity<MassPoint>{
  public Item data;
  public DroppedItem(Game pg,int x,int y,Item data) {
    super(pg.p,new MassPoint(x,y),pg);
    this.data=data;
  }
  @Override
  public void display() {}
  // public static class DroppedItemType extends MetaCreature<DroppedItem>{
  //   {
  //     w=12;
  //     h=12;
  //     dx=-6;
  //     dy=-12;
  //   }
  //   public DroppedItemType(MetaCreatureCenter0001 pc,int id) {
  //     super(pc,"dropped item",id,4,0,0);
  //   }
  //   @Override
  //   public void init() {}
  // }
}