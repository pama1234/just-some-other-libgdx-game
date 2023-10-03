package pama1234.gdx.game.sandbox.platformer.world.world0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.io.MetaPropertiesCenter;
import pama1234.gdx.game.sandbox.platformer.player.Player.PlayerType;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Dirt;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Door;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.LightOre;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.NullBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Sapling;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Stone;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Torch;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeBranch;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeLeaf;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeLog;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.WoodPlank;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.WoodPlatform;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.ColorBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.LightBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.WorldDebugger;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Chest;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Furnace;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Workbench;

public class WorldMetaInfoUtil{

  public static MetaBlockCenter0001<WorldType0001Base<?>> loadBlockC(WorldType0001Base<?> in) {
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
    metaBlocks.list.add(metaBlocks.worldDebugger=new WorldDebugger(metaBlocks,metaBlocks.id()));
    return metaBlocks;
  }
  public static void saveBlockC(MetaBlockCenter0001<?> metaBlocks) {
    for(var e:metaBlocks.list) {
      System.out.println(e.name+": ");
      String ts=MetaPropertiesCenter.YAML.save(e);
      System.out.println(ts);
      System.out.println();
    }
  }

  public static <M extends WorldType0001Base<?>> MetaItemCenter0001<M> loadItemC(M pwt) {
    MetaItemCenter0001<M> metaItems=new MetaItemCenter0001<>(pwt);
    metaItems.list.add(metaItems.dirt=new MetaItem(metaItems,"dirt",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.dirt;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[0][2];
    }));
    metaItems.list.add(metaItems.stone=new MetaItem(metaItems,"stone",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.stone;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[0][3];
    }));
    metaItems.list.add(metaItems.log=new MetaItem(metaItems,"log",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.log;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[1][0];
    }));
    metaItems.list.add(metaItems.branch=new MetaItem(metaItems,"branch",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.branch;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[1][1];
    }));
    metaItems.list.add(metaItems.leaf=new MetaItem(metaItems,"leaf",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.leaf;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[1][2];
    }));
    metaItems.list.add(metaItems.workbench=new MetaItem(metaItems,"workbench",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.workbench;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[2][0];
    }));
    metaItems.list.add(metaItems.stonePickaxe=new MetaItem(metaItems,"stone-pickaxe",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.pickaxe;
      in.attr.genericSpeed=2;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[2][1];
    }));
    metaItems.list.add(metaItems.stoneAxe=new MetaItem(metaItems,"stone-axe",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.axe;
      in.attr.genericSpeed=2;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[2][2];
    }));
    metaItems.list.add(metaItems.stoneChisel=new MetaItem(metaItems,"stone-chisel",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.chisel;
      in.attr.genericSpeed=2;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[2][3];
    }));
    metaItems.list.add(metaItems.stoneSword=new MetaItem(metaItems,"stone-sword",metaItems.id(),in-> {
      in.attr.weaponType=MetaItem.hack;
      in.attr.genericSpeed=2;
      in.attr.damage=0.04f;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[2][4];
    }));
    metaItems.list.add(metaItems.sapling=new MetaItem(metaItems,"sapling",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.sapling;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[1][3];
    }));
    metaItems.list.add(metaItems.torch=new MetaItem(metaItems,"torch",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.torch;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[6][0];
    }));
    metaItems.list.add(metaItems.woodPlank=new MetaItem(metaItems,"wood-plank",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.woodPlank;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[3][0];
    }));
    metaItems.list.add(metaItems.woodPlatform=new MetaItem(metaItems,"wood-platform",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.woodPlatform;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[3][2];
    }));
    metaItems.list.add(metaItems.furnace=new MetaItem(metaItems,"furnace",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.furnace;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[6][1];
    }));
    metaItems.list.add(metaItems.door=new MetaItem(metaItems,"door",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.doorClosed;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[3][7];
    }));
    metaItems.list.add(metaItems.chest=new MetaItem(metaItems,"chest",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.chest;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[3][3];
    }));
    metaItems.list.add(metaItems.lightOre=new MetaItem(metaItems,"light-ore",metaItems.id(),in-> {
      // in.rttr.blockType=pw.type.metaBlocks.chest;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][0];
    }));
    metaItems.list.add(metaItems.lightIngot=new MetaItem(metaItems,"light-ingot",metaItems.id(),in-> {
      // in.rttr.blockType=pw.type.metaBlocks.chest;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][1];
    }));
    metaItems.list.add(metaItems.lightPickaxe=new MetaItem(metaItems,"light-pickaxe",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.pickaxe;
      in.attr.genericSpeed=4;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][2];
    }));
    metaItems.list.add(metaItems.lightAxe=new MetaItem(metaItems,"light-axe",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.axe;
      in.attr.genericSpeed=4;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][3];
    }));
    metaItems.list.add(metaItems.lightChisel=new MetaItem(metaItems,"light-chisel",metaItems.id(),in-> {
      in.attr.toolType=MetaItem.chisel;
      in.attr.genericSpeed=4;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][4];
    }));
    metaItems.list.add(metaItems.lightSword=new MetaItem(metaItems,"light-sword",metaItems.id(),in-> {
      in.attr.weaponType=MetaItem.hack;
      in.attr.genericSpeed=4;
      in.attr.damage=0.08f;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[7][5];
    }));
    metaItems.list.add(metaItems.colorBlock=new MetaItem(metaItems,"color-block",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.colorBlock;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[8][0];
    }));
    metaItems.list.add(metaItems.lightBlock=new MetaItem(metaItems,"light-block",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.lightBlock;
      in.rttr.tiles=new TextureRegion[1];
      in.rttr.tiles[0]=ImageAsset.items[8][1];
    }));
    metaItems.list.add(metaItems.worldRoot=new MetaItem(metaItems,"world-root",metaItems.id(),in-> {
      in.rttr.blockType=pwt.metaBlocks.worldDebugger;
      in.rttr.tiles=new TextureRegion[2];
      in.rttr.tiles[0]=ImageAsset.items[8][2];
      in.rttr.tiles[1]=ImageAsset.items[8][3];
    }));
    //temp
    // MetaPropertiesCenter.saveItem(WorldKryoUtil.kryo,Gdx.files.local("data/test/dirt.mpd"),metaItems.dirt);
    return metaItems;
  }
  public static void saveItemC(MetaItemCenter0001<?> metaItems) {
    for(var e:metaItems.list) {
      System.out.println(e.name+": ");
      String ts=MetaPropertiesCenter.YAML.save(e);
      System.out.println(ts);
      System.out.println();
    }
  }

  public static <M extends WorldType0001Base<?>> MetaCreatureCenter0001<M> loadCreatureC(M in) {
    MetaCreatureCenter0001<M> metaEntitys=new MetaCreatureCenter0001<>(in);
    metaEntitys.list.add(metaEntitys.player=new PlayerType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.droppedItem=new DroppedItemType(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.fly=new FlyType(metaEntitys,metaEntitys.id()));
    return metaEntitys;
  }
}