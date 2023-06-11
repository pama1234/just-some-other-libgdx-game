package pama1234.gdx.game.duel.util.input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

public class AndroidCtrl extends Entity<Duel>{
  public float maxDist;
  public float magCache;
  public float dxCache,dyCache;
  public TouchInfo moveCtrl;
  public AndroidCtrl(Duel p) {
    super(p);
  }
  @Override
  public void init() {
    p.buttons=UiGenerator.genButtons_0010(p);
    for(TextButton<?> e:p.buttons) p.centerScreen.add.add(e);
    updateMaxDist();
  }
  /**
   * cam only
   */
  @Override
  public void display() {
    if(moveCtrl!=null) {
      p.doStroke();
      p.stroke(0);
      p.strokeWeight(2);
      p.cross(moveCtrl.sx,moveCtrl.sy,32,32);
      p.line(moveCtrl.x,moveCtrl.y,moveCtrl.sx,moveCtrl.sy);
      p.cross(moveCtrl.x,moveCtrl.y,16,16);
      float deg=UtilMath.deg(UtilMath.atan2(dxCache,dyCache));
      p.arc(moveCtrl.sx,moveCtrl.sy,magCache,45-deg,90);
      p.noStroke();
    }
  }
  @Override
  public void update() {
    if(!p.paused) {
      if(moveCtrl!=null) {
        dxCache=moveCtrl.x-moveCtrl.sx;
        dyCache=moveCtrl.y-moveCtrl.sy;
        p.currentInput.targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
      }
    }
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.osx<p.width/2f) {
      if(moveCtrl==null) moveCtrl=info;
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(moveCtrl==info) {
      moveCtrl=null;
      p.currentInput.dx=0;
      p.currentInput.dy=0;
      magCache=0;
    }
  }
  @Override
  public void frameResized(int w,int h) {
    updateMaxDist();
  }
  public void updateMaxDist() {
    maxDist=p.u;
  }
}
