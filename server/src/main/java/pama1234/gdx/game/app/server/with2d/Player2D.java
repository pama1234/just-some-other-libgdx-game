package pama1234.gdx.game.app.server.with2d;

import java.net.SocketAddress;

import pama1234.gdx.game.app.server.game.Player;
import pama1234.gdx.game.app.server.with2d.net.PlayerInfo2D;
import pama1234.gdx.util.entity.ServerPointEntity;
import pama1234.math.physics.MassPoint;

public class Player2D extends ServerPointEntity<MassPoint> implements Player{
  public PlayerInfo2D info;
  public SocketAddress addr;
  public Player2D(MassPoint in,SocketAddress addr) {
    super(in);
    info=new PlayerInfo2D();
    this.addr=addr;
  }
  public Player2D(MassPoint in) {
    super(in);
    info=new PlayerInfo2D();
  }
  @Override
  public void display() {}
  @Override
  public void dispose() {}
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
}
