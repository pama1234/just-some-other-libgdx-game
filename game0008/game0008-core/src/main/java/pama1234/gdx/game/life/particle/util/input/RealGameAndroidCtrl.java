package pama1234.gdx.game.life.particle.util.input;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.ui.generator.UiGenerator;
import pama1234.gdx.util.android.AndroidCtrlScreenBase;
import pama1234.gdx.util.app.UtilScreenColor;

public class RealGameAndroidCtrl extends AndroidCtrlScreenBase<Screen0045>{
  public RealGameAndroidCtrl(Screen0045 p) {
    super(p,UtilScreenColor.color(255));
    scale=2;
    updateMaxDist();
    init();
  }
  @Override
  public void init() {
    buttons=UiGenerator.genButtons_0003(p);
    updateMaxDist();
    // for(TextButton<?> e:buttons) p.centerScreen.add.add(e);
  }
  // @Override
  // public <X extends UtilScreen> TextButton<?>[] genButtons_0001(X p) {
  //   return UiGenerator.genButtons_0003((ScreenCore2D)p);
  // }
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