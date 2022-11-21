package pama1234.gdx.game.app.headless;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

import pama1234.gdx.launcher.MainApp;

/** Launches the headless application. Can be converted into a utilities project or a server application. */
public class HeadlessLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Application createApplication() {
		// Note: you can use a custom ApplicationListener implementation for the headless project instead of MainApp.
		return new HeadlessApplication(new MainApp(), getDefaultConfiguration());
	}

	private static HeadlessApplicationConfiguration getDefaultConfiguration() {
		HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
		configuration.updatesPerSecond = -1; // When this value is negative, MainApp#render() is never called.
		//// If the above line doesn't compile, it is probably because the project libGDX version is older.
		//// In that case, uncomment and use the below line.
		//configuration.renderInterval = -1f; // When this value is negative, MainApp#render() is never called.
		return configuration;
	}
}