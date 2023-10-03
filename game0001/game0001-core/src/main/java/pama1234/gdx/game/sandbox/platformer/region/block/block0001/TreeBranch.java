package pama1234.gdx.game.sandbox.platformer.region.block.block0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class TreeBranch extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public TreeBranch(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"tree-branch",id,6,1,(world,in,type,x,y)-> {//change to branch
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from branch
    });
    attr.destroyTime=10;
    attr.buildTime=5;
    attr.fullBlock=false;
    initTreeBranchLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.branch,1)};
  }
  @Override
  public void init() {
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    rttr.tiles[3]=tsrc[18][4];
    rttr.tiles[1]=tsrc[19][4];
    rttr.tiles[2]=tsrc[18][5];
    rttr.tiles[0]=tsrc[19][5];
    rttr.tiles[4]=tsrc[19][6];
  }
  public void initTreeBranchLambda() {
    rttr.displayUpdater=(world,in,x,y)-> {
      int typeCache=0;
      if(TreeLog.isTreeLeaf(world.getBlock(x,y-1),pc.leaf)) typeCache+=1;// up
      boolean flag_1=isBranchExtend(world.getBlock(x-1,y),this,pc.log);// left
      boolean flag_2=isBranchExtend(world.getBlock(x+1,y),this,pc.log);// right
      if(flag_1||flag_2) {//nand left right
        if(!flag_1) typeCache+=2;// left
        if(!flag_2) typeCache=4;// right
      }
      in.displayType[0]=typeCache;

      if(in.updateLighting) lightingUpdate(in,x,y,world);
    };
  }
  public boolean isBranchExtend(Block in,TreeBranch branch,TreeLog log) {
    return in!=null&&(in.type==branch||(in.type==log&&in.displayType[0]>1));
  }
}
