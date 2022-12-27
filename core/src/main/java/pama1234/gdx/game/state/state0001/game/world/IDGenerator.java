package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.util.function.GetInt;

public class IDGenerator implements GetInt{
  public int count;
  @Override
  public synchronized int get() {
    int out=count;
    count+=1;
    return out;
  }
}
