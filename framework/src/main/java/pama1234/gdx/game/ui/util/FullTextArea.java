package pama1234.gdx.game.ui.util;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.PathPoint;

public class FullTextArea<T extends ScreenCore2D>extends PointEntity<T,PathPoint>{
  public TextArea data;
  public FullTextArea(T p,PathPoint in,TextArea data) {
    super(p,in);
    this.data=data;
  }
  public void addToScreen() {
    p.screenStage.addActor(data);
  }
  public void addToCam() {
    p.camStage.addActor(data);
  }
  public void removeFormScreen() {
    p.screenStage.getRoot().removeActor(data);
  }
  public void removeFormCam() {
    p.camStage.getRoot().removeActor(data);
  }
}
