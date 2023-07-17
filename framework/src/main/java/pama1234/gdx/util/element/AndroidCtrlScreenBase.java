package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.UtilMath;

public abstract class AndroidCtrlScreenBase<T extends UtilScreen>extends AndroidCtrlBase<T>{
  public AndroidCtrlScreenBase(T p) {
    super(p);
  }
  public AndroidCtrlScreenBase(T p,Color strokeColor) {
    super(p,strokeColor);
  }
  @Override
  public void update() {
    if(!paused()) {
      if(moveCtrl!=null) {
        dxCache=moveCtrl.ox-moveCtrl.osx;
        dyCache=moveCtrl.oy-moveCtrl.osy;
        targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
      }
    }
  }
  @Override
  public void display() {
    if(moveCtrl!=null) {
      p.doStroke();
      p.stroke(strokeColor);
      p.strokeWeight(2*scale);
      p.cross(moveCtrl.osx,moveCtrl.osy,32*scale,32*scale);
      p.line(moveCtrl.ox,moveCtrl.oy,moveCtrl.osx,moveCtrl.osy);
      p.cross(moveCtrl.ox,moveCtrl.oy,16*scale,16*scale);
      float deg=UtilMath.deg(UtilMath.atan2(dxCache,dyCache));
      p.arc(moveCtrl.osx,moveCtrl.osy,magCache,45-deg,90);
      p.noStroke();
    }
  }
}
