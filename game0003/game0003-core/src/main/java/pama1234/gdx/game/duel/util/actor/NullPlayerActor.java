package pama1234.gdx.game.duel.util.actor;

public final class NullPlayerActor extends AbstractPlayerActor{
  public NullPlayerActor() {
    super(0,null);
    xPosition=Float.NaN;
    yPosition=Float.NaN;
  }
  public NullPlayerActor(float xIn,float yIn,ActorGroup group) {
    super(0,null);
    xPosition=xIn;
    yPosition=yIn;
    this.group=group;
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