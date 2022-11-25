package pama1234.gdx.game.net.client;

import java.io.IOException;

import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.SocketData;

public class ClientStateWriteThread extends Thread{
  public Screen0003 p;
  public SocketData stateSocket;
  public ClientStateWriteThread(Screen0003 p,SocketData stateSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
  }
  @Override
  public void run() {
    byte[] outData=new byte[12];
    while(!p.stop) {
      try {
        doF(outData);
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void doF(byte[] outData) throws IOException {
    System.out.println("ClientWrite state="+stateSocket.state);
    switch(stateSocket.state) {
      case FinishedProcessing: {}
        break;
      default:
        throw new RuntimeException("state err="+stateSocket.state);
    }
  }
}