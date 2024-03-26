package hhs.game.diffjourney;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.gdx.android.UtilAndroidApplication;

/* Launches the Android application. */
public class AndroidLauncher extends UtilAndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration configuration=getDefaultConfiguration();
    initialize(new MainGame(),configuration);
  }
}
