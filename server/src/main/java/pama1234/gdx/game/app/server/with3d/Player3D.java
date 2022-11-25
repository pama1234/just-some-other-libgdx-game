package pama1234.gdx.game.app.server.with3d;

import pama1234.gdx.game.app.server.game.Player;
import pama1234.gdx.game.app.server.with3d.net.PlayerInfo3D;
import pama1234.gdx.util.entity.ServerPoint3DEntity;
import pama1234.math.physics.PathPoint3D;

public class Player3D extends ServerPoint3DEntity<PathPoint3D> implements Player{
  public PlayerInfo3D info;
  public Player3D() {
    super(null);//TODO
    info=new PlayerInfo3D();
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
