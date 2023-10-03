package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.math.UtilMath;

public class Sapling extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public Sapling(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"sapling",id,1,0,(world,in,type,x,y)-> {//change to me
      // in.light.set(16);
      in.intData=new int[1];
    },(world,in,type,x,y)-> {//change from me
      in.intData=null;
    });
    attr.blockType=woodType;
    attr.destroyTime=10;
    attr.buildTime=5;
    attr.fullBlock=false;
    // initFullBlockLambda();
    initSaplingLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.sapling,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[0]=tsrc[6][6];
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null) in.intData=new int[1];
  }
  public void initSaplingLambda() {
    rttr.updater=(world,in,x,y)-> {
      lightUpdater.update(world,in,x,y);
      if(in.intData[0]<200) {
        in.intData[0]+=1;
        if(in.intData[0]==1) {
          Block tb=world.getBlock(x,y+1);
          if(tb!=null&&tb.type!=pc.dirt) world.r.destroyBlock(in,x,y);
        }
      }else {
        in.intData[0]=0;
        int th=4+UtilMath.floor(world.random(12));
        int ty=y-th;
        int n=1+UtilMath.floor(world.random(2));
        int m=1+UtilMath.floor(world.random(2));
        for(int i=1;i<th;i++) {
          Block block=world.getBlock(x,y-i);
          if(block==null||block.type!=pc.air) return;
        }
        for(int i=-n;i<=n;i++) for(int j=-m;j<=m;j++) {
          Block block=world.getBlock(x+i,ty+j);
          if(block==null||block.type!=pc.air) return;
        }
        for(int i=0;i<th;i++) world.r.setBlock(pc.log,x,y-i);
        for(int i=-n;i<=n;i++) for(int j=-m;j<=m;j++) world.r.setBlock(pc.leaf,x+i,ty+j);
      }
    };
  }
}