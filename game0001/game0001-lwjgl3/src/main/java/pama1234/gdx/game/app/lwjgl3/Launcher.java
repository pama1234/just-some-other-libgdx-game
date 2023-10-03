package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;

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
    MainApp app=new MainApp();
    return new Lwjgl3Application(app,getDefaultConfiguration(app));
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration(MainApp app) {
    Lwjgl3ApplicationConfiguration configuration=new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("Loading");
    configuration.useVsync(true);
    configuration.setForegroundFPS(60);
    // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    configuration.setWindowedMode(640,480);
    configuration.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");

    configuration.setWindowListener(new Lwjgl3WindowListener() {
      @Override
      public void created(Lwjgl3Window window) {}
      @Override
      public void iconified(boolean isIconified) {}
      @Override
      public void maximized(boolean isMaximized) {}
      @Override
      public void focusLost() {
        app.focusLost();
      }
      @Override
      public void focusGained() {
        app.focusGained();
      }
      @Override
      public boolean closeRequested() {
        return true;
      }
      @Override
      public void filesDropped(String[] files) {}
      @Override
      public void refreshRequested() {}
    });
    return configuration;
  }
}