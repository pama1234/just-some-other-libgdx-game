package pama1234.gdx.game.sandbox.platformer.region.block.block0002;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.item.DisplaySlot;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.region.block.Block.BlockUi;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.generator.UiGenerator;

public class WorldDebugger extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public WorldDebugger(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"world-debugger",id,1,1,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
      in.intData=null;
      in.ui=null;
    });
    attr.blockType=stoneType;
    attr.workStation=true;
    attr.fullBlock=false;
    attr.destroyTime=120;
    attr.buildTime=30;
    initLambda();
  }
  public void initLambda() {
    rttr.updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      int tw=world.settings.blockWidth,
        th=world.settings.blockHeight;
      in.intData[2]=(x-2)*tw;
      in.intData[3]=(y-3)*th;

      in.intData[0]+=1;
      if(in.intData[0]>10) {
        in.intData[0]=0;
        in.displayType[0]+=1;
        in.displayType[0]%=rttr.tiles.length;
      }
    };
    rttr.displayer=(r,p,world,in,x,y)-> {
      defaultBlockDisplayer.display(r,p,world,in,x,y);
      for(TextButtonCam<?> e:in.ui.camButton) e.display();
    };
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null) in.intData=new int[4];
    else if(in.intData.length<4) in.intData=new int[4];
    in.ui=new BlockUi();
    in.ui.displaySlot=new DisplaySlot[0];
    in.ui.camButton=UiGenerator.genButtons_0010(world.p,world,in);
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.worldRoot,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[0]=tsrc[6][7];
    rttr.tiles[0]=tsrc[7][7];
  }
}