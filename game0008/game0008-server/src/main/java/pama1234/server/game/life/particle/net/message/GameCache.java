package pama1234.server.game.life.particle.net.message;

import java.util.ArrayList;

public class GameCache{
  public int updateCount;

  public CellCenterCache cellc;

  public GameCache init() {
    return this;
  }

  public static class CellCenterCache{

    public ArrayList<CellCache> cellList;

    public int playerPos;
    public int[] otherPlayer;

    public int playerOnline;

    // cellList太大了，服务端不会一次性传输完，客户端可以提前获得一部分cellList的数据然后提前刷新cell的位置数据，并在flushOnClient为true的时候进行cellList.refresh等操作
    public boolean flushOnClient;

    public CellCenterCache init() {
      cellList=new ArrayList<>();
      return this;
    }
  }

}
