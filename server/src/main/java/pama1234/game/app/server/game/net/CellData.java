package pama1234.game.app.server.game.net;

// public class CellData{
public final class CellData{
  public int id,type;
  public float x,y,z;
  public CellData() {}
  public CellData(int type,float x,float y,float z) {
    this.type=type;
    this.x=x;
    this.y=y;
    this.z=z;
  }
}
