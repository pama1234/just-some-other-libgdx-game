package pama1234.game.app.server.server0002.game.metainfo;

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
