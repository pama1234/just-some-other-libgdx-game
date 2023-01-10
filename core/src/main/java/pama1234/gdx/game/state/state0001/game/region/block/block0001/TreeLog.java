package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class TreeLog extends MetaBlock{
  public TreeLog(MetaBlockCenter0001 pc,int id) {
    super(pc,"tree-log",id,20,2,(in,type)-> {//change to log
      in.light.set(16);
    },(in,type)-> {//change from log
    });
    destroyTime=60;
    buildTime=10;
    // initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.log,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[2]=tsrc[16][4];
    tiles[1]=tsrc[16][5];
    tiles[4]=tsrc[17][5];
    tiles[3]=tsrc[16][6];
    tiles[0]=tsrc[17][6];
    tiles[5]=tsrc[18][6];
  }
}
