package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.util.wrapper.Center;

public class MetaInfoUtil{
  public static class MetaCreatureCenter extends Center<MetaCreature<?>>{
  }
  public static class MetaBlockCenter extends Center<MetaBlock>{
  }
  public static class MetaItemCenter<T extends MetaItem>extends Center<T>{
  }
}
