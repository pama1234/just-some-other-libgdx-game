package pama1234.gdx.game.net.server;

import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;

import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.SocketData;

public class ServerStateWriteThread extends Thread{
  public Screen0007 p;
  public SocketData stateSocket;
  public ServerStateWriteThread(Screen0007 p,SocketData stateSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
  }
  @Override
  public void run() {
    byte[] outData=new byte[20];
    while(!p.stop) {
      p.dataSocketCenter.refresh();
      synchronized(p.dataSocketCenter.list) {
        synchronized(p.group) {
          for(SocketData e:p.dataSocketCenter.list) {
            try {
              doF(e,outData);
            }catch(IOException exception) {
              exception.printStackTrace();
            }
          }
        }
      }
    }
  }
  public void doF(SocketData e,byte[] outData) throws IOException {
    System.out.println("ServerWrite state="+e.state);
    // if(e.state==1)
    switch(e.state) {
      case FinishedProcessing: {
        writeHeader(e,outData,0);
      }
        break;
      default:
        throw new RuntimeException("state err="+e.state);
    }
  }
}