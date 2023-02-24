package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class Door extends MetaBlock{
  public Door(MetaBlockCenter0001 pc,int id) {
    super(pc,"door",id,6,1,(in,type)-> {//change to me
      in.light.set(16);
    },(in,type)-> {//change from me
    });
    blockType=woodType;
    fullBlock=false;
    destroyTime=120;
    buildTime=60;
    width=2;
    height=3;
    initLambda();
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=7,ty=10;
    //-----------------------------------------------------
    tiles[0]=tsrc[tx][ty];
    tiles[1]=tsrc[tx+1][ty];
    tiles[2]=tsrc[tx][ty+1];
    tiles[3]=tsrc[tx+1][ty+1];
    tiles[4]=tsrc[tx][ty+2];
    tiles[5]=tsrc[tx+1][ty+2];
    //-----------------------------------------------------
  }
  @Override
  public void initBlock(Block in) {
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pw.metaItems.door,1)};
  }
  public void initLambda() {
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      in.displayType[0]=in.xOff+in.yOff*in.type.width;
    };
  }
}