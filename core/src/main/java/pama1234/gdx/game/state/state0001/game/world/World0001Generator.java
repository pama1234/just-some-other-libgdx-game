package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerType;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Stone;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeBranch;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLeaf;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLog;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Workbench;

public class World0001Generator{
  public static MetaBlockCenter0001 createBlockC(World0001 in) {
    MetaBlockCenter0001 metaBlocks=new MetaBlockCenter0001(in);
    metaBlocks.list.add(metaBlocks.air=new MetaBlock(metaBlocks,"air",metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.dirt=new Dirt(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.stone=new Stone(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.log=new TreeLog(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.branch=new TreeBranch(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.leaf=new TreeLeaf(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.workbench=new Workbench(metaBlocks,metaBlocks.id()));
    return metaBlocks;
  }
  public static MetaItemCenter0001 createItemC(World0001 pw) {
    MetaItemCenter0001 metaItems=new MetaItemCenter0001(pw);
    metaItems.list.add(metaItems.inventoryConfig=new MetaItem(metaItems,"empty",metaItems.id(),in-> {
      in.tiles=new TextureRegion[2];
      in.tiles[0]=ImageAsset.items[0][0];
      in.tiles[1]=ImageAsset.items[0][1];
    }));
    metaItems.list.add(metaItems.dirt=new MetaItem(metaItems,"dirt",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.dirt;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[0][2];
    }));
    metaItems.list.add(metaItems.stone=new MetaItem(metaItems,"stone",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.stone;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[0][3];
    }));
    metaItems.list.add(metaItems.log=new MetaItem(metaItems,"log",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.log;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][0];
    }));
    metaItems.list.add(metaItems.branch=new MetaItem(metaItems,"branch",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.branch;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][1];
    }));
    metaItems.list.add(metaItems.leaf=new MetaItem(metaItems,"leaf",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.leaf;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][2];
    }));
    metaItems.list.add(metaItems.workbench=new MetaItem(metaItems,"workbench",metaItems.id(),in-> {
      in.blockType=pw.metaBlocks.workbench;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][0];
    }));
    return metaItems;
  }
  public static MetaCreatureCenter0001 createCreatureC(World0001 in) {
    MetaCreatureCenter0001 metaEntitys=new MetaCreatureCenter0001(in);
    metaEntitys.list.add(metaEntitys.player=new PlayerType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.droppedItem=new DroppedItemType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.fly=new FlyType(metaEntitys,metaEntitys.id()));
    return metaEntitys;
  }
}
