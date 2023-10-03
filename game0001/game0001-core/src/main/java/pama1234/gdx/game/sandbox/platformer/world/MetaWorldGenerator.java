package pama1234.gdx.game.sandbox.platformer.world;

import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001;
import pama1234.gdx.game.sandbox.platformer.world.world0002.WorldType0002;
import pama1234.gdx.game.state.state0001.Game;

public class MetaWorldGenerator{
  public static MetaWorldCenter0001 createWorldC(Game pg) {
    MetaWorldCenter0001 worlds=new MetaWorldCenter0001(pg);
    worlds.list.add(worlds.world0001=new WorldType0001(worlds,worlds.id()));
    worlds.list.add(worlds.world0002=new WorldType0002(worlds,worlds.id()));
    return worlds;
  }
}