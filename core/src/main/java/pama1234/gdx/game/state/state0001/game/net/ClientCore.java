package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.net.SocketData;

public class ClientCore{
  public Game game;
  public World0001 world;
  //---
  public SocketData socketData;
  public ClientCore(Game game,World0001 world,SocketData socketData) {
    this.game=game;
    this.world=world;
    this.socketData=socketData;
  }
  public void start() {}
}