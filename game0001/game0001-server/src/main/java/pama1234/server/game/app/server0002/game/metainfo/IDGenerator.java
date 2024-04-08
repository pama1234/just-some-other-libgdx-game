package pama1234.server.game.app.server0002.game.metainfo;

import pama1234.util.function.GetInt;

public class IDGenerator implements GetInt{
  public int count;
  @Override
  public synchronized int get() {
    int out=count;
    count+=1;
    return out;
  }
}
