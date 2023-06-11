package pama1234.app.game.server.duel;

public class ServerConfigData{
  public enum GameMode{
    OnLine,
    OffLine
  }
  public static final int game=0,neat=1;
  public int mode;
  public GameMode gameMode;
  // public SkinData skin;
  public void init() {
    mode=game;
    gameMode=GameMode.OffLine;
    // skin=new ServerSkinData();
    // skin.init();
  }
}
