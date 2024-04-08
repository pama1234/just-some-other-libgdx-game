package pama1234.server.app;

import pama1234.server.game.life.particle.element.ParticleSandboxServer;
import pama1234.server.game.life.particle.net.message.RoomInfo;
import pama1234.util.UtilServer;

public class DedicatedServer extends UtilServer{
  public ParticleSandboxServer sandbox;

  @Override
  public void init() {
    sandbox=new ParticleSandboxServer(this,new RoomInfo().initDefault(),true,true);

    center.add.add(sandbox);
  }

  @Override
  public void update() {
    // System.out.println(sandbox.cellCenter.list.getFirst().point.pos);
  }

  @Override
  public void dispose() {}

}
