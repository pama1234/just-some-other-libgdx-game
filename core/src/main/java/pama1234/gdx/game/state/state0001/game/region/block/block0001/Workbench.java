package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.item.Inventory.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Workbench extends MetaBlock{
  public int outputSlotSize=1;
  public Workbench(MetaBlockCenter0001 pc,int id) {
    super(pc,"workbench",id,1,2,(in,type)-> {//change to workbench
      in.light.set(16);
    },(in,type)-> {//change from workbench
    });
    workStation=true;
    fullBlock=false;
    destroyTime=120;
    buildTime=10;
    // initLambda();
    initLambda();
  }
  public void initLambda() {
    updater=(in,x,y)-> {
      lightUpdater.update(in,x,y);
      tag:
      for(CraftRecipe e:recipeList) {
        for(CraftItem i:e.input) {
          int count=0;
          for(int j=outputSlotSize;j<in.itemData.length;j++) {
            Item ti=in.itemData[j];
            if(ti==null) continue;
            if(ti.type==i.type) count+=ti.count;
          }
          if(count<i.count) continue tag;
        }
        for(CraftItem i:e.input) {
          int count=i.count;
          for(int j=outputSlotSize;j<in.itemData.length;j++) {
            Item ti=in.itemData[j];
            if(ti==null) continue;
            if(ti.type==i.type) {
              if(count>ti.count) {
                count-=ti.count;
                in.itemData[j]=null;
              }else {
                ti.count-=count;
                count=0;
              }
            }
          }
          if(count<i.count) continue tag;
        }
        for(int k=0;k<e.output.length;k++) {
          if(k>=outputSlotSize) break;
          CraftItem i=e.output[k];
          in.itemData[k]=i.type.createItem(i.count);
        }
      }
    };
    displayer=(p,in,x,y)-> {
      defaultBlockDisplayer.display(p,in,x,y);
    };
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.workbench,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[6][8];
    //-----------------------------------------------------
    MetaItemCenter0001 mi=pc.pw.metaItems;
    recipeList=new CraftRecipe[] {new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.pickaxe)})};
  }
  @Override
  public void initBlock(Block in) {
    // in.blockData=new int[3];
    in.itemData=new Item[3];
    in.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.displaySlot.length;i++) in.displaySlot[i]=new DisplaySlot(new InventorySlot(in.itemData[i]));
  }
  public CraftRecipe[] recipeList;
  public static class CraftRecipe{
    public CraftItem[] input,output;
    public CraftRecipe(CraftItem[] input,CraftItem[] output) {
      this.input=input;
      this.output=output;
    }
  }
  public static class CraftItem{
    public int count=1;
    public MetaItem type;
    public CraftItem(MetaItem item) {
      this.type=item;
    }
    public CraftItem(MetaItem item,int count) {
      this.type=item;
      this.count=count;
    }
  }
}