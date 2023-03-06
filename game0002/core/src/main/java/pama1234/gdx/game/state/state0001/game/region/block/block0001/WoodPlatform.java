package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class WoodPlatform extends MetaBlock{
  public WoodPlatform(MetaBlockCenter0001 pc,int id) {
    super(pc,"wood-platform",id,4,2,(in,type,x,y)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(in,type,x,y)-> {//change from me
    });
    blockType=woodType;
    destroyTime=10;
    buildTime=5;
    fullBlock=false;
    fullBlockType=FullBlockType.platformType;
    initLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.woodPlatform,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=11,ty=9;
    //-----------------------------------------------------
    tiles[0]=tsrc[tx][ty];
    tiles[1]=tsrc[tx+1][ty];
    tiles[3]=tsrc[tx+2][ty];
    tiles[2]=tsrc[tx+3][ty];
  }
  public void initLambda() {
    // updater=lightUpdater;
    displayUpdater=(world,in,x,y)-> {
      // World0001 world=in.type.pc.pw;
      int typeCache=0;
      if(isPlatformOrPlank(pc.woodPlank,world.getBlock(x+1,y))) typeCache+=1;
      if(isPlatformOrPlank(pc.woodPlank,world.getBlock(x-1,y))) typeCache+=2;
      // if(typeCache==3) typeCache=2;
      // else if(typeCache==2) typeCache=3;
      in.displayType[0]=typeCache;
      //---
      defaultDisplayUpdater.update(world,in,x,y);
      // in.light.update();
    };
  }
  public boolean isPlatformOrPlank(WoodPlank type,Block in) {
    return in!=null&&(in.type==this||in.type==type);
  }
  public static boolean isWoodPlatform(WoodPlatform type,Block block) {
    return block!=null&&block.type==type;
  }
}