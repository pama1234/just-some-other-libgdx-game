package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class TreeLeaf extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public int maxLogCount=7;
  public TreeLeaf(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"tree-leaf",id,25,3,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
      in.intData=null;
    });
    attr.destroyTime=15;
    attr.buildTime=8;
    setLightIntensity(2);
    attr.fullBlockType=FullBlockType.leafType;
    // fullBlock=false;
    initTreeLeafLambda();
    attr.intData=new int[1];
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.leaf,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=16,ty=0;
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
    tx=16;
    ty=7;
    //-----------------------------------------------------
    rttr.tiles[16]=tsrc[tx][ty];
    rttr.tiles[17]=tsrc[tx+1][ty];
    rttr.tiles[18]=tsrc[tx][ty+1];
    rttr.tiles[19]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    tx=18;
    ty=7;
    //-----------------------------------------------------
    rttr.tiles[20]=tsrc[tx][ty];
    rttr.tiles[21]=tsrc[tx+1][ty];
    rttr.tiles[22]=tsrc[tx][ty+1];
    rttr.tiles[23]=tsrc[tx+1][ty+1];
    //-----------------------------------------------------
    rttr.tiles[24]=tsrc[17][4];
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null||in.intData.length<3) in.intData=new int[] {0,-60,-maxLogCount*2};
  }
  public boolean isTreeLeaf(Block tb) {
    return tb.type==this;
  }
  public void testCount(int[] array,Block tb) {
    if(tb==null) {
      array[1]=maxLogCount;
    }else if(isTreeLeaf(tb)) {
      int ti=tb.intData[0]-1;
      if(ti>array[1]) array[1]=ti;
    }
  }
  public void initTreeLeafLambda() {
    rttr.updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      int[] array=in.intData;
      if(array[2]<1) {
        if(array[2]%2==0) {
          array[1]=0;
          Block tb;
          testCount(array,tb=world.getBlock(x,y+1));
          testCount(array,world.getBlock(x,y-1));
          testCount(array,world.getBlock(x+1,y));
          testCount(array,world.getBlock(x-1,y));
          if(tb!=null&&tb.type==pc.log) array[0]=array[1]=maxLogCount;
        }else {
          array[0]=array[1];
        }
        array[2]++;
      }else {
        array[2]=0;
        array[0]=array[1];
        Block tb=world.getBlock(x,y+1);
        if(tb!=null) {
          if(tb.type==pc.log) array[0]=array[1]=maxLogCount;
          else if(array[0]<=0) world.r.destroyBlock(in,x,y);
        }
      }
    };
    rttr.displayUpdater=(world,in,x,y)-> {
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

      if(in.updateLighting) lightingUpdate(in,x,y,world);
    };
    rttr.displayer=(r,p,world,in,x,y)-> {
      r.tint(
        getLighting(in.light.r()),
        getLighting(in.light.g()),
        getLighting(in.light.b()));
      int tp_0=in.displayType[0];
      r.tile(in.type.rttr.tiles[tp_0],x,y);
      int tp_1=in.displayType[1];
      TextureRegion tr;
      if((tp_0&2)+(tp_0&8)==0) {// down and right is leaf
        if((tp_1&4)!=0) tr=in.type.rttr.tiles[16];// down right is not leaf
        else tr=in.type.rttr.tiles[20];
        r.tile(tr,x,y);
      }
      if((tp_0&2)+(tp_0&4)==0) {// down and left is leaf
        if((tp_1&2)!=0) tr=in.type.rttr.tiles[17];// down left is not leaf
        else tr=in.type.rttr.tiles[21];
        r.tile(tr,x,y);
      }
      if((tp_0&1)+(tp_0&8)==0) {// up and right is leaf
        if((tp_1&8)!=0) tr=in.type.rttr.tiles[18];// up right is not leaf
        else tr=in.type.rttr.tiles[22];
        r.tile(tr,x,y);
      }
      if((tp_0&1)+(tp_0&4)==0) {// up and left is leaf
        if((tp_1&1)!=0) tr=in.type.rttr.tiles[19];// up left is not leaf
        else tr=in.type.rttr.tiles[23];
        r.tile(tr,x,y);
      }
      int tp_2=in.displayType[2];
      if(tp_2!=0) r.tile(in.type.rttr.tiles[24],x,y);
      if(!pc.pwt.pc.pg.p.settings.debugGraphics||in.intData==null) return;
      r.end();
      p.textScale(0.5f);
      p.text(Integer.toString(in.intData[0]),x,y);
      p.text(Integer.toString(in.intData[1]),x,y+9);
      p.textScale(1);
      r.begin();
    };
  }
}