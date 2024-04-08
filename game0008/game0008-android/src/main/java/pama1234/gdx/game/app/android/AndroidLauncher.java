package pama1234.gdx.game.app.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.gdx.android.UtilAndroidApplication;
import pama1234.gdx.launcher.MainApp;

/** Launches the Android application. */
public class AndroidLauncher extends UtilAndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration config=getDefaultConfiguration();
    config.r=8;//TODO 性能
    config.g=8;
    config.b=8;
    config.a=8;
    config.useImmersiveMode=true;
    // config.depth=32;
    // config.useGyroscope=true;
    // config.useAccelerometer=true;
    // config.useCompass=true;
    initialize(new MainApp(),config);
  }
}