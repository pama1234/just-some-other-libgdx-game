package pama1234.gdx.game.sandbox.platformer.region.block.workstation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem;
import pama1234.gdx.game.sandbox.platformer.item.DisplaySlot;
import pama1234.gdx.game.sandbox.platformer.item.Inventory;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.region.block.Block.BlockUi;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.game.ui.element.TextButtonCam;

public class Chest extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public int sloatSize=9,sloatDisplayWidth=3;
  public Chest(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"chest",id,1,0,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
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
      // in.ui.displaySlot=null;
    });
    attr.blockType=woodType;
    attr.workStation=true;
    attr.fullBlock=false;
    attr.destroyTime=120;
    attr.buildTime=10;
    initLambda();
  }
  public void initLambda() {
    rttr.updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
    };
    rttr.displayUpdater=(world,in,x,y)-> {
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
    rttr.displayer=(r,p,world,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,world,in,x,y);
      p.textScale(0.5f);
      for(DisplaySlot e:in.ui.displaySlot) Inventory.displaySlot(p,e);
      p.textScale(1);
    };
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
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
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.chest,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[0]=tsrc[6][1];
  }
}