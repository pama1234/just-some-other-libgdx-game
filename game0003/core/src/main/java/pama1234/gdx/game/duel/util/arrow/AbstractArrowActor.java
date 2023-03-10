package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.Actor;

public abstract class AbstractArrowActor extends Actor{
  public final float halfLength;
  public AbstractArrowActor(float _collisionRadius,float _halfLength) {
    super(_collisionRadius);
    halfLength=_halfLength;
  }
  @Override
  public void update() {
    super.update();
    if(xPosition<-halfLength||
      xPosition>Duel.INTERNAL_CANVAS_SIDE_LENGTH+halfLength||
      yPosition<-halfLength||
      yPosition>Duel.INTERNAL_CANVAS_SIDE_LENGTH+halfLength) {
      group.removingArrowList.add(this);
    }
  }
  public abstract boolean isLethal();
}