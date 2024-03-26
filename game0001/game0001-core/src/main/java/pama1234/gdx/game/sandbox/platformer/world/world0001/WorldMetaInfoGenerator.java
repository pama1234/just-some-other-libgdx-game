package pama1234.gdx.game.sandbox.platformer.world.world0001;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.sandbox.platformer.item.item0001.ItemGenerator0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.io.MetaPropertiesCenter;
import pama1234.gdx.game.sandbox.platformer.player.Player.PlayerType;
import pama1234.gdx.game.sandbox.platformer.region.block.BlockGenerator0001;

/** 用于生成世界中的方块，物品，生物 */
public class WorldMetaInfoGenerator{
  public static HashMap<String,ArrayList<String>> list;
  static {
    list=MetaPropertiesCenter.YAML.u.yaml.load(Gdx.files.internal("definition/list.yaml").readString());
  }

  /** 创建方块定义数据 */
  public static MetaBlockCenter0001<WorldType0001Base<?>> createBlockC(WorldType0001Base<?> in) {
    MetaBlockCenter0001<WorldType0001Base<?>> metaBlocks=new MetaBlockCenter0001<>(in);
    BlockGenerator0001.load(metaBlocks);
    return metaBlocks;
  }
  /** 加载方块定义数据 */
  public static MetaBlockCenter0001<WorldType0001Base<?>> loadBlockC(WorldType0001Base<?> in,MetaBlockCenter0001<WorldType0001Base<?>> metaBlocks) {
    //    HashMap<String,ArrayList<String>> list=MetaPropertiesCenter.YAML.u.yaml.load(Gdx.files.internal("definition/list.yaml").readString());
    FileHandle[] files=getFileHandles("block");

    for(FileHandle file:files) {
      String content=file.readString();
      String fileName=file.nameWithoutExtension();
      MetaBlock<WorldType0001Base<?>,?> block=null;
      for(var e:metaBlocks.list) {
        if(e.name.equals(fileName)) block=e;
      }
      if(block!=null) {
        MetaPropertiesCenter.YAML.loadBlock(content,block);
      }
    }
    return metaBlocks;
  }

  public static FileHandle[] getFileHandles(String name) {
    ArrayList<String> blockList=list.get(name);
    FileHandle[] files=new FileHandle[blockList.size()];
    for(int i=0;i<files.length;i++) {
      files[i]=Gdx.files.internal("definition/"+name+"/"+blockList.get(i)+".yaml");
    }
    return files;
  }
  //  /** 创建并加载方块定义数据 */
  //  public static MetaBlockCenter0001<WorldType0001Base<?>> loadAndCreateBlockC(WorldType0001Base<?> in) {
  //    MetaBlockCenter0001<WorldType0001Base<?>> metaBlocks=new MetaBlockCenter0001<>(in);
  //    FileHandle[] files=Gdx.files.local("data/saved/definition/block").list();
  //    for(FileHandle file:files) {
  //      String content=file.readString();
  //      MetaBlock<WorldType0001Base<?>,?> block=MetaPropertiesCenter.YAML.load(content);
  //      metaBlocks.list.add(block);
  //    }
  //    return metaBlocks;
  //  }
  /** 保存方块定义数据 */
  public static void saveBlockC(MetaBlockCenter0001<?> metaBlocks,boolean onlyWriteChangedFile) {
    for(var e:metaBlocks.list) {
      if(!onlyWriteChangedFile||e.attributeChanged) {
        String ts=MetaPropertiesCenter.YAML.save(e);
        Gdx.files.local("data/saved/definition/block/"+e.name+".yaml").writeString(ts,false);
      }
    }
  }

