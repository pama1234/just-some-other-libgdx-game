package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.net.ClientIO.ClientRead;
import pama1234.gdx.game.state.state0001.game.net.ClientIO.ClientWrite;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class ClientCore{
  public Game pg;
  public World0001 pw;
  //---
  public SocketData clientSocket;
  public ClientData clientData;
  //---
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  //---
  public Thread connectThread;
  public ClientCore(Game pg,World0001 pw,SocketData clientSocket) {
    this.pg=pg;
    this.pw=pw;
    clientData=new ClientData(pg,pw);
    connectThread=new Thread() {
      public void run() {
        while(!clientSocket.s.isConnected()) {
          try {
            sleep(200);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
        //TODO
        (clientRead=new ClientRead(clientData,clientSocket)).start();
        (clientWrite=new ClientWrite(clientData,clientSocket)).start();
      }
    };
  }
  public void start() {
    connectThread.start();
  }
}