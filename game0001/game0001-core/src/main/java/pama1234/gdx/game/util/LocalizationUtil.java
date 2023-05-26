package pama1234.gdx.game.util;

public class LocalizationUtil{
  // public static final int defaultType=0,taptap=1,pico=2;
  public static final int canUseGyroscope=3,canNotUseGyroscope=4,
    canUseCompass=5,canNotUseCompass=6,
    canUseAccelerometer=7,canNotUseAccelerometer=8,
    needRestart=9,
    thisIsIpAddr=10;
  public class LocalizationData{
    public String canUseGyroscope,canNotUseGyroscope,
      canUseCompass,canNotUseCompass,
      canUseAccelerometer,canNotUseAccelerometer,
      needRestart,
      thisIsIpAddr;
  }
}
