package pama1234.app.game.server.duel;

@FunctionalInterface
public interface GetRandom{
  public float random(float min,float max);
  public default float random(float max) {
    return random(0,max);
  };
}