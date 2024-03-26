package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import pama1234.gdx.MobileUtil.EmptyMobileUtil;
import pama1234.gdx.Pama;
import pama1234.gdx.launcher.MainApp;
import pama1234.util.gdx.lwjgl.UtilLauncher;

/** Launches the desktop (LWJGL3) application. */
public class Launcher extends UtilLauncher{
  public static void main(String[] args) {
    Pama.mobile=new EmptyMobileUtil();
    createApplication();
  }
  public static Lwjgl3Application createApplication() {
    MainApp app=new MainApp();
    return new Lwjgl3Application(app,getConfiguration(app));
  }
  public static Lwjgl3ApplicationConfiguration getConfiguration(MainApp app) {
    Lwjgl3ApplicationConfiguration conf=getDefaultConfiguration(app);
    conf.setTitle("MBTI all in one");
    conf.useVsync(true);
    conf.setForegroundFPS(60);
    // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    conf.setWindowedMode(640,480);
    conf.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");
    return conf;
  }
}