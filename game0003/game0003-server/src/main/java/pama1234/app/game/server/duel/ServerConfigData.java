package pama1234.app.game.server.duel;

import pama1234.app.game.server.duel.util.theme.ServerThemeData;
import pama1234.math.Tools;
import pama1234.util.net.NetAddressInfo;

public class ServerConfigData{
  public static final ThemeType[] themeTypeArray=ThemeType.values();
  public static final int game=0,neat=1;
  public int mode;
  public GameMode gameMode;
  public ThemeType themeType;
  public int orientation;
  public boolean firstPlay;
  public ServerThemeData theme;
  public ServerAttr server;
  public boolean debug;
  public void init() {
    mode=game;
    gameMode=GameMode.OffLine;
    themeType=ThemeType.Light;
    server=new ServerAttr();
    firstPlay=true;
  }
  public ThemeType nextTheme(ThemeType themeType) {
    return themeTypeArray[Tools.moveInRange(themeType.ordinal()+1,0,themeTypeArray.length)];
  }
  public enum GameMode{
    OnLine,
    OffLine;
    public GameMode next() {
      if(this==OffLine) return OnLine;
      else return OffLine;
    }
    @Override
    public String toString() {
      switch(this) {
        case OffLine:
          return "单机";
        case OnLine:
          return "联机";
        default:
          return "未知";
      }
    }
  }
  public enum ThemeType{
    Light,Dark,Custom;
    @Override
    public String toString() {
      switch(this) {
        case Light:
          return "亮色";
        case Dark:
          return "暗色";
        case Custom:
          return "自定义";
        default:
          return "未知";
      }
    }
  }
  public static class ServerAttr extends NetAddressInfo{
    @Deprecated
    public ServerAttr() {}//kryo only
    public ServerAttr(String ip,int port) {
      super(ip,port);
    }
  }
}
