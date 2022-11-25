package pama1234.gdx.game.net;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;

public class ClientReadThread extends Thread{
  public Screen0003 p;
  public ClientReadThread(Screen0003 p) {
    this.p=p;
  }
  @Override
  public void run() {
    // byte[] td=new byte[4];
    byte[] inData=new byte[20];
    // int state;
    // int readSize;
    while(!p.stop) {
      try {
        // int ti=client.i.readNBytes(td,0,4);
        // println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
        // synchronized(cellData) {
        // state=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        // readSize=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        doF(inData,
          ByteUtil.byteToInt(readNBytes(inData,0,4),0),
          ByteUtil.byteToInt(readNBytes(inData,0,4),0));
        // doF(inData,state,readSize);
        // }
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void doF(byte[] inData,int state,int readSize) throws IOException {
    System.out.println("ClientRead state="+state+" readSize="+readSize);
    // p.println(state,readSize);
    switch(state) {
      case 1: {
        if(readSize!=4) throw new RuntimeException("state 0 readSize!=4 "+readSize);//TODO
        readNBytes(inData,0,readSize);
        // System.out.println(ByteUtil.byteToInt(inData,0));
        p.client.state=1;
        // p.println("p.client.state=0");//TODO
        // p.println(inData);
      }
        break;
      case 2: {
        if(readSize!=p.cellData.length) throw new RuntimeException("state 1 readSize!=p.cellData.length "+readSize+" "+p.cellData.length);//TODO
        for(int i=0;i<readSize;i++) {
          readNBytes(inData,0,inData.length);
          p.cellData[i].id=ByteUtil.byteToInt(inData,0);
          p.cellData[i].type=ByteUtil.byteToInt(inData,4);
          p.cellData[i].x=ByteUtil.byteToFloat(inData,8);
          p.cellData[i].y=ByteUtil.byteToFloat(inData,12);
          p.cellData[i].z=ByteUtil.byteToFloat(inData,16);
        }
      }
        break;
      default:
        int ti=p.client.state;
        p.client.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
  public byte[] readNBytes(byte[] out,int offset,int size) throws IOException {
    int ti=0;
    while(ti==0) ti=p.client.i.readNBytes(out,offset,size);
    return out;
  }
}