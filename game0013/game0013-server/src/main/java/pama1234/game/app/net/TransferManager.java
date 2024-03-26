package pama1234.game.app.net;

import com.esotericsoftware.kryo.Kryo;

public class TransferManager{
  public NetConst netConst;
  public Kryo kryo;

  public TransferManager() {
    init();
  }

  public TransferManager init() {
    kryo=new Kryo();
    kryo.register(NetConst.class);
    kryo.register(RoomInfo.class);

    return this;
  }
}
