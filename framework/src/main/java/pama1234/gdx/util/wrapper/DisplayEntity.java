package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.function.ExecuteFunction;

/**
 * 一种包装，使得其中的实体只监听绘制方法
 * </p>
 * TODO 很丑，得改
 */
public class DisplayEntity implements EntityListener{
  public ExecuteFunction f;
  public DisplayEntity(ExecuteFunction f) {
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
