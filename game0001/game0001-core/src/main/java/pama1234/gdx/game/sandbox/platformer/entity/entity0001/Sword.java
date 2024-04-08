package pama1234.gdx.game.sandbox.platformer.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.sandbox.platformer.entity.util.MovementLimitBox;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
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
    p.image(data.type.rttr.tiles[0],x()+type.attr.dx,y()+type.attr.dy,type.attr.w,type.attr.h);
  }
  public static class SwordType extends MetaCreature<DroppedItem>{
    {
      attr.w=12;
      attr.h=12;
      attr.dx=-6;
      attr.dy=-12;
    }
    public SwordType(MetaCreatureCenter0001<?> pc,int id) {
      super(pc,"sword",id,4,0,0);
      attr.immortal=true;
    }
    @Override
    public void init() {}
  }
}