package pama1234.gdx.game.cide.util;

import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.math.physics.PathPoint;

public class ToolBar extends CideEntity{
  {
    name="工具栏";
  }
  public ToolBar(ScreenCide2D p,float x,float y) {
    super(p,new PathPoint(x,y));
  }
  @Override
  public void update() {
    super.update();
  }
  @Override
  public void innerDisplay() {}
}
