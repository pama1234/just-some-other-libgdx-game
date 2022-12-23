package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Dirt extends MetaBlock{
  public Dirt(MetaBlockCenter0001 pc) {
    super(pc,"dirt",new TextureRegion[20],2,(in,type)-> {//change to dirt
    },(in,type)-> {//change from dirt
    });
    initLambda();
  }
  public void initLambda() {
    updater=(in,x,y)-> {
      // if(in.displayType==null) return;//TODO
      int typeCache=0;
      if(Block.isEmpty(pc.pw.regions.getBlock(x,y-1))) typeCache+=1;// up
      if(Block.isEmpty(pc.pw.regions.getBlock(x,y+1))) typeCache+=2;// down
      if(Block.isEmpty(pc.pw.regions.getBlock(x-1,y))) typeCache+=4;// left
      if(Block.isEmpty(pc.pw.regions.getBlock(x+1,y))) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(Block.isEmpty(pc.pw.regions.getBlock(x-1,y-1))) typeCache+=1;
      if(Block.isEmpty(pc.pw.regions.getBlock(x-1,y+1))) typeCache+=2;
      if(Block.isEmpty(pc.pw.regions.getBlock(x+1,y+1))) typeCache+=4;
      if(Block.isEmpty(pc.pw.regions.getBlock(x+1,y-1))) typeCache+=8;
      in.displayType[1]=typeCache;
    };
    displayer=(pIn,in,x,y)-> {
      // if(in.displayType==null) return;//TODO
      int tp_0=in.displayType[0];
      pIn.image(pc.dirt.tiles[tp_0],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
      int tp_1=in.displayType[1];
      if(tp_1!=0) {
        if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) pIn.image(pc.dirt.tiles[16],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
        if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) pIn.image(pc.dirt.tiles[17],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
        if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) pIn.image(pc.dirt.tiles[18],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
        if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) pIn.image(pc.dirt.tiles[19],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
      }
    };
  }
  @Override
  public void init() {
    if(tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[15]=tsrc[0][0];
    tiles[7]=tsrc[1][0];
    tiles[3]=tsrc[2][0];
    tiles[11]=tsrc[3][0];
    //-----------------------------------------------------
    tiles[13]=tsrc[0][1];
    tiles[5]=tsrc[1][1];
    tiles[1]=tsrc[2][1];
    tiles[9]=tsrc[3][1];
    //-----------------------------------------------------
    tiles[12]=tsrc[0][6];
    tiles[4]=tsrc[1][6];
    tiles[0]=tsrc[2][6];
    tiles[8]=tsrc[3][6];
    //-----------------------------------------------------
    tiles[14]=tsrc[0][7];
    tiles[6]=tsrc[1][7];
    tiles[2]=tsrc[2][7];
    tiles[10]=tsrc[3][7];
    //----------------------------------------------------- TODO
    tiles[16]=tsrc[4][0];
    tiles[17]=tsrc[5][0];
    tiles[18]=tsrc[4][1];
    tiles[19]=tsrc[5][1];
  }
}
