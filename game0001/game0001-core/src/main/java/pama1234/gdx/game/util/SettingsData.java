package pama1234.gdx.game.util;

import pama1234.gdx.util.cam.CameraController2D;

public class SettingsData{
  public boolean showEarth=true;
  public boolean debugInfo,debugGraphics;
  public boolean mute;
  public float volume=1;
  public boolean zoomButton;
  public boolean useGyroscope,useAccelerometer;
  public float gyroscopeSensitivity=1,accelerometerSensitivity=1;
  public float gConst=9.81f;
  // public boolean useCompass;
  public boolean overridePlatform;
  public boolean isAndroid;
  public boolean showLog;
  public boolean printLog;
  public boolean ctrlButton;
  public String langType;
  public int pixelPerfectGlobal=CameraController2D.SMOOTH;
  public int pixelPerfectIngame;
}