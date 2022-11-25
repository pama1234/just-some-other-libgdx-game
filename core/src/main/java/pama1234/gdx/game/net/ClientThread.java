package pama1234.gdx.game.net;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;

public class ClientThread extends Thread{
  public Screen0003 p;
  public ClientThread(Screen0003 p) {
    this.p=p;
  }
  @Override
  public void run() {
    // byte[] td=new byte[4];
    byte[] inData=new byte[20];
    int ti;
    while(!p.stop) {
      try {
        // int ti=client.i.readNBytes(td,0,4);
        // println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
        // synchronized(cellData) {
        for(int i=0;i<p.cellData.length;i++) {
          ti=0;
          while(ti==0) ti=p.client.i.readNBytes(inData,0,inData.length);
          p.cellData[i].id=ByteUtil.byteToInt(inData);
          p.cellData[i].type=ByteUtil.byteToInt(inData,4);
          p.cellData[i].x=ByteUtil.byteToFloat(inData,8);
          p.cellData[i].y=ByteUtil.byteToFloat(inData,12);
          p.cellData[i].z=ByteUtil.byteToFloat(inData,16);
        }
        // }
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
}
