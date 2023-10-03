package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class Dirt extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public Dirt(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"dirt",id,20,2,(world,in,type,x,y)-> {//change to dirt
      // in.lighting=16;
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from dirt
    });
    attr.blockType=dirtType;
    attr.destroyTime=30;
    attr.buildTime=15;
    initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.dirt,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[15]=tsrc[0][0];
    rttr.tiles[7]=tsrc[1][0];
    rttr.tiles[3]=tsrc[2][0];
    rttr.tiles[11]=tsrc[3][0];
    //-----------------------------------------------------
    rttr.tiles[13]=tsrc[0][1];
    rttr.tiles[5]=tsrc[1][1];
    rttr.tiles[1]=tsrc[2][1];
    rttr.tiles[9]=tsrc[3][1];
    //-----------------------------------------------------
    rttr.tiles[12]=tsrc[0][6];
    rttr.tiles[4]=tsrc[1][6];
    rttr.tiles[0]=tsrc[2][6];
    rttr.tiles[8]=tsrc[3][6];
    //-----------------------------------------------------
    rttr.tiles[14]=tsrc[0][7];
    rttr.tiles[6]=tsrc[1][7];
    rttr.tiles[2]=tsrc[2][7];
    rttr.tiles[10]=tsrc[3][7];
    //----------------------------------------------------- TODO
    rttr.tiles[16]=tsrc[4][0];
    rttr.tiles[17]=tsrc[5][0];
    rttr.tiles[18]=tsrc[4][1];
    rttr.tiles[19]=tsrc[5][1];
  }
}