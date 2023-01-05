package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.physics.MassPoint;

public class DroppedItem extends LivingEntity{
  public DroppedItemCenter pdic;
  public Item data;
  public MovementLimitBox limitBox;
  public DroppedItem(Screen0011 p,World0001 pw,float x,float y,DroppedItemType type,Item data) {
    super(p,pw,new MassPoint(x,y),type);
    this.data=data;
    pdic=data.type.pc.pw.entities.items;
    limitBox=new MovementLimitBox(this);
  }
  @Override
  public void update() {
    super.update();
    limitBox.update();
    limitBox.updateLimit();
    limitBox.doInAirTest();
    if(limitBox.inAir) point.vel.y+=pw.g;
    limitBox.constrain();
  }
  @Override
  public void display() {
    // p.image(data.type.tiles[0],x()+pdic.dx,y()+pdic.dy,pdic.w,pdic.h);
    p.image(data.type.tiles[0],x()+type.dx,y()+type.dy,type.w,type.h);
  }
  public static class DroppedItemType extends MetaCreature<DroppedItem>{
    {
      w=12;
      h=12;
      dx=-6;
      dy=-12;
    }
    public DroppedItemType(MetaCreatureCenter0001 pc,int id) {
      super(pc,"dropped item",id,4,0,0);
    }
    @Override
    public void init() {}
  }
  public static class DroppedItemCenter extends EntityCenter<Screen0011,DroppedItem>{
    // public int w=12,h=12,
    //   dx=-6,dy=-12;
    public DroppedItemCenter(Screen0011 p) {
      super(p);
    }
  }
}