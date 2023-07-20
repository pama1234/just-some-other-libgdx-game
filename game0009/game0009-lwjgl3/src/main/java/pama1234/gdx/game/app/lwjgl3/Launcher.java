package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import hhs.game.diffjourney.MainGame;

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
    return new Lwjgl3Application(new MainGame(),getDefaultConfiguration());
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration configuration=new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("异星征途");
    configuration.useVsync(true);
    configuration.setForegroundFPS(60);
    // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    configuration.setWindowedMode(1280,720);
    configuration.setWindowIcon("icon0002/icon128.png","icon0002/icon64.png","icon0002/icon32.png","icon0002/icon16.png");
    return configuration;
  }
}