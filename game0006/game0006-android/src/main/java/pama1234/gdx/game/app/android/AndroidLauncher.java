package pama1234.gdx.game.app.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.PamaServer;
import pama1234.gdx.Pama;
import pama1234.gdx.android.AndroidMobileUtil;
import pama1234.gdx.launcher.MainApp;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{
  static {
    Pama.dex=PamaServer.dex=new AndroidDexUtil();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    {
      Pama.mobile=new AndroidMobileUtil(this);
    }
    AndroidApplicationConfiguration configuration=new AndroidApplicationConfiguration();
    configuration.useImmersiveMode=false;
    initialize(new MainApp(),configuration);
  }
}