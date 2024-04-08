package pama1234.gdx.game.element.duel.server;

@FunctionalInterface
public interface GetRandom{
  public float random(float min,float max);
  public default float random(float max) {
    return random(0,max);
  };
}