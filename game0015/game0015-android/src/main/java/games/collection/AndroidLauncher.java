package com.games.collection;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.Handler;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simple.spiderman.SpiderMan;
import java.lang.reflect.InvocationTargetException;

/* Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{

  public static Class<? extends ApplicationListener> game;
  public static boolean landscape=true;
  public static Activity activity;
  public static Handler handle;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity=this;
    handle=new Handler();
    if(landscape) {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }else {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /* You can adjust this configuration to fit your needs */
    AndroidApplicationConfiguration configuration=new AndroidApplicationConfiguration();
    configuration.useImmersiveMode=true;
    try {
      ApplicationListener reflect=game.getConstructor().newInstance();
      initialize(reflect,configuration);
    }catch(NoSuchMethodException e) {
      SpiderMan.show(e);
    }catch(IllegalAccessException e) {
      SpiderMan.show(e);
    }catch(InstantiationException e) {
      SpiderMan.show(e);
    }catch(InvocationTargetException e) {
      SpiderMan.show(e);
    }
  }
}