  /** 创建物品定义数据 */
  public static <M extends WorldType0001Base<?>> MetaItemCenter0001<M> createItemC(M pwt) {
    MetaItemCenter0001<M> metaItems=new MetaItemCenter0001<>(pwt);
    ItemGenerator0001.load(pwt,metaItems);
    return metaItems;
  }
  /** 加载物品定义数据 */
  public static <M extends WorldType0001Base<?>> MetaItemCenter0001<M> loadItemC(M pwt,MetaItemCenter0001<M> metaItems) {
    FileHandle[] files=getFileHandles("item");
    for(FileHandle file:files) {
      String content=file.readString();
      String fileName=file.nameWithoutExtension();
      MetaItem item=null;
      for(var e:metaItems.list) {
        if(e.name.equals(fileName)) item=e;
      }
      if(item!=null) {
        MetaPropertiesCenter.YAML.loadItem(content,item);
      }
    }
    return metaItems;
  }
  //  /** 创建并加载物品定义数据 */
  //  public static <M extends WorldType0001Base<?>> MetaItemCenter0001<M> loadAndCreateItemC(M pwt) {
  //    MetaItemCenter0001<M> metaItems=new MetaItemCenter0001<>(pwt);
  //    FileHandle[] files=Gdx.files.local("data/saved/definition/item").list();
  //    for(FileHandle file:files) {
  //      String content=file.readString();
  //      MetaItem item=MetaPropertiesCenter.YAML.load(content);
  //      metaItems.list.add(item);
  //    }
  //    return metaItems;
  //  }
  /** 保存物品定义数据 */
  public static void saveItemC(MetaItemCenter0001<?> metaItems,boolean onlyWriteChangedFile) {
    for(var e:metaItems.list) {
      if(!onlyWriteChangedFile||e.attributeChanged) {
        String ts=MetaPropertiesCenter.YAML.save(e);
        Gdx.files.local("data/saved/definition/item/"+e.name+".yaml").writeString(ts,false);
      }
    }
  }

  /** 创建生物定义数据 */
  public static <M extends WorldType0001Base<?>> MetaCreatureCenter0001<M> createCreatureC(M in) {
    MetaCreatureCenter0001<M> metaEntities=new MetaCreatureCenter0001<>(in);
    metaEntities.list.add(metaEntities.player=new PlayerType(metaEntities,metaEntities.id()));
    metaEntities.list.add(metaEntities.droppedItem=new DroppedItemType(metaEntities,metaEntities.id()));
    metaEntities.list.add(metaEntities.fly=new FlyType(metaEntities,metaEntities.id()));
    return metaEntities;
  }
  /** 加载生物定义数据 */
  public static <M extends WorldType0001Base<?>> MetaCreatureCenter0001<M> loadCreatureC(M in,MetaCreatureCenter0001<M> metaEntities) {
    FileHandle[] files=getFileHandles("item");
    for(FileHandle file:files) {
      String content=file.readString();
      String fileName=file.nameWithoutExtension();
      MetaCreature creature=null;
      for(var e:metaEntities.list) {
        if(e.name.equals(fileName)) creature=e;
      }
      if(creature!=null) {
        MetaPropertiesCenter.YAML.loadCreature(content,creature);
      }
    }
    return metaEntities;
  }
  //  /** 创建并加载生物定义数据 */
  //  public static <M extends WorldType0001Base<?>> MetaCreatureCenter0001<M> loadAndCreateCreatureC(M in) {
  //    MetaCreatureCenter0001<M> metaEntities=new MetaCreatureCenter0001<>(in);
  //    FileHandle[] files=Gdx.files.local("data/saved/definition/creature").list();
  //    for(FileHandle file:files) {
  //      String content=file.readString();
  //      MetaCreature<?> creature=MetaPropertiesCenter.YAML.load(content);
  //      metaEntities.list.add(creature);
  //    }
  //    return metaEntities;
  //  }
  /** 保存生物定义数据 */
  public static void saveCreatureC(MetaCreatureCenter0001<?> metaEntities,boolean onlyWriteChangedFile) {
    for(var e:metaEntities.list) {
      if(!onlyWriteChangedFile||e.attributeChanged) {
        String ts=MetaPropertiesCenter.YAML.save(e);
        Gdx.files.local("data/saved/definition/creature/"+e.name+".yaml").writeString(ts,false);
      }
    }
  }

}