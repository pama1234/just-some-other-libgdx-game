package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class Stone extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public Stone(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"stone",id,20,2,(world,in,type,x,y)-> {//change to stone
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from stone
    });
    attr.blockType=stoneType;
    attr.destroyTime=60;
    attr.buildTime=15;
    initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.stone,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=0,ty=8;
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
    //----------------------------------------------------- //TODO
    rttr.tiles[16]=tsrc[4][8];
    rttr.tiles[17]=tsrc[5][8];
    rttr.tiles[18]=tsrc[4][9];
    rttr.tiles[19]=tsrc[5][9];
  }
}
