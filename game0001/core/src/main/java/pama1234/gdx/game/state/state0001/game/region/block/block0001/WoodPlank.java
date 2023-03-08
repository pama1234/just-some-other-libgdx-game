package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class WoodPlank extends MetaBlock{
  public WoodPlank(MetaBlockCenter0001 pc,int id) {
    super(pc,"wood-plank",id,22,3,(world,in,type,x,y)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
    });
    blockType=woodType;
    destroyTime=30;
    buildTime=15;
    fullBlockType=FullBlockType.plankType;
    initLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.woodPlank,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=0,ty=12;
    //-----------------------------------------------------
    tiles[15]=tsrc[tx][ty];
    tiles[7]=tsrc[tx+1][ty];
    tiles[3]=tsrc[tx+2][ty];
    tiles[11]=tsrc[tx+3][ty];
    //-----------------------------------------------------
    tiles[13]=tsrc[tx][ty+1];
    tiles[5]=tsrc[tx+1][ty+1];
    tiles[1]=tsrc[tx+2][ty+1];
    tiles[9]=tsrc[tx+3][ty+1];
    //-----------------------------------------------------
    tiles[12]=tsrc[tx][ty+2];
    tiles[4]=tsrc[tx+1][ty+2];
    tiles[0]=tsrc[tx+2][ty+2];
    tiles[8]=tsrc[tx+3][ty+2];
    //-----------------------------------------------------
    tiles[14]=tsrc[tx][ty+3];
    tiles[6]=tsrc[tx+1][ty+3];
    tiles[2]=tsrc[tx+2][ty+3];
    tiles[10]=tsrc[tx+3][ty+3];
    //-----------------------------------------------------
    tx=4;
    ty=10;
    tiles[16]=tsrc[tx][ty];
    tiles[17]=tsrc[tx+1][ty];
    tiles[18]=tsrc[tx][ty+1];
    tiles[19]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tx=4;
    ty=12;
    tiles[20]=tsrc[tx][ty];
    tiles[21]=tsrc[tx+1][ty];
  }
  public void initLambda() {
    // updater=lightUpdater;
    displayUpdater=(world,in,x,y)-> {
      fullBlockDisplayUpdater.update(world,in,x,y);
      int typeCache=0;
      if(WoodPlatform.isWoodPlatform(pc.woodPlatform,world.getBlock(x-1,y))) typeCache+=2;// left
      if(WoodPlatform.isWoodPlatform(pc.woodPlatform,world.getBlock(x+1,y))) typeCache+=1;// right
      in.displayType[2]=typeCache;
    };
    displayer=(r,p,world,in,x,y)-> {
      fullBlockDisplayer.display(r,p,world,in,x,y);
      int ti=in.displayType[2];
      if(ti!=0) {
        if((ti&1)!=0) r.tile(in.type.tiles[20],x,y);
        if((ti&2)!=0) r.tile(in.type.tiles[21],x,y);
      }
    };
  }
}