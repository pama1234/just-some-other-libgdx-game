package pama1234.gdx.game.cgj.util.input;

import pama1234.gdx.game.cgj.app.app0002.Screen0036;
import pama1234.gdx.game.cgj.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.element.AndroidCtrlScreenBase;

public class AndroidCtrl extends AndroidCtrlScreenBase<Screen0036>{
  public float scale;
  public AndroidCtrl(Screen0036 p) {
    super(p);
    scale=2;
  }
  @Override
  public void init() {
    p.textButtons=UiGenerator.genButtons_0003(p);
    for(TextButton<?> e:p.textButtons) p.centerScreen.add.add(e);
    updateMaxDist();
  }
  @Override
  public void targetTouchMoved(float dx,float dy,float mag) {
    p.currentInput.targetTouchMoved(dx,dy,mag);
  }
  @Override
  public void clearTargetTouch() {
    p.currentInput.dx=0;
    p.currentInput.dy=0;
  }
  @Override
  public boolean paused() {
    return p.paused;
  }
}
