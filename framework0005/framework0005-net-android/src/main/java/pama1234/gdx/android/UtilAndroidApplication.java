package pama1234.gdx.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.gdx.Pama;

public class UtilAndroidApplication extends AndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    {
      Pama.mobile=new AndroidMobileUtil(this);
    }
  }
  public static AndroidApplicationConfiguration getDefaultConfiguration() {
    AndroidApplicationConfiguration config=new AndroidApplicationConfiguration();
    //    config.useGyroscope=true;
    //    config.useAccelerometer=true;
    //    config.useCompass=true;
    config.useImmersiveMode=true;
    return config;
  }
}
