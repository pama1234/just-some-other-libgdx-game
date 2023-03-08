package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;

public class Torch extends MetaBlock{
  public Torch(MetaBlockCenter0001 pc,int id) {
    super(pc,"torch",id,4,1,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
    });
    destroyTime=15;
    buildTime=8;
    setLightIntensity(64);
    fullBlock=false;
    initTorchLambda();
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.torch,1)};
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null) in.intData=new int[1];
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tiles[0]=tsrc[8][8];
    tiles[1]=tsrc[6][9];
    tiles[2]=tsrc[7][9];
    tiles[3]=tsrc[8][9];
  }
  public void initTorchLambda() {
    updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      if(Block.isFullBlockOrNull(world.getBlock(x,y+1))) in.intData[0]=0;
      else if(Block.isFullBlockOrNull(world.getBlock(x-1,y))) in.intData[0]=1;
      else if(Block.isFullBlockOrNull(world.getBlock(x+1,y))) in.intData[0]=2;
      else if(Block.isFullBlockOrNull(world.getBlock(x,y-1))) in.intData[0]=3;
      else world.r.destroyBlock(in,x,y);
    };
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      in.displayType[0]=in.intData[0];
    };
  }
}