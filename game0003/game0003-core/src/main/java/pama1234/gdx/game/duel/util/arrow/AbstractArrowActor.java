package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.Actor;

public abstract class AbstractArrowActor extends Actor{
  public final Duel duel;
  public final float halfLength;
  public AbstractArrowActor(Duel duel,float collisionRadius,float halfLength) {
    super(collisionRadius);
    this.duel=duel;
    this.halfLength=halfLength;
  }
  @Override
  public void update() {
    super.update();
    if(xPosition<-halfLength||
      xPosition>Duel.CANVAS_SIZE+halfLength||
      yPosition<-halfLength||
      yPosition>Duel.CANVAS_SIZE+halfLength) {
      group.removingArrowList.add(this);
    }
  }
  public abstract boolean isLethal();
}