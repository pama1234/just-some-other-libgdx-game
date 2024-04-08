package pama1234.gdx.game.app.lwjgl3;

import pama1234.gdx.launcher.MainApp;
import pama1234.util.gdx.lwjgl.UtilLauncher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/** Launches the desktop (LWJGL3) application. */
public class Launcher extends UtilLauncher{
  public static void main(String[] args) {
    // try {
    Lwjgl3Application app=createApplication();
    // }catch(Exception e) {
    // 	ExceptionState.data.add(e);//TODO
    // }
  }
  public static Lwjgl3Application createApplication() {
    MainApp app=new MainApp();
    return new Lwjgl3Application(app,getConfiguration(app));
  }
  public static Lwjgl3ApplicationConfiguration getConfiguration(MainApp app) {
    Lwjgl3ApplicationConfiguration conf=getDefaultConfiguration(app);
    conf.setTitle("pama1234");
    conf.useVsync(true);
    conf.setForegroundFPS(60);
    // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    conf.setWindowedMode(1920,1080);
//    conf.setWindowedMode(640,480);
    conf.setWindowIcon("icon0002/icon128.png","icon0002/icon64.png","icon0002/icon32.png","icon0002/icon16.png");
    return conf;
  }
}