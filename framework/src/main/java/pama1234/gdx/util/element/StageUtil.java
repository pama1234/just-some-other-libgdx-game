package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageUtil extends Stage{
  public StageUtil() {}
  public StageUtil(Viewport viewport) {
    super(viewport);
  }
  public StageUtil(Viewport viewport,Batch batch) {
    super(viewport,batch);
  }
}