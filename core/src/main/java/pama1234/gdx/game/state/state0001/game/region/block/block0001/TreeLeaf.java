package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class TreeLeaf extends MetaBlock{
  public int maxLogCount=512;
  public TreeLeaf(MetaBlockCenter0001 pc,int id) {
    super(pc,"tree-leaf",id,25,3,(in,type)-> {//change to me
      in.light.set(16);
    },(in,type)-> {//change from me
      in.intData=null;
    });
    destroyTime=15;
    buildTime=8;
    setLightIntensity(2);
    fullBlockType=1;
    // fullBlock=false;
    // initFullBlockLambda();
    initTreeLeafLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.leaf,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=16,ty=0;
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
    tx=16;
    ty=7;
    //-----------------------------------------------------
    tiles[16]=tsrc[tx][ty];
    tiles[17]=tsrc[tx+1][ty];
    tiles[18]=tsrc[tx][ty+1];
    tiles[19]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tx=18;
    ty=7;
    //-----------------------------------------------------
    tiles[20]=tsrc[tx][ty];
    tiles[21]=tsrc[tx+1][ty];
    tiles[22]=tsrc[tx][ty+1];
    tiles[23]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tiles[24]=tsrc[17][4];
  }
  @Override
  public void initBlock(Block in) {
    if(in.intData==null) in.intData=new int[] {maxLogCount,0};
  }
  public boolean isLeafAndNotNull(Block tb) {
    return tb!=null&&tb.type==pc.leaf;
  }
  public void testCount(Block in,Block tb) {
    int ti;
    int tc=in.intData[1];
    if(isLeafAndNotNull(tb)&&tb.intData[0]>tc) {
      ti=tb.intData[0]-tc;
      ti/=4;
      in.intData[1]+=ti;
      tb.intData[1]-=ti;
    }
  }
  public void initTreeLeafLambda() {
    updater=(in,x,y)-> {
      lightUpdater.update(in,x,y);
      int[] array=in.intData;
      // if(array==null) return;
      if(pc.pw.data.time%2==0) {
        array[0]+=array[1];
        array[1]=0;
        return;
      }
      World0001 world=pc.pw;
      Block tb=world.getBlock(x,y+1);
      if(tb!=null&&tb.type==pc.log) array[0]=maxLogCount;
      else array[1]-=2;
      //------------------------------------------------------------
      testCount(in,tb);
      testCount(in,world.getBlock(x,y-1));
      testCount(in,world.getBlock(x+1,y));
      testCount(in,world.getBlock(x-1,y));
      //------------------------------------------------------------
      if(array[0]<=0) world.destroyBlock(in,x,y);
      else if(array[0]>maxLogCount) array[0]=maxLogCount;
    };
    displayUpdater=(in,x,y)-> {
      World0001 world=in.type.pc.pw;
      int typeCache=0;
      if(!TreeLog.isTreeLeaf(world.getBlock(x,y-1),this)) typeCache+=1;// up
      if(!TreeLog.isTreeLeaf(world.getBlock(x,y+1),this)) typeCache+=2;// down
      if(!TreeLog.isTreeLeaf(world.getBlock(x-1,y),this)) typeCache+=4;// left
      if(!TreeLog.isTreeLeaf(world.getBlock(x+1,y),this)) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(!TreeLog.isTreeLeaf(world.getBlock(x-1,y-1),this)) typeCache+=1;// up left
      if(!TreeLog.isTreeLeaf(world.getBlock(x-1,y+1),this)) typeCache+=2;// down left
      if(!TreeLog.isTreeLeaf(world.getBlock(x+1,y+1),this)) typeCache+=4;// down right
      if(!TreeLog.isTreeLeaf(world.getBlock(x+1,y-1),this)) typeCache+=8;// up right
      in.displayType[1]=typeCache;
      typeCache=0;
      if(TreeLog.isTreeLog(world.getBlock(x,y+1),pc.log)) typeCache+=1;
      in.displayType[2]=typeCache;
      //---
      if(in.updateLighting) lightingUpdate(in,x,y,world);
      // in.light.update();
    };
    displayer=(r,p,in,x,y)-> {
      // World0001 world=in.type.pc.pw;
      r.tint(
        getLighting(in.light.r()),
        getLighting(in.light.g()),
        getLighting(in.light.b()));
      int tp_0=in.displayType[0];
      // if(tp_0!=0)
      // int tw=world.settings.blockWidth,
      //   th=world.settings.blockHeight;
      r.tile(in.type.tiles[tp_0],x,y);
      int tp_1=in.displayType[1];
      // if(tp_1!=0) {
      TextureRegion tr;
      if((tp_0&2)+(tp_0&8)==0) {// down and right is leaf
        if((tp_1&4)!=0) tr=in.type.tiles[16];// down right is not leaf
        else tr=in.type.tiles[20];
        r.tile(tr,x,y);
      }
      if((tp_0&2)+(tp_0&4)==0) {// down and left is leaf
        if((tp_1&2)!=0) tr=in.type.tiles[17];// down left is not leaf
        else tr=in.type.tiles[21];
        r.tile(tr,x,y);
      }
      if((tp_0&1)+(tp_0&8)==0) {// up and right is leaf
        if((tp_1&8)!=0) tr=in.type.tiles[18];// up right is not leaf
        else tr=in.type.tiles[22];
        r.tile(tr,x,y);
      }
      if((tp_0&1)+(tp_0&4)==0) {// up and left is leaf
        if((tp_1&1)!=0) tr=in.type.tiles[19];// up left is not leaf
        else tr=in.type.tiles[23];
        r.tile(tr,x,y);
      }
      // }
      int tp_2=in.displayType[2];
      if(tp_2!=0) r.tile(in.type.tiles[24],x,y);
      if(!pc.pw.pg.debugGraphics) return;
      r.end();
      p.textScale(0.5f);
      p.text(Integer.toString(in.intData[0]),x,y);
      p.textScale(1);
      r.begin();
    };
  }
}