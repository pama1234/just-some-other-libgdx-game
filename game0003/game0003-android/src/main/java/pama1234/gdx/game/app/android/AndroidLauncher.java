package pama1234.gdx.game.app.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.gdx.Pama;
import pama1234.gdx.android.AndroidMobileUtil;
import pama1234.gdx.launcher.MainApp;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    {
      Pama.mobile=new AndroidMobileUtil(this);
    }
    AndroidApplicationConfiguration config=new AndroidApplicationConfiguration();
    // config.useGyroscope=true;
    // config.useAccelerometer=true;
    // config.useCompass=true;
    initialize(new MainApp(),config);
  }
}