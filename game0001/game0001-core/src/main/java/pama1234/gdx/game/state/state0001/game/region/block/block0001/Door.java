package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class Door extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public boolean doorState;
  public Door(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id,boolean opened) {
    super(pc,"door",id,opened?12:6,1,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
    });
    blockType=woodType;
    fullBlock=false;
    destroyTime=120;
    buildTime=60;
    width=2;
    height=3;
    // overrideFullBlock=true;
    initLambda();
    //---
    doorState=opened;
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=7,ty=10;
    int size=tiles.length/2;
    if(doorState) {
      tiles[0]=tsrc[tx][ty];
      tiles[1]=tsrc[tx+1][ty];
      tiles[2]=tsrc[tx][ty+1];
      tiles[3]=tsrc[tx+1][ty+1];
      tiles[4]=tsrc[tx][ty+2];
      tiles[5]=tsrc[tx+1][ty+2];
    }else {
      tiles[0]=tsrc[tx-1][ty];
      tiles[1]=tsrc[tx-1][ty+1];
      tiles[2]=tsrc[tx+1][ty+2];
    }
    for(int i=0;i<size;i++) {
      tiles[size+i]=new TextureRegion(tiles[i]);
      tiles[size+i].flip(true,false);
    }
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null||in.intData.length<2) in.intData=new int[2];
    // in.intData[0]=1;
    in.changed=true;
  }
  @Override
  public void initItemDrop() {
    itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.door,1)};
  }
  public void initLambda() {
    displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      if(in.intData[0]==0) in.displayType[0]=in.xOff+in.yOff*in.type.width;
      else in.displayType[0]=tiles.length/2+(doorState?1:0-in.xOff)+in.yOff*in.type.width;
    };
  }
}