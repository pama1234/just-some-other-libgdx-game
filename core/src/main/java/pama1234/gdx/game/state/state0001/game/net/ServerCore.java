package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.net.ServerSocketData;

public class ServerCore{
  public Game game;
  public World0001 world;
  //---
  public ServerSocketData serverSocketData;
  public ServerCore(Game game,World0001 world,ServerSocketData serverSocketData) {
    this.game=game;
    this.world=world;
    this.serverSocketData=serverSocketData;
  }
  public void start() {}
}