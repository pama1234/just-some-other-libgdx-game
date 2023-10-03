package pama1234.gdx.util.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.android.AndroidTouchUtil;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 包含一些未被加入至{@link UtilScreen2D}的功能
 * </p>
 * 定期移动到{@link UtilScreen2D}或其上级{@link UtilScreenCore}
 */
public abstract class ScreenCore2D extends UtilScreen2D{
  @Deprecated
  public ScreenCore2D p=this;
  /**
   * only use on Android
   */
  public TextField hideKeyboardTextField;
  public int hideKeyboard;//TODO fix this by remove

  public AndroidTouchUtil androidTouch;

  public SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
  public FileHandle rootPath;

  public boolean drawCursorWhenGrab;
  public int drawCursorPressedColor=255,drawCursorReleasedColor;
  @Override
  public void init() {
    // super.init();
    androidTouch=new AndroidTouchUtil(this,this::touchButtonToRight);
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
        androidTouch.update();
      }
      @Override
      public void touchStarted(TouchInfo info) {
        androidTouch.touchStarted(info);
      }
      @Override
      public void frameResized(int w,int h) {
        androidTouch.updateAndroidTouchHoldToRightButtonMagCache();

      }
    });
  }
  @Override
  public void camOverlay() {
    if(drawCursorWhenGrab) {
      if(grabCursor) {
        beginBlend();
        drawCursor(drawCursorPressedColor,drawCursorReleasedColor);
        endBlend();
      }
    }
  }
  public void keyboardHidden(TextField textField) {}
  public void touchButtonToRight(TouchInfo info) {}
  //---------------------------------------------------------------------------
  public String timeString() {
    return dateFormat.format(new Date());
  }
}
