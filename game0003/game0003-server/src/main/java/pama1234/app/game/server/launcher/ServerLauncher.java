package pama1234.app.game.server.launcher;

import pama1234.app.game.server.duel.DuelServer;

public class ServerLauncher{
  public static void main(String[] args) {
    new DuelServer().run();
  }
}