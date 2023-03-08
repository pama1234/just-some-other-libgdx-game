package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Stone extends MetaBlock{
  public Stone(MetaBlockCenter0001 pc,int id) {
    super(pc,"stone",id,20,2,(world,in,type,x,y)-> {//change to stone
      // in.lighting=16;
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from stone
    });
    blockType=stoneType;
    destroyTime=60;
    buildTime=15;
    // setLightIntensity(2);
    initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.stone,1)};
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
}
