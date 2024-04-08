package pama1234.gdx.game.app.app0002;

import pama1234.game.app.server.server0001.game.particle.jocl.CellGroup3D;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("JOCL 测试")
public class Screen0047 extends ScreenCore2D{
  String out;
  @Override
  public void setup() {
    out=CellGroup3D.doTest();
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    text(out);
  }
  @Override
  public void frameResized() {}
}
