package pama1234.game.app.server.game.net.data;

import pama1234.game.app.server.game.ServerPlayer3D;
import pama1234.game.app.server.game.net.CellData;

public class ClientCore{
  public volatile CellData[] cellData;
  public ServerPlayer3D yourself;
  public ClientCore(int size,String name) {
    cellData=new CellData[size];
    for(int i=0;i<cellData.length;i++) cellData[i]=new CellData();
    yourself=new ServerPlayer3D(name,0,0,0);
  }
  public ClientCore(int size,ServerPlayer3D yourself) {
    cellData=new CellData[size];
    for(int i=0;i<cellData.length;i++) cellData[i]=new CellData();
    this.yourself=yourself;
  }
  public void sleep(long in) {
    try {
      Thread.sleep(in);
    }catch(InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);//TODO
    }
  }
}