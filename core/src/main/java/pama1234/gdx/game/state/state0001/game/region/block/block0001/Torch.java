package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Torch extends MetaBlock{
  public Torch(MetaBlockCenter0001 pc,int id) {
    super(pc,"torch",id,1,0,(in,type)-> {//change to log
      in.light.set(16);
    },(in,type)-> {//change from log
    });
    destroyTime=15;
    buildTime=8;
    setLightIntensity(16);
    fullBlock=false;
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.torch,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[8][8];
  }
}
