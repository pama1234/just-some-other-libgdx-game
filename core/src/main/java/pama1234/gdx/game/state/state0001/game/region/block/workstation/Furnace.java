package pama1234.gdx.game.state.state0001.game.region.block.workstation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe;
import pama1234.gdx.game.state.state0001.game.item.CraftRecipe.CraftItem;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.item.MeltRecipe;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.Block.BlockUi;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.math.Tools;

public class Furnace extends MetaBlock{
  // public static final int stepMod=2,stopMod=1,updateMod=0;
  public int outputSlotSize=1,sloatSize=2;
  public MeltRecipe[] recipeList;
  public Furnace(MetaBlockCenter0001 pc,int id) {
    super(pc,"furnace",id,4,1,(in,type)-> {//change to me
      in.light.set(16);
    },(in,type)-> {//change from me 
      World0001 world=pc.pw;
      int x=in.intData[4],y=in.intData[5];
      boolean flag=Block.isEmpty(world.getBlock(x,y-1));
      float randomConst=0.8f;
      for(ItemSlot e:in.itemData) if(e.item!=null) DroppedItem.dropItem(world.p,x,y,world,flag,randomConst,e.item);
      in.intData=null;
      in.itemData=null;
      in.ui=null;
    });
    blockType=stoneType;
    workStation=true;
    fullBlock=false;
    destroyTime=120;
    buildTime=60;
    width=2;
    height=2;
    initLambda();
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[9][8];
    tiles[1]=tsrc[10][8];
    tiles[2]=tsrc[9][9];
    tiles[3]=tsrc[10][9];
    //-----------------------------------------------------
    MetaItemCenter0001 mi=pc.pw.metaItems;
    recipeList=new MeltRecipe[] {
      new MeltRecipe(60,new CraftItem[] {new CraftItem(mi.lightOre,4)},new CraftItem[] {new CraftItem(mi.lightIngot)}),
    };
    intData=new int[] {recipeList.length};
  }
  @Override
  public void initBlock(Block in) {
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
    // in.ui.camButton=new TextButtonCam[0];
    in.ui.camButton=UiGenerator.genButtons_0009(pc.pw.p,in);
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.furnace,1)};
  }
  public void initLambda() {
    updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      if(in.offBlock()) return;
      int tw=pc.pw.settings.blockWidth,
        th=pc.pw.settings.blockHeight;
      // int tx=(in.ui.displaySlot.length-1)/2*tw;
      // in.intData[2]=(x-tx)*tw;
      in.intData[4]=x;
      in.intData[5]=y;
      in.intData[2]=(x-2)*tw;
      in.intData[3]=(y-3)*th;
      if(in.intData[1]==Workbench.stopMod) return;
      // Workbench.checkRecipe(in,recipeList[in.intData[0]],in.intData[1]);
      // for(CraftRecipe e:recipeList) Workbench.checkRecipe(in,e,in.intData[1]);
    };
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      in.displayType[0]=in.xOff+in.yOff*in.type.width;
      if(in.offBlock()) return;
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      int length=in.ui.displaySlot.length;
      float tx=(length-1)/2;
      for(int i=0;i<length;i++) in.ui.displaySlot[i].update(
        (x+i-tx)*tw,(y-1)*th);//TODO waste efficiency
    };
    displayer=(r,p,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,in,x,y);
      if(in.offBlock()) return;
      float tw=pc.pw.settings.blockWidth,
        th=pc.pw.settings.blockHeight;
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
      for(CraftItem e:recipe.output) Workbench.drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      i+=1;
      for(CraftItem e:recipe.input) Workbench.drawItemWithCount(p,x+(i+=1)*tw,y-th*2,e.type,e.count);
      p.noTint();
      for(TextButtonCam<?> e:in.ui.camButton) e.display();
    };
  }
}