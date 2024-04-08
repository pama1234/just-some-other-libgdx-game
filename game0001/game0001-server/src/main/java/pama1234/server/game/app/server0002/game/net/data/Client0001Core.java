package pama1234.server.game.app.server0002.game.net.data;

public class Client0001Core{
  public volatile CellData[] cellData;
  public ServerPlayer3D yourself;
  public Client0001Core(int size,String name) {
    cellData=new CellData[size];
    for(int i=0;i<cellData.length;i++) cellData[i]=new CellData();
    yourself=new ServerPlayer3D(name,0,0,0);
  }
  public Client0001Core(int size,ServerPlayer3D yourself) {
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