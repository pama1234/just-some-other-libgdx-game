package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.physics.MassPoint;

public class DroppedItem extends GamePointEntity<MassPoint>{
  public DroppedItemCenter pdic;
  public Item data;
  // public MovementLimitBox limitBox;
  public DroppedItem(Game pg,float x,float y,Item data) {
    super(pg.p,new MassPoint(x,y),pg);
    this.data=data;
    pdic=data.type.pc.pw.entities.items;
    // limitBox=new MovementLimitBox(this);
  }
  @Override
  public void display() {
    p.image(data.type.tiles[0],x()+pdic.dx,y()+pdic.dy,pdic.w,pdic.h);
  }
  public static class DroppedItemCenter extends EntityCenter<Screen0011,DroppedItem>{
    public int w=12,h=12,
      dx=-6,dy=-12;
    public DroppedItemCenter(Screen0011 p) {
      super(p);
    }
  }
}