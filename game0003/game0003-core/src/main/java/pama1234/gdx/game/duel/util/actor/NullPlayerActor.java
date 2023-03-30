package pama1234.gdx.game.duel.util.actor;

public final class NullPlayerActor extends AbstractPlayerActor{
  public NullPlayerActor(float xIn,float yIn) {
    super(0,null);
    xPosition=xIn;
    yPosition=yIn;
  }
  @Override
  public void act() {}
  @Override
  public void display() {}
  @Override
  public boolean isNull() {
    return true;
  }
}