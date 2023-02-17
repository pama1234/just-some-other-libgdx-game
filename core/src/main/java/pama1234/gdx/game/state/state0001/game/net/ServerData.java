package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.net.ServerIO.ServerRead;
import pama1234.gdx.game.state.state0001.game.net.ServerIO.ServerWrite;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class ServerData{
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  //---
  public Game game;
  public World0001 world;
  public Object lock;
  public ServerData(Center<SocketData> socketCenter,Center<ServerRead> serverReadPool,Center<ServerWrite> serverWritePool,
    Game pg,World0001 pw) {
    this.socketCenter=socketCenter;
    this.serverReadPool=serverReadPool;
    this.serverWritePool=serverWritePool;
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
