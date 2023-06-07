package pama1234.app.game.server.duel;

public class Config{
  public enum GameMode{
    OnLine,
    OffLine
  }
  public static final int game=0,neat=1;
  public int mode;
  public GameMode gameMode;
  public Config init(boolean isAndroid) {
    mode=isAndroid?game:neat;
    gameMode=GameMode.OffLine;
    return this;
  }
}
