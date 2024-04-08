package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class Door extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public boolean doorState;
  public Door(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id,boolean opened) {
    super(pc,"door",id,opened?12:6,1,(world,in,type,x,y)-> {//change to me
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
    });
    attr.blockType=woodType;
    attr.fullBlock=false;
    attr.destroyTime=120;
    attr.buildTime=60;
    attr.width=2;
    attr.height=3;
    // overrideFullBlock=true;
    initLambda();

    doorState=opened;
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=7,ty=10;
    int size=rttr.tiles.length/2;
    if(doorState) {
      rttr.tiles[0]=tsrc[tx][ty];
      rttr.tiles[1]=tsrc[tx+1][ty];
      rttr.tiles[2]=tsrc[tx][ty+1];
      rttr.tiles[3]=tsrc[tx+1][ty+1];
      rttr.tiles[4]=tsrc[tx][ty+2];
      rttr.tiles[5]=tsrc[tx+1][ty+2];
    }else {
      rttr.tiles[0]=tsrc[tx-1][ty];
      rttr.tiles[1]=tsrc[tx-1][ty+1];
      rttr.tiles[2]=tsrc[tx+1][ty+2];
    }
    for(int i=0;i<size;i++) {
      rttr.tiles[size+i]=new TextureRegion(rttr.tiles[i]);
      rttr.tiles[size+i].flip(true,false);
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
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.door,1)};
  }
  public void initLambda() {
    rttr.displayUpdater=(world,in,x,y)-> {
      defaultDisplayUpdater.update(world,in,x,y);
      if(in.intData[0]==0) in.displayType[0]=in.xOff+in.yOff*in.type.attr.width;
      else in.displayType[0]=rttr.tiles.length/2+(doorState?1:0-in.xOff)+in.yOff*in.type.attr.width;
    };
  }
}