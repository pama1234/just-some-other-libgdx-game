package pama1234.gdx.game.state.state0001.game.net;

public class NetState{
  public static class ClientToServer{
    public static final int playerCtrl=0;
    public static final int playerAuth=1;
    public static final int playerMoveItem=2;
    public static final int playerChangeBlock=3;
  }
  public static class ServerToClient{
    public static final int playerPos=0;
    public static final int chunkData=1;
    public static final int needAuth=2;
    public static final int entityData=3;
  }
}
