package pama1234.gdx.game.app.android;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidInput;
import com.badlogic.gdx.backends.android.DefaultAndroidInput;

import pama1234.gdx.game.cgj.life.particle.element.Welcome;
import pama1234.gdx.launcher.MainApp;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration config=new AndroidApplicationConfiguration();
    config.useAccelerometer=false;
    config.useCompass=false;
    config.useImmersiveMode=true;
    MainApp.type=MainApp.taptap;
    Welcome.title="粒子生命";
    initialize(new MainApp(),config);
  }
  @Override
  public AndroidInput createInput(Application activity,Context context,Object view,AndroidApplicationConfiguration config) {
    return new DefaultAndroidInput(this,this,graphics.getView(),config) {
      //			@Override
      //			public void registerSensorListeners() {
      //			}
      @Override
      public void onResume() {
        //				super.onResume();
      }
      @Override
      public void onDreamingStarted() {
        //				super.onDreamingStarted();
      }
    };
  }
}