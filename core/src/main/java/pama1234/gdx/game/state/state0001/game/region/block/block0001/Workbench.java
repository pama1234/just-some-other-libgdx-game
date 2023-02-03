package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
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
      // System.out.println(recipeList[0]);
      // System.out.println(in.itemData[0]);
      // System.out.println(in.displaySlot[0].data.item);
      tag_1:
      for(CraftRecipe e:recipeList) {
        for(CraftItem i:e.input) {
          int count=0;
          // System.out.println(i.type);
          for(int j=outputSlotSize;j<in.itemData.length;j++) {
            Item ti=in.itemData[j].item;
            // System.out.println(ti);
            if(ti==null) continue;
            if(ti.type==i.type) count+=ti.count;
            // System.out.println(ti.count);
          }
          // System.out.println(count);
          if(count<i.count) continue tag_1;
        }
        for(CraftItem i:e.input) {
          int count=i.count;
          for(int j=outputSlotSize;j<in.itemData.length;j++) {
            Item ti=in.itemData[j].item;
            if(ti==null) continue;
            if(ti.type==i.type) {
              if(count>ti.count) {
                count-=ti.count;
                in.itemData[j].item=null;
              }else {
                ti.count-=count;
                if(ti.count==0) in.itemData[j].item=null;
                count=0;
                break;
              }
            }
          }
          // if(count>0) continue tag;
        }
        // System.out.println("Workbench.initLambda()");
        for(int k=0;k<e.output.length;k++) {
          if(k>=outputSlotSize) break;
          CraftItem i=e.output[k];
          in.itemData[k].item=i.type.createItem(i.count);
        }
      }
    };
    displayer=(r,p,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,in,x,y);
      float tw=pc.pw.settings.blockWidth,
        th=pc.pw.settings.blockHeight;
      float tx=(in.displaySlot.length-1)/2*tw;
      for(int i=0;i<in.displaySlot.length;i++) {//TODO waste efficiency
        DisplaySlot slot=in.displaySlot[i];
        slot.update(x-tx+i*tw,y-th);
      }
      p.textScale(0.5f);
      for(DisplaySlot e:in.displaySlot) Inventory.displaySlot(p,e);
      p.textScale(1);
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
    in.itemData=new InventorySlot[3];
    in.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.displaySlot.length;i++) in.displaySlot[i]=new DisplaySlot(in.itemData[i]=new InventorySlot());
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