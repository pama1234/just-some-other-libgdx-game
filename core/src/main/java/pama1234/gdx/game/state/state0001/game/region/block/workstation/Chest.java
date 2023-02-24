package pama1234.gdx.game.state.state0001.game.region.block.workstation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.Block.BlockUi;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class Chest extends MetaBlock{
  public int sloatSize=9,sloatDisplayWidth=3;
  public Chest(MetaBlockCenter0001 pc,int id) {
    super(pc,"chest",id,1,0,(in,type)-> {//change to me
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
      // in.ui.displaySlot=null;
    });
    workStation=true;
    fullBlock=false;
    destroyTime=120;
    buildTime=10;
    initLambda();
  }
  public void initLambda() {
    updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
    };
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      float tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      int length=in.ui.displaySlot.length;
      float tx=(sloatDisplayWidth-1)/2,
        ty=length/sloatDisplayWidth;
      for(int i=0;i<length;i++) in.ui.displaySlot[i].update(
        (x+i%sloatDisplayWidth-tx)*tw,
        (y-ty+i/sloatDisplayWidth)*th);//TODO waste efficiency
    };
    displayer=(r,p,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,in,x,y);
      p.textScale(0.5f);
      for(DisplaySlot e:in.ui.displaySlot) Inventory.displaySlot(p,e);
      p.textScale(1);
    };
  }
  @Override
  public void initBlock(Block in) {
    if(in.intData==null) in.intData=new int[6];
    else if(in.intData.length<6) in.intData=new int[6];
    if(in.itemData==null) {
      in.itemData=new ItemSlot[sloatSize];
      for(int i=0;i<in.itemData.length;i++) in.itemData[i]=new ItemSlot();
    }
    in.ui=new BlockUi();
    in.ui.displaySlot=new DisplaySlot[in.itemData.length];
    for(int i=0;i<in.ui.displaySlot.length;i++) in.ui.displaySlot[i]=new DisplaySlot(in.itemData[i]);
    // in.ui.camButton=UiGenerator.genButtons_0009(pc.pw.p,in);
    in.ui.camButton=new TextButtonCam[0];
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.chest,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[6][1];
  }
}