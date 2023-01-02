package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.math.UtilMath;

public class Stone extends MetaBlock{
  public Stone(MetaBlockCenter0001 pc,int id) {
    super(pc,"stone",id,new TextureRegion[20],2,(in,type)-> {//change to stone
      in.lighting=16;
    },(in,type)-> {//change from stone
    });
    initLambda();
  }
  public void initLambda() {
    updater=doNothing;
    displayUpdater=fullBlockDisplayUpdater;
    displayer=fullBlockDisplayer;
  }
  @Override
  public void init() {
    // if(tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[15]=tsrc[0][8];
    tiles[7]=tsrc[1][8];
    tiles[3]=tsrc[2][8];
    tiles[11]=tsrc[3][8];
    //-----------------------------------------------------
    tiles[13]=tsrc[0][9];
    tiles[5]=tsrc[1][9];
    tiles[1]=tsrc[2][9];
    tiles[9]=tsrc[3][9];
    //-----------------------------------------------------
    tiles[12]=tsrc[0][10];
    tiles[4]=tsrc[1][10];
    tiles[0]=tsrc[2][10];
    tiles[8]=tsrc[3][10];
    //-----------------------------------------------------
    tiles[14]=tsrc[0][11];
    tiles[6]=tsrc[1][11];
    tiles[2]=tsrc[2][11];
    tiles[10]=tsrc[3][11];
    //----------------------------------------------------- TODO
    tiles[16]=tsrc[4][0];
    tiles[17]=tsrc[5][0];
    tiles[18]=tsrc[4][1];
    tiles[19]=tsrc[5][1];
  }
}
