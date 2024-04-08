package pama1234.gdx.game.sandbox.platformer.region.block.block0002;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class ColorBlock extends MetaBlock<WorldType0001Base<?>,MetaBlockCenter0001<WorldType0001Base<?>>>{
  public ColorBlock(MetaBlockCenter0001<WorldType0001Base<?>> pc,int id) {
    super(pc,"color-block",id,20,2,(world,in,type,x,y)-> {//change to me
      // in.lighting=16;
      in.light.set(16);
    },(world,in,type,x,y)-> {//change from me
      in.intData=null;
    });
    attr.blockType=stoneType;
    attr.destroyTime=2;
    attr.buildTime=2;
    // setLightIntensity(2);
    initLambda();
  }
  @Override
  public void initItemDrop() {
    rttr.itemDrop=new ItemDropAttr[] {new ItemDropAttr(pc.pwt.metaItems.colorBlock,1)};
  }
  @Override
  public void init() {
    // if(rttr.tiles[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    int tx=4,ty=13;
    //-----------------------------------------------------
    rttr.tiles[15]=tsrc[tx][ty];
    rttr.tiles[7]=tsrc[tx+1][ty];
    rttr.tiles[3]=tsrc[tx+2][ty];
    rttr.tiles[11]=tsrc[tx+3][ty];
    //-----------------------------------------------------
    rttr.tiles[13]=tsrc[tx][ty+1];
    rttr.tiles[5]=tsrc[tx+1][ty+1];
    rttr.tiles[1]=tsrc[tx+2][ty+1];
    rttr.tiles[9]=tsrc[tx+3][ty+1];
    //-----------------------------------------------------
    rttr.tiles[12]=tsrc[tx][ty+2];
    rttr.tiles[4]=tsrc[tx+1][ty+2];
    rttr.tiles[0]=tsrc[tx+2][ty+2];
    rttr.tiles[8]=tsrc[tx+3][ty+2];
    //-----------------------------------------------------
    rttr.tiles[14]=tsrc[tx][ty+3];
    rttr.tiles[6]=tsrc[tx+1][ty+3];
    rttr.tiles[2]=tsrc[tx+2][ty+3];
    rttr.tiles[10]=tsrc[tx+3][ty+3];
    //-----------------------------------------------------
    tx=8;
    ty=16;
    rttr.tiles[16]=tsrc[tx][ty];
    rttr.tiles[17]=tsrc[tx+1][ty];
    rttr.tiles[18]=tsrc[tx][ty+1];
    rttr.tiles[19]=tsrc[tx+1][ty+1];
  }
  @Override
  public void initBlock(WorldBase2D<?> world,Block in) {
    if(in.intData==null) {
      in.intData=new int[3];
      in.intData[0]=(int)world.random(256);
      in.intData[1]=(int)world.random(256);
      in.intData[2]=(int)world.random(256);
    }
  }
  public void initLambda() {
    // updater=lightUpdater;
    rttr.displayUpdater=fullBlockDisplayUpdater;
    rttr.displayer=(r,p,world,in,x,y)-> {
      // if(in.light.isDark())
      r.tint(
        getLighting(in.light.r()*in.intData[0]/255f),
        getLighting(in.light.g()*in.intData[1]/255f),
        getLighting(in.light.b()*in.intData[2]/255f));
      int tp_0=in.displayType[0];
      r.tile(in.type.rttr.tiles[tp_0],x,y);
      int tp_1=in.displayType[1];
      if(tp_1!=0) {
        if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) r.tile(in.type.rttr.tiles[16],x,y);
        if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) r.tile(in.type.rttr.tiles[17],x,y);
        if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) r.tile(in.type.rttr.tiles[18],x,y);
        if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) r.tile(in.type.rttr.tiles[19],x,y);
      }
    };
  }
}
