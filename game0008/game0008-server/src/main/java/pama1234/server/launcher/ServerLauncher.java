package pama1234.server.launcher;

import pama1234.server.app.DedicatedServer;

public class ServerLauncher{
  public static void main(String[] args) {
    new DedicatedServer().run();
  }
}