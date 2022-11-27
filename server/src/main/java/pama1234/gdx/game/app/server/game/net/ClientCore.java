package pama1234.gdx.game.app.server.game.net;

public class ClientCore{
  public volatile CellData[] cellData;
  public ClientCore(int size) {
    cellData=new CellData[size];
    for(int i=0;i<cellData.length;i++) cellData[i]=new CellData();
  }
}