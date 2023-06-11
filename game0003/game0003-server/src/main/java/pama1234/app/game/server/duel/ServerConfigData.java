package pama1234.app.game.server.duel;

import pama1234.app.game.server.duel.util.skin.ServerSkinData;

public class ServerConfigData{
  public enum GameMode{
    OnLine,
    OffLine
  }
  public static final int game=0,neat=1;
  public int mode;
  public GameMode gameMode;
  public ServerSkinData skin;
  public void init() {
    mode=game;
    gameMode=GameMode.OffLine;
  }
}
