package pama1234.server.game.life.particle.core;

import pama1234.util.wrapper.Center;

public class BoxExtension{
  public boolean spawnBox=false;

  public int maxBoxCount=16;
  public int boxSize=32;
  public int boxLifeTime=60*10;

  public Center<Box> boxCenter=new Center<>();;
}
