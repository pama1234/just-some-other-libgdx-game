package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaIntItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerType;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Stone;

public class World0001Generator{
  public static MetaBlockCenter0001 createBlockC(World0001 in) {
    MetaBlockCenter0001 metaBlocks=new MetaBlockCenter0001(in);
    metaBlocks.list.add(metaBlocks.air=new MetaBlock(metaBlocks,"air",metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.dirt=new Dirt(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.stone=new Stone(metaBlocks,metaBlocks.id()));
    return metaBlocks;
  }
  public static MetaItemCenter0001 createItemC(World0001 in) {
    MetaItemCenter0001 metaItems=new MetaItemCenter0001(in);
    metaItems.list.add(metaItems.inventoryConfig=new MetaIntItem(metaItems,"empty",metaItems.id()) {
      @Override
      public void init() {
        tiles=new TextureRegion[2];
        tiles[0]=ImageAsset.items[0][0];
        tiles[1]=ImageAsset.items[0][1];
      }
    });
    metaItems.list.add(metaItems.dirt=new MetaIntItem(metaItems,"dirt",metaItems.id()) {
      @Override
      public void init() {
        blockType=in.metaBlocks.dirt;
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.items[0][2];
      }
    });
    metaItems.list.add(metaItems.stone=new MetaIntItem(metaItems,"stone",metaItems.id()) {
      @Override
      public void init() {
        blockType=in.metaBlocks.stone;
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.items[0][3];
      }
    });
    return metaItems;
  }
  public static MetaCreatureCenter0001 createCreatureC(World0001 in) {
    MetaCreatureCenter0001 metaEntitys=new MetaCreatureCenter0001(in);
    metaEntitys.list.add(metaEntitys.player=new PlayerType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.fly=new FlyType(metaEntitys,metaEntitys.id()));
    return metaEntitys;
  }
}
