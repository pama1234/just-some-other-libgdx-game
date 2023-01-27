package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Sapling extends MetaBlock{
  public Sapling(MetaBlockCenter0001 pc,int id) {
    super(pc,"sapling",id,1,1,(in,type)-> {//change to log
      in.light.set(16);
    },(in,type)-> {//change from log
    });
    blockType=woodType;
    destroyTime=10;
    buildTime=5;
    fullBlock=false;
    // initFullBlockLambda();
    initTreeLogLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.sapling,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[6][6];
  }
  public void initTreeLogLambda() {
    // updater=lightUpdater;
    // displayUpdater=(in,x,y)-> {
    //   World0001 world=in.type.pc.pw;
    //   int typeCache=0;
    //   if(isTreeLog(world.getBlock(x,y+1),this)) {// down
    //     if(isTreeLeaf(world.getBlock(x,y-2),pc.leaf)&&// up 2
    //       isTreeLog(world.getBlock(x,y-1),this)) typeCache=1;// up
    //     else {
    //       typeCache=2;// down
    //       if(isTreeBranch(world.getBlock(x-1,y),pc.branch)) typeCache+=1;// left
    //       if(isTreeBranch(world.getBlock(x+1,y),pc.branch)) typeCache+=2;// right
    //     }
    //   }
    //   in.displayType[0]=typeCache;
    //   //---
    //   if(in.updateLighting) lightingUpdate(in,x,y,world);
    //   // in.light.update();
    // };
    // displayer=fullBlockDisplayer;
  }
}