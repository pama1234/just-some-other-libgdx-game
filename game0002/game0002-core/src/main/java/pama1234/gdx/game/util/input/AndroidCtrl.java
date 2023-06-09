package pama1234.gdx.game.util.input;

import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

public class AndroidCtrl extends Entity<Screen0005>{
  public float maxDist;
  public float magCache;
  public float dxCache,dyCache;
  public TouchInfo moveCtrl;
  public AndroidCtrl(Screen0005 p) {
    super(p);
  }
  @Override
  public void init() {
    p.textButtons=UiGenerator.genButtons_0003(p);
    for(TextButton<?> e:p.textButtons) p.centerScreen.add.add(e);
  }
  /**
   * cam only
   */
  @Override
  public void display() {
    if(moveCtrl!=null) {
      p.doStroke();
      p.stroke(255);
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
    maxDist=p.u;
  }
}
