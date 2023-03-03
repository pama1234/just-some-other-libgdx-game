package pama1234.gdx.game.app.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pama1234.gdx.launcher.MainApp;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication{
	static {
		// system.loadLibrary("openxr_loader");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration configuration=new AndroidApplicationConfiguration();
		// configuration.useGyroscope=true;
		MainApp.type=MainApp.pico;
		initialize(new MainApp(),configuration);
		// initialize(new Game(), configuration);
	}
}