package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class LightOre extends MetaBlock{
  public LightOre(MetaBlockCenter0001 pc,int id) {
    super(pc,"light-ore",id,24,4,(in,type)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(in,type)-> {//change from me
    });
    blockType=stoneType;
    // fullBlockType=FullBlockType.stoneType;
    // fullBlockType=FullBlockType.oreType;
    destroyTime=60;
    buildTime=15;
    setLightIntensity(4);
    // initFullBlockLambda();
    initLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.lightOre,1)};
  }
  @Override
  public void init() {
    // if(tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=9,ty=10;
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
    tx=9;
    ty=14;
    //-----------------------------------------------------
    tiles[16]=tsrc[tx][ty];
    tiles[17]=tsrc[tx+1][ty];
    tiles[18]=tsrc[tx][ty+1];
    tiles[19]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tx=11;
    ty=14;
    //-----------------------------------------------------
    tiles[20]=tsrc[tx][ty];
    tiles[21]=tsrc[tx+1][ty];
    tiles[22]=tsrc[tx][ty+1];
    tiles[23]=tsrc[tx+1][ty+1];
  }
  public static boolean isSame(Block in,LightOre type) {
    return in!=null&&in.type==type;
  }
  public void initLambda() {
    displayUpdater=(world,in,x,y)-> {
      int typeCache=0;
      if(isNotFullBlockOrNotSameType(world.getBlock(x,y-1),FullBlockType.stoneType)) typeCache+=1;// up
      if(isNotFullBlockOrNotSameType(world.getBlock(x,y+1),FullBlockType.stoneType)) typeCache+=2;// down
      if(isNotFullBlockOrNotSameType(world.getBlock(x-1,y),FullBlockType.stoneType)) typeCache+=4;// left
      if(isNotFullBlockOrNotSameType(world.getBlock(x+1,y),FullBlockType.stoneType)) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(isNotFullBlockOrNotSameType(world.getBlock(x-1,y-1),FullBlockType.stoneType)) typeCache+=1;
      if(isNotFullBlockOrNotSameType(world.getBlock(x-1,y+1),FullBlockType.stoneType)) typeCache+=2;
      if(isNotFullBlockOrNotSameType(world.getBlock(x+1,y+1),FullBlockType.stoneType)) typeCache+=4;
      if(isNotFullBlockOrNotSameType(world.getBlock(x+1,y-1),FullBlockType.stoneType)) typeCache+=8;
      in.displayType[1]=typeCache;
      //---
      typeCache=0;
      if(!isSame(world.getBlock(x,y-1),this)) typeCache+=1;// up
      if(!isSame(world.getBlock(x,y+1),this)) typeCache+=2;// down
      if(!isSame(world.getBlock(x-1,y),this)) typeCache+=4;// left
      if(!isSame(world.getBlock(x+1,y),this)) typeCache+=8;// right
      in.displayType[2]=typeCache;
      typeCache=0;
      if(!isSame(world.getBlock(x-1,y-1),this)) typeCache+=1;// up left
      if(!isSame(world.getBlock(x-1,y+1),this)) typeCache+=2;// down left
      if(!isSame(world.getBlock(x+1,y+1),this)) typeCache+=4;// down right
      if(!isSame(world.getBlock(x+1,y-1),this)) typeCache+=8;// up right
      in.displayType[3]=typeCache;
      //---
      if(in.updateLighting) lightingUpdate(in,x,y,world);
    };
    displayer=(r,p,in,x,y)-> {
      // if(in.light.isDark())
      r.tint(
        getLighting(in.light.r()),
        getLighting(in.light.g()),
        getLighting(in.light.b()));
      int tp_0=in.displayType[0];
      r.tile(pc.stone.tiles[tp_0],x,y);
      int tp_1=in.displayType[1];
      if(tp_1!=0) {
        if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) r.tile(pc.stone.tiles[16],x,y);
        if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) r.tile(pc.stone.tiles[17],x,y);
        if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) r.tile(pc.stone.tiles[18],x,y);
        if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) r.tile(pc.stone.tiles[19],x,y);
      }
      //---
      int tp_2=in.displayType[2];
      r.tile(in.type.tiles[tp_2],x,y);
      int tp_3=in.displayType[3];
      TextureRegion tr;
      if((tp_2&2)+(tp_2&8)==0) {// down and right is me
        if((tp_3&4)!=0) tr=in.type.tiles[16];// down right is not me
        else tr=in.type.tiles[20];
        r.tile(tr,x,y);
      }
      if((tp_2&2)+(tp_2&4)==0) {// down and left is me
        if((tp_3&2)!=0) tr=in.type.tiles[17];// down left is not me
        else tr=in.type.tiles[21];
        r.tile(tr,x,y);
      }
      if((tp_2&1)+(tp_2&8)==0) {// up and right is me
        if((tp_3&8)!=0) tr=in.type.tiles[18];// up right is not me
        else tr=in.type.tiles[22];
        r.tile(tr,x,y);
      }
      if((tp_2&1)+(tp_2&4)==0) {// up and left is me
        if((tp_3&1)!=0) tr=in.type.tiles[19];// up left is not me
        else tr=in.type.tiles[23];
        r.tile(tr,x,y);
      }
    };
    ;
  }
}