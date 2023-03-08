package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.math.physics.MassPoint;

public class Sword extends LivingEntity{
  public Item data;
  public MovementLimitBox limitBox;
  public Sword(Screen0011 p,World0001 pw,float x,float y,float xVel,float yVel,DroppedItemType type,Item data) {
    super(p,pw,new MassPoint(x,y,xVel,yVel),type);
    this.data=data;
    limitBox=new MovementLimitBox(this);
  }
  @Override
  public void update() {
    limitBox.preCtrlUpdate();
    move();
    if(limitBox.inAir) point.vel.y+=pw.settings.g;
    super.update();
  }
  public void move() {}
  @Override
  public void display() {
    p.image(data.type.tiles[0],x()+type.dx,y()+type.dy,type.w,type.h);
  }
  public static class SwordType extends MetaCreature<DroppedItem>{
    {
      w=12;
      h=12;
      dx=-6;
      dy=-12;
    }
    public SwordType(MetaCreatureCenter0001<?> pc,int id) {
      super(pc,"sword",id,4,0,0);
      immortal=true;
    }
    @Override
    public void init() {}
  }
}