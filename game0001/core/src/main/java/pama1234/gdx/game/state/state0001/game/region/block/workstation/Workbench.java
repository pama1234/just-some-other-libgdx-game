package pama1234.gdx.game.state.state0001.game.region.block.workstation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe.CraftItem;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.Block.BlockUi;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.math.Tools;

public class Workbench extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public static final int stepMod=2,stopMod=1,updateMod=0;
  public int outputSlotSize=2,sloatSize=5;
  public CraftRecipe[] recipeList;
  public Workbench(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"workbench",id,1,0,(world,in,type,x,y)-> {//change to workbench
      in.light.set(16);
      in.intData[4]=x;
      in.intData[5]=y;
    },(world,in,type,x,y)-> {//change from workbench
      // World0001 world=pc.pw;
      // int x=in.intData[4],y=in.intData[5];
      boolean flag=Block.isEmpty(world.getBlock(x,y-1));
      float randomConst=0.8f;
      for(ItemSlot e:in.itemData) if(e.item!=null) DroppedItem.dropItem(world.p,x,y,world,flag,randomConst,e.item);
      in.intData=null;
      in.itemData=null;
      in.ui=null;
      // in.ui.displaySlot=null;
    });
    blockType=woodType;
    workStation=true;
    fullBlock=false;
    destroyTime=120;
    buildTime=10;
    initLambda();
  }
  public void initLambda() {
    updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      int tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      // int tx=(in.ui.displaySlot.length-1)/2*tw;
      // in.intData[2]=(x-tx)*tw;
      // in.intData[4]=x;
      // in.intData[5]=y;
      in.intData[2]=(x-2)*tw;
      in.intData[3]=(y-3)*th;
      if(in.intData[1]==stopMod) return;
      checkRecipe(in,recipeList[in.intData[0]],in.intData[1]);
      for(CraftRecipe e:recipeList) checkRecipe(in,e,in.intData[1]);
    };
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      int length=in.ui.displaySlot.length;
      float tx=(length-1)/2;
      for(int i=0;i<length;i++) in.ui.displaySlot[i].update(
        (x+i-tx)*tw,(y-1)*th);//TODO waste efficiency
    };
    displayer=(r,p,world,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,world,in,x,y);
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      p.textScale(0.5f);
      for(DisplaySlot e:in.ui.displaySlot) Inventory.displaySlot(p,e);
      p.textScale(1);
      // p.text(Integer.toString(in.intData[0]),x,y-tw*2);
      int ti=in.intData[0];
      String ts=Integer.toString(ti);
      p.text(ts+")",x-tw*(1.5f+ts.length()/2f),y-th*2);
      if(!Tools.inRangeInclude(ti,0,recipeList.length-1)) return;
      p.text("<-",x,y-th*2);
      CraftRecipe recipe=recipeList[ti];
      p.tint(255,160);
      p.textColor(255,160);
      float i=-2;
      for(CraftItem e:recipe.output) drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      i+=1;
      for(CraftItem e:recipe.input) drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      p.noTint();
      for(TextButtonCam<?> e:in.ui.camButton) e.display();
    };
  }
  public static void drawItemWithCount(Screen0011 p,float x,float y,MetaItem type,int count) {
    TextureRegion tr=type.tiles[0];
    p.image(tr,x,y);
    p.text(Integer.toString(count),x,y);
  }
  public void checkRecipe(Block in,CraftRecipe e,int type) {
    checkRecipe(in.itemData,e,outputSlotSize,type);
  }
  public static void checkRecipe(ItemSlot[] itemData,CraftRecipe e,int outputSlotSize,int type) {
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
    if(type==stepMod) {
      for(int k=0;k<e.output.length;k++) if(itemData[k].item!=null) return;
    }else {
      for(int k=0;k<e.output.length;k++) {
        ItemSlot slot=itemData[k];
        if(slot.item!=null&&slot.item.type!=e.output[k].type) return;
      }
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
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null) in.intData=new int[6];
    else if(in.intData.length<6) in.intData=new int[6];
    // in.intData[0]=0;
    // else if(in.intData[0]>=recipeList.length) in.intData[0]=recipeList.length-1;
    if(in.itemData==null) {
      in.itemData=new ItemSlot[sloatSize];
      for(int i=0;i<in.itemData.length;i++) in.itemData[i]=new ItemSlot();
    }
    in.ui=new BlockUi();
    in.ui.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.ui.displaySlot.length;i++) in.ui.displaySlot[i]=new DisplaySlot(in.itemData[i]);
    in.ui.camButton=UiGenerator.genButtons_0009(pc.pw.pc.pg.p,in);
    in.changed=true;
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
    MetaItemCenter0001<?> mi=pc.pw.metaItems;
    recipeList=new CraftRecipe[] {
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stonePickaxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneAxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneSword)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.stone)},new CraftItem[] {new CraftItem(mi.stoneChisel)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch,8)},new CraftItem[] {new CraftItem(mi.log)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.log)},new CraftItem[] {new CraftItem(mi.branch,8)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.log)},new CraftItem[] {new CraftItem(mi.woodPlank,4)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.leaf)},new CraftItem[] {new CraftItem(mi.sapling,2)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.leaf),new CraftItem(mi.branch)},new CraftItem[] {new CraftItem(mi.torch,4)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.log)},new CraftItem[] {new CraftItem(mi.woodPlatform,4)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.stone,8)},new CraftItem[] {new CraftItem(mi.furnace)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.woodPlank,8)},new CraftItem[] {new CraftItem(mi.door)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.woodPlank,8)},new CraftItem[] {new CraftItem(mi.chest)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.lightIngot,4)},new CraftItem[] {new CraftItem(mi.lightPickaxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.lightIngot,4)},new CraftItem[] {new CraftItem(mi.lightAxe)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.lightIngot,4)},new CraftItem[] {new CraftItem(mi.lightSword)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.branch),new CraftItem(mi.lightIngot,4)},new CraftItem[] {new CraftItem(mi.lightChisel)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.stoneChisel)},new CraftItem[] {new CraftItem(mi.stoneChisel),new CraftItem(mi.colorBlock,64)}),
      new CraftRecipe(new CraftItem[] {new CraftItem(mi.lightChisel)},new CraftItem[] {new CraftItem(mi.lightChisel),new CraftItem(mi.lightBlock,64)}),
    };
    intData=new int[] {recipeList.length};
  }
}