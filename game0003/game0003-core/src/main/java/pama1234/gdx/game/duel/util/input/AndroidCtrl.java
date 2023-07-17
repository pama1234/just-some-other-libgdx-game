package pama1234.gdx.game.duel.util.input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.util.element.AndroidCtrlBase;
import pama1234.math.UtilMath;

public class AndroidCtrl extends AndroidCtrlBase<Duel>{
  public Game pg;
  public AndroidCtrl(Duel p,Game pg) {
    super(p,p.theme.stroke);
    this.pg=pg;
  }
  @Override
  public void targetTouchMoved(float dx,float dy,float mag) {
    pg.currentInput.targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
  }
  @Override
  public void clearTargetTouch() {
    pg.currentInput.dx=0;
    pg.currentInput.dy=0;
  }
  @Override
  public boolean paused() {
    return pg.paused;
  }
}
