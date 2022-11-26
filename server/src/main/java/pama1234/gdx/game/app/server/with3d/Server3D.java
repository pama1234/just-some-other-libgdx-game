package pama1234.gdx.game.app.server.with3d;

import pama1234.gdx.game.app.server.game.ServerPlayerCenter;
import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;
import pama1234.gdx.util.UtilServer;

public class Server3D extends UtilServer{
  public CellGroup3D group;
  public boolean doUpdate=true;
  public Thread updateCell;
  //---
  ServerPlayerCenter<ServerPlayer3D> playerCenter;
  @Override
  public void init() {
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.GenerateFromMiniCore();
    //---
    updateCell=new Thread("UpdateCell") {
      @Override
      public void run() {
        while(!stop) {
          if(doUpdate) group.update();
          else try {
            sleep(1000);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    updateCell.start();
    //---
    playerCenter=new ServerPlayerCenter<ServerPlayer3D>();
  }
  @Override
  public void update() {
    group.update();
    playerCenter.update();
  }
  @Override
  public void dispose() {
    group.dispose();
    playerCenter.dispose();
  }
}
