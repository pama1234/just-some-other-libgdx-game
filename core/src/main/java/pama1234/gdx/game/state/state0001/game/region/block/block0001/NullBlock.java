package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class NullBlock extends MetaBlock{
  public int count;
  public NullBlock(MetaBlockCenter0001 pc,int id) {
    super(pc,"null-block",id,20,2,(in,type)-> {//change to stone
      // in.lighting=16;
      in.light.set(16);
    },(in,type)-> {//change from stone
    });
    blockType=stoneType;
    destroyTime=Integer.MAX_VALUE;
    buildTime=Integer.MAX_VALUE;
    initLambda();
  }
  @Override
  public void initItemDrop() {
    // itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.stone,1)};
    itemDrop=new ItemDropAttr[0];
  }
  @Override
  public void init() {
    // if(tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=0,ty=8;
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
    //----------------------------------------------------- //TODO
    tiles[16]=tsrc[4][8];
    tiles[17]=tsrc[5][8];
    tiles[18]=tsrc[4][9];
    tiles[19]=tsrc[5][9];
  }
  public void initLambda() {
    updater=(w,in,x,y)-> {
      count+=1;
      // lightUpdater.update(w,in,x,y);
    };
    displayUpdater=(world,in,x,y)-> {
      count+=1;
      int typeCache=0;
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x,y-1))) typeCache+=1;// up
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x,y+1))) typeCache+=2;// down
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y))) typeCache+=4;// left
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y))) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y-1))) typeCache+=1;
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y+1))) typeCache+=2;
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y+1))) typeCache+=4;
      if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y-1))) typeCache+=8;
      in.displayType[1]=typeCache;
      //---
      if(in.updateLighting) lightingUpdate(in,x,y,world);
    };
    displayer=(r,p,in,x,y)-> {
      count+=1;
      // if(in.light.isDark())
      // r.tint(getLighting(in.light.r()),0,0);
      r.tint(255,0,0);
      int tp_0=in.displayType[0];
      r.tile(in.type.tiles[tp_0],x,y);
      int tp_1=in.displayType[1];
      if(tp_1!=0) {
        if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) r.tile(in.type.tiles[16],x,y);
        if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) r.tile(in.type.tiles[17],x,y);
        if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) r.tile(in.type.tiles[18],x,y);
        if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) r.tile(in.type.tiles[19],x,y);
      }
    };
  }
}