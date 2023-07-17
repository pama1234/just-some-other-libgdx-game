package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class TreeLog extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public TreeLog(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"tree-log",id,6,1,(world,in,type,x,y)-> {//change to log
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from log
      in.intData=null;
    });
    blockType=woodType;
    destroyTime=60;
    buildTime=10;
    fullBlock=false;
    // initFullBlockLambda();
    initTreeLogLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.log,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[1]=tsrc[16][4];
    tiles[2]=tsrc[16][5];
    tiles[5]=tsrc[17][5];
    tiles[3]=tsrc[16][6];
    tiles[0]=tsrc[17][6];
    tiles[4]=tsrc[18][6];
  }
  public void initTreeLogLambda() {
    updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      // World0001 world=pc.pw;
      Block tb=world.getBlock(x,y+1);
      if(tb!=null&&tb.type!=pc.log&&tb.type!=pc.dirt) world.r.destroyBlock(in,x,y);
    };
    displayUpdater=(world,in,x,y)-> {
      // World0001 world=pc.pw;
      int typeCache=0;
      if(isTreeLog(world.getBlock(x,y+1),this)) {// down
        if(isTreeLeaf(world.getBlock(x,y-2),pc.leaf)&&// up 2
          isTreeLog(world.getBlock(x,y-1),this)) typeCache=1;// up
        else {
          typeCache=2;// down
          if(isTreeBranch(world.getBlock(x-1,y),pc.branch)) typeCache+=1;// left
          if(isTreeBranch(world.getBlock(x+1,y),pc.branch)) typeCache+=2;// right
        }
      }
      in.displayType[0]=typeCache;
      //---
      if(in.updateLighting) lightingUpdate(in,x,y,world);
      // in.light.update();
    };
  }
  public static boolean isTreeLog(Block in,TreeLog type) {
    return in!=null&&in.type==type;
  }
  public static boolean isTreeBranch(Block in,TreeBranch type) {
    return in!=null&&in.type==type;
  }
  public static boolean isTreeLeaf(Block in,TreeLeaf type) {
    return in!=null&&in.type==type;
  }
  public static boolean isTreeLeafOrNull(Block in,TreeLeaf type) {
    return in==null||in.type==type;
  }
}