package pama1234.gdx.android;

import android.content.pm.ActivityInfo;
import com.badlogic.gdx.backends.android.AndroidApplication;

import pama1234.gdx.MobileUtil;


public class AndroidMobileUtil implements MobileUtil {
  public AndroidApplication p;
  public AndroidMobileUtil(AndroidApplication p) {
    this.p=p;
  }
  @Override
  public void orientation(int type) {
    if(type==MobileUtil.portrait) p.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    else if(type==MobileUtil.landscape) p.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }
  @Override
  public void frameRate(int fps) {

  }
}