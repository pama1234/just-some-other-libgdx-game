package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Inventory.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe.CraftItem;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.math.Tools;

public class Workbench extends MetaBlock{
  public int outputSlotSize=2,sloatSize=5;
  public CraftRecipe[] recipeList;
  public Workbench(MetaBlockCenter0001 pc,int id) {
    super(pc,"workbench",id,1,0,(in,type)-> {//change to workbench
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
      if(in.intData[1]!=0) return;
      in.intData[0]=Tools.moveInRange(in.intData[0],0,recipeList.length);
      checkRecipe(in,recipeList[in.intData[0]]);
      for(CraftRecipe e:recipeList) checkRecipe(in,e);
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
      // p.text(Integer.toString(in.intData[0]),x,y-tw*2);
      int ti=in.intData[0];
      String ts=Integer.toString(ti);
      p.text(ts+":",x-tw*(3-ts.length()),y-th*2);
      if(!Tools.inRangeInclude(ti,0,recipeList.length-1)) return;
      p.text("<-",x,y-th*2);
      CraftRecipe recipe=recipeList[ti];
      p.tint(255,160);
      p.textColor(255,160);
      float i=-2;
      for(CraftItem j:recipe.output) drawItemWithCount(p,x+(i+=1)*tw,y-th*2,j.type,j.count);
      i+=1;
      for(CraftItem j:recipe.input) drawItemWithCount(p,x+(i+=1)*tw,y-th*2,j.type,j.count);
      p.noTint();
    };
  }
  public static void drawItemWithCount(Screen0011 p,float x,float y,MetaItem type,int count) {
    TextureRegion tr=type.tiles[0];
    p.image(tr,x,y);
    p.text(Integer.toString(count),x,y);
  }
  public void checkRecipe(Block in,CraftRecipe e) {
    checkRecipe(in.itemData,e,outputSlotSize);
  }
  public static void checkRecipe(ItemSlot[] itemData,CraftRecipe e,int outputSlotSize) {
    if(e.output.length>outputSlotSize) return;
    for(CraftItem i:e.input) {
      int count=0;
      for(int j=outputSlotSize;j<itemData.length;j++) {
        Item ti=itemData[j].item;
        if(ti==null) continue;
        if(ti.type==i.type) count+=ti.count;
      }
      if(count<i.count) return;
    }
    for(int k=0;k<e.output.length;k++) {
      ItemSlot slot=itemData[k];
      if(slot.item!=null&&slot.item.type!=e.output[k].type) return;
    }
    for(CraftItem i:e.input) {
      int count=i.count;
      for(int j=outputSlotSize;j<itemData.length;j++) {
        Item ti=itemData[j].item;
        if(ti==null) continue;
        if(ti.type==i.type) {
          if(count>ti.count) {
            count-=ti.count;
            itemData[j].item=null;
          }else {
            ti.count-=count;
            if(ti.count==0) itemData[j].item=null;
            count=0;
            break;
          }
        }
      }
    }
    for(int k=0;k<e.output.length;k++) {
      CraftItem i=e.output[k];
      ItemSlot slot=itemData[k];
      if(slot.item==null) slot.item=i.type.createItem(i.count);
      else if(slot.item.type==i.type) slot.item.count+=i.count;
    }
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
    recipeList=new CraftRecipe[] {
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stonePickaxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneAxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneSword)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneChisel)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch,4)},new CraftItem[] {new CraftItem(mi.log)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.log)},new CraftItem[] {new CraftItem(mi.branch,4)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.leaf)},new CraftItem[] {new CraftItem(mi.sapling,2)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.leaf),new CraftItem(mi.branch)},new CraftItem[] {new CraftItem(mi.torch,4)}),
    };
  }
  @Override
  public void initBlock(Block in) {
    in.intData=new int[2];
    in.itemData=new ItemSlot[sloatSize];
    in.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.displaySlot.length;i++) in.displaySlot[i]=new DisplaySlot(in.itemData[i]=new ItemSlot());
  }
}