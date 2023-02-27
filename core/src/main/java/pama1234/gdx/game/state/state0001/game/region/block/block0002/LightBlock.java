package pama1234.gdx.game.state.state0001.game.region.block.block0002;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class LightBlock extends MetaBlock{
  public LightBlock(MetaBlockCenter0001 pc,int id) {
    super(pc,"light-block",id,20,2,(in,type)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(in,type)-> {//change from me
      in.intData=null;
    });
    blockType=stoneType;
    destroyTime=2;
    buildTime=2;
    setLightIntensity(16);
    initLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.lightBlock,1)};
  }
  @Override
  public void init() {
    // if(tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=4,ty=13;
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
    tx=8;
    ty=16;
    tiles[16]=tsrc[tx][ty];
    tiles[17]=tsrc[tx+1][ty];
    tiles[18]=tsrc[tx][ty+1];
    tiles[19]=tsrc[tx+1][ty+1];
  }
  @Override
  public void initBlock(Block in) {
    if(in.intData==null) {
      in.intData=new int[3];
      in.intData[0]=(int)pc.pw.random(256);
      in.intData[1]=(int)pc.pw.random(256);
      in.intData[2]=(int)pc.pw.random(256);
    }
  }
  public void initLambda() {
    // updater=lightUpdater;
    displayUpdater=fullBlockDisplayUpdater;
    displayer=(r,p,world,in,x,y)-> {
      // if(in.light.isDark())
      r.tint(
        getLighting(in.light.r()*in.intData[0]/255f),
        getLighting(in.light.g()*in.intData[1]/255f),
        getLighting(in.light.b()*in.intData[2]/255f));
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
