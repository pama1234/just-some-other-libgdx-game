package pama1234.gdx.game.sandbox.platformer.region.block.workstation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.Tools;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem;
import pama1234.gdx.game.sandbox.platformer.item.CraftRecipe;
import pama1234.gdx.game.sandbox.platformer.item.CraftRecipe.CraftItem;
import pama1234.gdx.game.sandbox.platformer.item.DisplaySlot;
import pama1234.gdx.game.sandbox.platformer.item.Inventory;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.item.MeltRecipe;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.region.block.Block.BlockUi;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.generator.UiGenerator;

public class Furnace extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  // public static final int stepMod=2,stopMod=1,updateMod=0;
  public int outputSlotSize=1,sloatSize=2;
  public MeltRecipe[] recipeList;
  public Furnace(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"furnace",id,4,1,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
      if(in.offBlock()) return;
      in.intData[4]=x;
      in.intData[5]=y;
    },(world,in,type,x,y)-> {//change from me 
      // World0001 world=pc.pw;
      // int x=in.intData[4],y=in.intData[5];
      boolean flag=Block.isEmpty(world.getBlock(x,y-1));
      float randomConst=0.8f;
      for(ItemSlot e:in.itemData) if(e.item!=null) DroppedItem.dropItem(world.p,x,y,world,flag,randomConst,e.item);
      in.intData=null;
      in.itemData=null;
      in.ui=null;
    });
    attr.blockType=stoneType;
    attr.workStation=true;
    attr.fullBlock=false;
    attr.destroyTime=120;
    attr.buildTime=60;
    attr.width=2;
    attr.height=2;
    initLambda();
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[0]=tsrc[9][8];
    rttr.tiles[1]=tsrc[10][8];
    rttr.tiles[2]=tsrc[9][9];
    rttr.tiles[3]=tsrc[10][9];
    //-----------------------------------------------------
    MetaItemCenter0001<?> mi=pc.pwt.metaItems;
    recipeList=new MeltRecipe[] {
      new MeltRecipe(60,new CraftItem[] {new CraftItem(mi.lightOre,4)},new CraftItem[] {new CraftItem(mi.lightIngot)}),
    };
    attr.intData=new int[] {recipeList.length};
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.offBlock()) {
      in.ui=new BlockUi();
      in.ui.displaySlot=new DisplaySlot[0];
      in.ui.camButton=new TextButtonCam[0];
      return;
    }
    if(in.intData==null) in.intData=new int[6];
    else if(in.intData.length<6) in.intData=new int[6];
    if(in.itemData==null) {
      in.itemData=new ItemSlot[sloatSize];
      for(int i=0;i<in.itemData.length;i++) in.itemData[i]=new ItemSlot();
    }
    in.ui=new BlockUi();
    in.ui.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.ui.displaySlot.length;i++) in.ui.displaySlot[i]=new DisplaySlot(in.itemData[i]);
    in.ui.camButton=UiGenerator.genButtons_0009(pc.pwt.pc.pg.p,in);
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.furnace,1)};
  }
  public void initLambda() {
    rttr.updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      if(in.offBlock()) return;
      int tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      // in.intData[4]=x;
      // in.intData[5]=y;
      in.intData[2]=(x-2)*tw;
      in.intData[3]=(y-3)*th;
      if(in.intData[1]==Workbench.stopMod) return;
      Workbench.checkRecipe(in.itemData,recipeList[in.intData[0]],outputSlotSize,in.intData[1]);
      for(CraftRecipe e:recipeList) Workbench.checkRecipe(in.itemData,e,outputSlotSize,in.intData[1]);
    };
    rttr.displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      in.displayType[0]=in.xOff+in.yOff*in.type.attr.width;
      if(in.offBlock()) return;
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      int length=in.ui.displaySlot.length;
      float tx=(length-1)/2;
      for(int i=0;i<length;i++) in.ui.displaySlot[i].update(
        (x+i-tx)*tw,(y-1)*th);//TODO waste efficiency
    };
    rttr.displayer=(r,p,world,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,world,in,x,y);
      if(in.offBlock()) return;
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      p.textScale(0.5f);
      for(DisplaySlot e:in.ui.displaySlot) Inventory.displaySlot(p,e);
      p.textScale(1);
      // p.text(Integer.toString(in.intData[0]),x,y-tw*2);
      int ti=in.intData[0];
      String ts=Integer.toString(ti);
      int tx=1;
      p.text(ts+")",x-tw*(tx+ts.length()/2f),y-th*2);
      if(!Tools.inRangeInclude(ti,0,recipeList.length-1)) return;
      p.text("<-",x-tw*(tx-1.5f),y-th*2);
      CraftRecipe recipe=recipeList[ti];
      p.tint(255,160);
      p.textColor(255,160);
      float i=tx-2.5f;
      for(CraftItem e:recipe.output) Workbench.drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      i+=1;
      for(CraftItem e:recipe.input) Workbench.drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      p.noTint();
      for(TextButtonCam<?> e:in.ui.camButton) e.display();
    };
  }
}