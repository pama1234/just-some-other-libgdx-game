package pama1234.app.game.server.duel.util.arrow;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.Actor;

public abstract class AbstractArrowActor extends Actor{
  public final float halfLength;
  public AbstractArrowActor(float collisionRadius,float halfLength) {
    super(collisionRadius);
    this.halfLength=halfLength;
  }
  @Override
  public void update() {
    super.update();
    if(xPosition<-halfLength||
      xPosition>Const.CANVAS_SIZE+halfLength||
      yPosition<-halfLength||
      yPosition>Const.CANVAS_SIZE+halfLength) {
      group.removingArrowList.add(this);
    }
  }
  public abstract boolean isLethal();
}