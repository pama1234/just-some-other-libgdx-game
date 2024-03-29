package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class WoodPlank extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public WoodPlank(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"wood-plank",id,22,3,(world,in,type,x,y)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
    });
    attr.blockType=woodType;
    attr.destroyTime=30;
    attr.buildTime=15;
    attr.fullBlockType=FullBlockType.plankType;
    initLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.woodPlank,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=0,ty=12;
    //-----------------------------------------------------
    rttr.tiles[15]=tsrc[tx][ty];
    rttr.tiles[7]=tsrc[tx+1][ty];
    rttr.tiles[3]=tsrc[tx+2][ty];
    rttr.tiles[11]=tsrc[tx+3][ty];
    //-----------------------------------------------------
    rttr.tiles[13]=tsrc[tx][ty+1];
    rttr.tiles[5]=tsrc[tx+1][ty+1];
    rttr.tiles[1]=tsrc[tx+2][ty+1];
    rttr.tiles[9]=tsrc[tx+3][ty+1];
    //-----------------------------------------------------
    rttr.tiles[12]=tsrc[tx][ty+2];
    rttr.tiles[4]=tsrc[tx+1][ty+2];
    rttr.tiles[0]=tsrc[tx+2][ty+2];
    rttr.tiles[8]=tsrc[tx+3][ty+2];
    //-----------------------------------------------------
    rttr.tiles[14]=tsrc[tx][ty+3];
    rttr.tiles[6]=tsrc[tx+1][ty+3];
    rttr.tiles[2]=tsrc[tx+2][ty+3];
    rttr.tiles[10]=tsrc[tx+3][ty+3];
    //-----------------------------------------------------
    tx=4;
    ty=10;
    rttr.tiles[16]=tsrc[tx][ty];
    rttr.tiles[17]=tsrc[tx+1][ty];
    rttr.tiles[18]=tsrc[tx][ty+1];
    rttr.tiles[19]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tx=4;
    ty=12;
    rttr.tiles[20]=tsrc[tx][ty];
    rttr.tiles[21]=tsrc[tx+1][ty];
  }
  public void initLambda() {
    // updater=lightUpdater;
    rttr.displayUpdater=(world,in,x,y)-> {
      fullBlockDisplayUpdater.update(world,in,x,y);
      int typeCache=0;
      if(WoodPlatform.isWoodPlatform(pc.woodPlatform,world.getBlock(x-1,y))) typeCache+=2;// left
      if(WoodPlatform.isWoodPlatform(pc.woodPlatform,world.getBlock(x+1,y))) typeCache+=1;// right
      in.displayType[2]=typeCache;
    };
    rttr.displayer=(r,p,world,in,x,y)-> {
      fullBlockDisplayer.display(r,p,world,in,x,y);
      int ti=in.displayType[2];
      if(ti!=0) {
        if((ti&1)!=0) r.tile(in.type.rttr.tiles[20],x,y);
        if((ti&2)!=0) r.tile(in.type.rttr.tiles[21],x,y);
      }
    };
  }
}