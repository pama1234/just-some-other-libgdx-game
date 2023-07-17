package pama1234.app.game.server.duel.util.arrow;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.Actor;
import pama1234.math.Tools;

public abstract class AbstractArrowActor extends Actor{
  public final float halfLength;
  public AbstractArrowActor(float collisionRadius,float halfLength) {
    super(collisionRadius);
    this.halfLength=halfLength;
  }
  @Override
  public void update() {
    super.update();
    if(Tools.notInRange(pos.x,pos.y,-halfLength,-halfLength,Const.CANVAS_SIZE+halfLength,Const.CANVAS_SIZE+halfLength)) {
      group.arrowCenter.remove.add(this);
    }
  }
  public abstract boolean isLethal();
}