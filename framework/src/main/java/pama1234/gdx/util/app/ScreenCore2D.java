package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 包含一些未被加入至{@link UtilScreen2D}的功能
 * </p>
 * 定期移动到{@link UtilScreen2D}或其上级{@link UtilScreenCore}
 */
public abstract class ScreenCore2D extends UtilScreen2D{
  /**
   * only use on Android
   */
  public TextField hideKeyboardTextField;
  public int hideKeyboard;//TODO fix this by remove
  //---
  public boolean androidTouchHoldToRightButton;
  public int androidTouchHoldToRightButtonTime=30;
  @Override
  public void init() {
    // super.init();
    center.add.add(new EntityListener() {
      @Override
      public void update() {
        if(hideKeyboard>0) {
          hideKeyboard--;
          if(hideKeyboard==0) {
            keyboardHidden(hideKeyboardTextField);
            if(isAndroid) Gdx.input.setOnscreenKeyboardVisible(false);
          }
        }
        if(androidTouchHoldToRightButton) {
          for(int j=0;j<touchCount;j++) {
            TouchInfo i=touches[j];
            if(i.button==Buttons.LEFT&&frameCount-i.startTime>=androidTouchHoldToRightButtonTime) {
              inputProcessor.touchUp(i.ox,i.oy,i.pointer,i.button);
              inputProcessor.touchDown(i.ox,i.oy,i.pointer,Buttons.RIGHT);
              touchButtonToRight(i);
            }
          }
        }
      }
    });
  }
  public void keyboardHidden(TextField textField) {}
  public void touchButtonToRight(TouchInfo info) {}
  public void centerCamAddAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.add.add(i);
  }
  public void centerCamRemoveAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.remove.add(i);
  }
}
