package pama1234.gdx.game.state.state0001.game.world.world0001;

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
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Door;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.LightOre;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.NullBlock;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Sapling;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Stone;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Torch;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeBranch;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLeaf;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLog;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.WoodPlank;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.WoodPlatform;
import pama1234.gdx.game.state.state0001.game.region.block.block0002.ColorBlock;
import pama1234.gdx.game.state.state0001.game.region.block.block0002.LightBlock;
import pama1234.gdx.game.state.state0001.game.region.block.block0002.WorldRoot;
import pama1234.gdx.game.state.state0001.game.region.block.workstation.Chest;
import pama1234.gdx.game.state.state0001.game.region.block.workstation.Furnace;
import pama1234.gdx.game.state.state0001.game.region.block.workstation.Workbench;

public class WorldType0001BaseGenerator{
  public static MetaBlockCenter0001<WorldType0001Base<?>> createBlockC(WorldType0001Base<?> in) {
    MetaBlockCenter0001<WorldType0001Base<?>> metaBlocks=new MetaBlockCenter0001<>(in);
    metaBlocks.list.add(metaBlocks.air=new MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>(metaBlocks,"air",metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.dirt=new Dirt(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.stone=new Stone(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.log=new TreeLog(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.branch=new TreeBranch(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.leaf=new TreeLeaf(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.workbench=new Workbench(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.sapling=new Sapling(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.torch=new Torch(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.woodPlank=new WoodPlank(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.woodPlatform=new WoodPlatform(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.furnace=new Furnace(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.doorClosed=new Door(metaBlocks,metaBlocks.id(),true));
    metaBlocks.list.add(metaBlocks.chest=new Chest(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.lightOre=new LightOre(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.nullBlock=new NullBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.colorBlock=new ColorBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.lightBlock=new LightBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.doorClosed=new Door(metaBlocks,metaBlocks.id(),false));
    metaBlocks.list.add(metaBlocks.worldRoot=new WorldRoot(metaBlocks,metaBlocks.id()));
    return metaBlocks;
  }
  public static <M extends WorldType0001Base<?>> MetaItemCenter0001<M> createItemC(M pwt) {
    MetaItemCenter0001<M> metaItems=new MetaItemCenter0001<>(pwt);
    metaItems.list.add(metaItems.dirt=new MetaItem(metaItems,"dirt",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.dirt;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[0][2];
    }));
    metaItems.list.add(metaItems.stone=new MetaItem(metaItems,"stone",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.stone;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[0][3];
    }));
    metaItems.list.add(metaItems.log=new MetaItem(metaItems,"log",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.log;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][0];
    }));
    metaItems.list.add(metaItems.branch=new MetaItem(metaItems,"branch",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.branch;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][1];
    }));
    metaItems.list.add(metaItems.leaf=new MetaItem(metaItems,"leaf",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.leaf;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][2];
    }));
    metaItems.list.add(metaItems.workbench=new MetaItem(metaItems,"workbench",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.workbench;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][0];
    }));
    metaItems.list.add(metaItems.stonePickaxe=new MetaItem(metaItems,"stone-pickaxe",metaItems.id(),in-> {
      in.toolType=MetaItem.pickaxe;
      in.speed=2;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][1];
    }));
    metaItems.list.add(metaItems.stoneAxe=new MetaItem(metaItems,"stone-axe",metaItems.id(),in-> {
      in.toolType=MetaItem.axe;
      in.speed=2;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][2];
    }));
    metaItems.list.add(metaItems.stoneChisel=new MetaItem(metaItems,"stone-chisel",metaItems.id(),in-> {
      in.toolType=MetaItem.chisel;
      in.speed=2;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][3];
    }));
    metaItems.list.add(metaItems.stoneSword=new MetaItem(metaItems,"stone-sword",metaItems.id(),in-> {
      in.weaponType=MetaItem.hack;
      in.speed=2;
      in.damage=0.04f;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[2][4];
    }));
    metaItems.list.add(metaItems.sapling=new MetaItem(metaItems,"sapling",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.sapling;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[1][3];
    }));
    metaItems.list.add(metaItems.torch=new MetaItem(metaItems,"torch",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.torch;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[6][0];
    }));
    metaItems.list.add(metaItems.woodPlank=new MetaItem(metaItems,"wood-plank",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.woodPlank;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[3][0];
    }));
    metaItems.list.add(metaItems.woodPlatform=new MetaItem(metaItems,"wood-platform",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.woodPlatform;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[3][2];
    }));
    metaItems.list.add(metaItems.furnace=new MetaItem(metaItems,"furnace",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.furnace;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[6][1];
    }));
    metaItems.list.add(metaItems.door=new MetaItem(metaItems,"door",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.doorClosed;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[3][7];
    }));
    metaItems.list.add(metaItems.chest=new MetaItem(metaItems,"chest",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.chest;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[3][3];
    }));
    metaItems.list.add(metaItems.lightOre=new MetaItem(metaItems,"light-ore",metaItems.id(),in-> {
      // in.blockType=pw.type.metaBlocks.chest;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][0];
    }));
    metaItems.list.add(metaItems.lightIngot=new MetaItem(metaItems,"light-ingot",metaItems.id(),in-> {
      // in.blockType=pw.type.metaBlocks.chest;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][1];
    }));
    metaItems.list.add(metaItems.lightPickaxe=new MetaItem(metaItems,"light-pickaxe",metaItems.id(),in-> {
      in.toolType=MetaItem.pickaxe;
      in.speed=4;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][2];
    }));
    metaItems.list.add(metaItems.lightAxe=new MetaItem(metaItems,"light-axe",metaItems.id(),in-> {
      in.toolType=MetaItem.axe;
      in.speed=4;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][3];
    }));
    metaItems.list.add(metaItems.lightChisel=new MetaItem(metaItems,"light-chisel",metaItems.id(),in-> {
      in.toolType=MetaItem.chisel;
      in.speed=4;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][4];
    }));
    metaItems.list.add(metaItems.lightSword=new MetaItem(metaItems,"light-sword",metaItems.id(),in-> {
      in.weaponType=MetaItem.hack;
      in.speed=4;
      in.damage=0.08f;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[7][5];
    }));
    metaItems.list.add(metaItems.colorBlock=new MetaItem(metaItems,"color-block",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.colorBlock;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[8][0];
    }));
    metaItems.list.add(metaItems.lightBlock=new MetaItem(metaItems,"light-block",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.lightBlock;
      in.tiles=new TextureRegion[1];
      in.tiles[0]=ImageAsset.items[8][1];
    }));
    metaItems.list.add(metaItems.worldRoot=new MetaItem(metaItems,"world-root",metaItems.id(),in-> {
      in.blockType=pwt.metaBlocks.worldRoot;
      in.tiles=new TextureRegion[2];
      in.tiles[0]=ImageAsset.items[8][2];
      in.tiles[1]=ImageAsset.items[8][3];
    }));
    return metaItems;
  }
  public static <M extends WorldType0001Base<?>> MetaCreatureCenter0001<M> createCreatureC(M in) {
    MetaCreatureCenter0001<M> metaEntitys=new MetaCreatureCenter0001<>(in);
    metaEntitys.list.add(metaEntitys.player=new PlayerType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.droppedItem=new DroppedItemType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.fly=new FlyType(metaEntitys,metaEntitys.id()));
    return metaEntitys;
  }
}