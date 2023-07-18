package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.element.AndroidCtrlBase;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class DuelAndroidCtrl extends AndroidCtrlBase<Duel>{
  public Game pg;
  public DuelAndroidCtrl(Duel p,Game pg) {
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
  @Override
  public <X extends UtilScreen> TextButton<?>[] genButtons_0001(X p) {
    return Tools.concat(super.genButtons_0001(p),new TextButton[] {
      new TextButton<X>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.C);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.C);
      },self->self.text="C ",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*2.5f,()->p.bu-p.pus,false),
    });
  }
}
