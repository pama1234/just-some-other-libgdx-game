package pama1234.gdx.util.android;

import java.util.function.Consumer;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.BooleanArray;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

public class AndroidTouchUtil extends Entity<UtilScreen>{
  public boolean touchHoldToRightButton;
  public BooleanArray touchHoldToRightButtonCache=new BooleanArray();
  public int touchHoldToRightButtonTime=30;
  public float touchHoldToRightButtonMag=4;
  public float touchHoldToRightButtonMagCache;
  public Consumer<TouchInfo> function;
  public AndroidTouchUtil(UtilScreen p,Consumer<TouchInfo> function) {
    super(p);
    this.function=function;
    touchHoldToRightButtonMagCache=touchHoldToRightButtonMag;
  }
  @Override
  public void update() {
    if(touchHoldToRightButton) {
      for(int j=0;j<p.touchCount;j++) {
        TouchInfo i=p.touches[j];
        if(touchHoldToRightButtonCache.get(i.pointer)) {
          boolean flag=UtilMath.dist(i.ox,i.oy,i.osx,i.osy)<touchHoldToRightButtonMagCache;
          // System.out.println(flag);
          touchHoldToRightButtonCache.set(i.pointer,flag);
          if(i.button==Buttons.LEFT&&flag&&p.frameCount-i.startTime>=touchHoldToRightButtonTime) {
            p.inputProcessor.touchUp(i.ox,i.oy,i.pointer,i.button);
            p.inputProcessor.touchDown(i.ox,i.oy,i.pointer,Buttons.RIGHT);
            // p.touchButtonToRight(i);
            function.accept(i);
          }
        }
      }
    }
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(touchHoldToRightButtonCache.size<=info.pointer) {
      touchHoldToRightButtonCache.addAll(new boolean[info.pointer-touchHoldToRightButtonCache.size+1]);
    }
    touchHoldToRightButtonCache.set(info.pointer,true);
  }
  public void updateAndroidTouchHoldToRightButtonMagCache() {
    touchHoldToRightButtonMagCache=touchHoldToRightButtonMag*p.pus;
  }
}
