package pama1234.gdx.game.duel.util.actor;

public final class NullPlayerActor extends AbstractPlayerActor{
  public NullPlayerActor() {
    super(0.0f,null);
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