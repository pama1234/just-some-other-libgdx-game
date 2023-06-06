package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import pama1234.gdx.launcher.MainApp;

/** Launches the desktop (LWJGL3) application. */
public class Launcher{
  public static void main(String[] args) {
    // try {
    createApplication();
    // }catch(Exception e) {
    // 	ExceptionState.data.add(e);//TODO
    // }
  }
  public static Lwjgl3Application createApplication() {
    return new Lwjgl3Application(new MainApp(),getDefaultConfiguration());
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration configuration=new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("几何决斗");
    configuration.useVsync(true);
    configuration.setForegroundFPS(60);
    // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    configuration.setWindowedMode(640,480);
    configuration.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");
    return configuration;
  }
}