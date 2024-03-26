package pama1234.server.game.net;

import java.util.LinkedList;

public class TransferPool<T>{
  public LinkedList<T> list;
  public LinkedList<T> pool;
  public TransferPool() {
    list=new LinkedList<>();
    pool=new LinkedList<>();
  }
}
