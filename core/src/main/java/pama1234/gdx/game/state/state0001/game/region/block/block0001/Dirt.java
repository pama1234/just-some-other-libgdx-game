package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Dirt extends MetaBlock{
  public Dirt(MetaBlockCenter0001 pc,int id) {
    super(pc,"dirt",id,20,2,(in,type)-> {//change to dirt
      // in.lighting=16;
      in.light.set(16);
    },(in,type)-> {//change from dirt
    });
    destroyTime=30;
    buildTime=15;
    initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.dirt,1)};
  }
  @Override
  public void init() {
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
