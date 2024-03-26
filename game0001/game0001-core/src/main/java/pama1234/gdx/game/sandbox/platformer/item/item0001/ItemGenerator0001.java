package pama1234.gdx.game.sandbox.platformer.item.item0001;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

/** 物品类型第一页 */
public class ItemGenerator0001{

  /** 加载物品类型第一页 */
  public static <M extends WorldType0001Base<?>> void load(M pwt,MetaItemCenter0001<M> metaItems) {
    metaItems.list.add(metaItems.dirt=new MetaItem(metaItems,"dirt",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.stone=new MetaItem(metaItems,"stone",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.log=new MetaItem(metaItems,"log",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.branch=new MetaItem(metaItems,"branch",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.leaf=new MetaItem(metaItems,"leaf",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.workbench=new MetaItem(metaItems,"workbench",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.stonePickaxe=new MetaItem(metaItems,"stone-pickaxe",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.stoneAxe=new MetaItem(metaItems,"stone-axe",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.stoneChisel=new MetaItem(metaItems,"stone-chisel",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.stoneSword=new MetaItem(metaItems,"stone-sword",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.sapling=new MetaItem(metaItems,"sapling",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.torch=new MetaItem(metaItems,"torch",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.woodPlank=new MetaItem(metaItems,"wood-plank",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.woodPlatform=new MetaItem(metaItems,"wood-platform",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.furnace=new MetaItem(metaItems,"furnace",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.door=new MetaItem(metaItems,"door",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.chest=new MetaItem(metaItems,"chest",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightOre=new MetaItem(metaItems,"light-ore",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightIngot=new MetaItem(metaItems,"light-ingot",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightPickaxe=new MetaItem(metaItems,"light-pickaxe",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightAxe=new MetaItem(metaItems,"light-axe",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightChisel=new MetaItem(metaItems,"light-chisel",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightSword=new MetaItem(metaItems,"light-sword",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.colorBlock=new MetaItem(metaItems,"color-block",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.lightBlock=new MetaItem(metaItems,"light-block",metaItems.id(),in-> {}));
    metaItems.list.add(metaItems.worldRoot=new MetaItem(metaItems,"world-root",metaItems.id(),in-> {}));
  }
}
