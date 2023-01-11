package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Workbench extends MetaBlock{
  public Workbench(MetaBlockCenter0001 pc,int id) {
    super(pc,"workbench",id,1,2,(in,type)-> {//change to workbench
      in.light.set(16);
    },(in,type)-> {//change from workbench
    });
    destroyTime=120;
    buildTime=10;
    // initFullBlockLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.workbench,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[6][8];
  }
}
