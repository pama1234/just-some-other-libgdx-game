package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class LauncherLwjglServer{
  public static void main(String[] args) {
    createApplication();
  }
  public static Lwjgl3Application createApplication() {
    return new Lwjgl3Application(null,getDefaultConfiguration());
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration configuration=new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("空想世界：啥也没有（服务端）");
    configuration.useVsync(true);
    //// Limits FPS to the refresh rate of the currently active monitor.
    configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
    //// useful for testing performance, but can also be very stressful to some hardware.
    //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
    configuration.setWindowedMode(640,480);
    // configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
    // configuration.setWindowIcon("libgdx128.png","libgdx64.png","libgdx32.png","libgdx16.png");
    configuration.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");
    return configuration;
  }
}