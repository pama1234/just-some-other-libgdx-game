package pama1234.gdx.game.net;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;

public class ServerThread extends Thread{
  public Screen0003 p;
  public ServerThread(Screen0003 p) {
    this.p=p;
  }
  @Override
  public void run() {
    byte[] outData=new byte[20];
    while(!p.stop) {
      synchronized(p.centerSocket.list) {
        synchronized(p.group) {
          for(SocketData e:p.centerSocket.list) {
            try {
              // i.o.write(new byte[] {(byte)((frameCount>>24)&0xff),(byte)((frameCount>>16)&0xff),(byte)((frameCount>>8)&0xff),(byte)(frameCount&0xff)});
              for(int i=0;i<p.group.size;i++) {
                ByteUtil.intToByte(i,outData);
                ByteUtil.intToByte(p.group.type[i],outData,4);
                ByteUtil.floatToByte(p.group.x(i),outData,8);
                ByteUtil.floatToByte(p.group.y(i),outData,12);
                ByteUtil.floatToByte(p.group.z(i),outData,16);
                e.o.write(outData);
              }
              e.o.flush();
            }catch(IOException exception) {
              exception.printStackTrace();
            }
          }
        }
      }
    }
  }
}