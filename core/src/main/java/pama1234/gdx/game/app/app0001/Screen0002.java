package pama1234.gdx.game.app.app0001;

import pama1234.game.app.server.particle.with2d.CellGroup2D;
import pama1234.game.app.server.particle.with2d.CellGroupGenerator2D;
import pama1234.gdx.util.app.UtilScreen2D;

/**
 * 2D 粒子系统 警告：未维护
 */
@Deprecated
public class Screen0002 extends UtilScreen2D{
  CellGroup2D group;
  // ServerPlayerCenter<Player2D> playerCenter;
  @Override
  public void setup() {
    CellGroupGenerator2D gen=new CellGroupGenerator2D(0,0);
    group=gen.GenerateFromMiniCore();
    // playerCenter=new ServerPlayerCenter<Player2D>();
    noStroke();
  }
  @Override
  public void update() {
    group.update();
    // playerCenter.update();
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {
    int circleSeg=circleSeg(CellGroup2D.SIZE*cam.scale.pos*(1/cam.frameScale));
    for(int i=0;i<group.size;i++) {
      fillHex(group.color(i));
      circle(group.x(i),group.y(i),CellGroup2D.SIZE,circleSeg);
    }
    // System.out.println(cam.scale.pos);
    // System.out.println();
  }
  @Override
  public void frameResized() {}
}