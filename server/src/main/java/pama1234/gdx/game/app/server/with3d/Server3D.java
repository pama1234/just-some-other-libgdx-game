package pama1234.gdx.game.app.server.with3d;

import pama1234.gdx.game.app.server.UtilServer;
import pama1234.gdx.game.app.server.game.ServerPlayerCenter;
import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;

public class Server3D extends UtilServer{
  CellGroup3D group;
  ServerPlayerCenter<ServerPlayer3D> playerCenter;
  @Override
  public void init() {
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    group=gen.GenerateFromMiniCore();
    playerCenter=new ServerPlayerCenter<ServerPlayer3D>();
  }
  @Override
  public void update() {
    group.update();
    playerCenter.update();
    // kryo.writeClassAndObject(out,group.posX);
    // kryo.writeClassAndObject(out,group.posY);
    // kryo.writeClassAndObject(out,group.posZ);
    // System.out.println(out.getMaxCapacity());
    // System.out.println(out.getBuffer().length);
    // System.out.println(Arrays.toString(out.getBuffer()));
    // System.out.println(group.posX[0]+" "+group.posY[0]+" "+group.posZ[0]);
  }
  @Override
  public void dispose() {
    group.dispose();
    playerCenter.dispose();
  }
}
