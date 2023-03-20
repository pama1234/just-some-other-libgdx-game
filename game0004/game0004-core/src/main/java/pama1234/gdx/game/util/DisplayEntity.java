package pama1234.gdx.game.util;

import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.function.ExecuteF;

public class DisplayEntity implements EntityListener{
  public ExecuteF f;
  public DisplayEntity(ExecuteF f) {
    this.f=f;
  }
  @Override
  public void display() {
    f.execute();
  }
  public interface DisplayWithCam{
    default public void displayCam() {}
  }
}
