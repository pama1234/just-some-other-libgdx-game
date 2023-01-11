package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class TreeLog extends MetaBlock{
  public TreeLog(MetaBlockCenter0001 pc,int id) {
    super(pc,"tree-log",id,6,2,(in,type)-> {//change to log
      in.light.set(16);
    },(in,type)-> {//change from log
    });
    destroyTime=60;
    buildTime=10;
    fullBlock=false;
    // initFullBlockLambda();
    initTreeLogLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.log,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[2]=tsrc[16][4];
    tiles[1]=tsrc[16][5];
    tiles[4]=tsrc[17][5];
    tiles[3]=tsrc[16][6];
    tiles[0]=tsrc[17][6];
    tiles[5]=tsrc[18][6];
  }
  public void initTreeLogLambda() {
    // updater=lightUpdater;
    displayUpdater=(in,x,y)-> {
      World0001 world=in.type.pc.pw;
      int typeCache=0;
      // if(isTreeLog(world.getBlock(x,y-1),this)) typeCache+=1;// up
      if(isTreeLog(world.getBlock(x,y+1),this)) typeCache+=1;// down
      // if(Block.isEmpty(world.getBlock(x-1,y))) typeCache+=4;// left
      // if(Block.isEmpty(world.getBlock(x+1,y))) typeCache+=8;// right
      in.displayType[0]=typeCache;
      // typeCache=0;
      // if(Block.isNotFullBlock(world.getBlock(x-1,y-1))) typeCache+=1;
      // if(Block.isNotFullBlock(world.getBlock(x-1,y+1))) typeCache+=2;
      // if(Block.isNotFullBlock(world.getBlock(x+1,y+1))) typeCache+=4;
      // if(Block.isNotFullBlock(world.getBlock(x+1,y-1))) typeCache+=8;
      // in.displayType[1]=typeCache;
      //---
      if(in.updateLighting) lightingUpdate(in,x,y,world);
      // in.light.update();
    };
    // displayer=fullBlockDisplayer;
  }
  public static boolean isTreeLog(Block in,TreeLog type) {
    return in!=null&&in.type==type;
  }
}
