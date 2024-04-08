package pama1234.app.game.server.duel.util.arrow;

/**
 * TODO 更好的继承结构
 */
public abstract class ServerLongbowArrowComponent extends AbstractArrowActor{
  public ServerLongbowArrowComponent() {
    super(16,16);
  }
  @Override
  public void act() {}
  @Override
  public boolean isLethal() {
    return true;
  }
  // @Override
  // public void display() {}
}
