package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class ClientData{
  public Game game;
  public World0001 world;
  public ClientData(Game game,World0001 world) {
    this.game=game;
    this.world=world;
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
