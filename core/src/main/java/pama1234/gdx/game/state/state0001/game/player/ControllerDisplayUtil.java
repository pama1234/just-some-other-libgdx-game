package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.GameDisplayUtil;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.player.ControllerUtil.ControllerBlockPointer;
import pama1234.math.UtilMath;

public class ControllerDisplayUtil{
  public static void drawSelectEntity(Screen0011 p,LivingEntity entity) {
    float tl=UtilMath.mag(entity.type.w,entity.type.h)/2f+2;
    float tcx=entity.cx(),tcy=entity.cy();
    p.tint(255,127);
    p.image(ImageAsset.select,tcx-tl,tcy-tl,tl,tl);
    p.image(ImageAsset.select,tcx+tl,tcy-tl,-tl,tl);
    p.image(ImageAsset.select,tcx-tl,tcy+tl,tl,-tl);
    p.image(ImageAsset.select,tcx+tl,tcy+tl,-tl,-tl);
    p.noTint();
  }
  public static void drawSelectBlock(Screen0011 p,ControllerBlockPointer selectBlock,float tw,float th) {
    MetaBlock type=selectBlock.block==null?null:selectBlock.block.type;
    switch(selectBlock.task) {
      case BlockPointer.idle: {
        p.beginBlend();
        p.fill(127,127);
        if(type!=null) GameDisplayUtil.boxStroke(p,1,
          (selectBlock.x-selectBlock.block.xOff)*tw,
          (selectBlock.y-selectBlock.block.yOff)*th,tw*type.width,th*type.height);
        else GameDisplayUtil.boxStroke(p,1,selectBlock.x*tw,selectBlock.y*th,tw,th);
        p.endBlend();
      }
        break;
      case BlockPointer.destroy: {
        p.tint(255,191);
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,type.destroyTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,
              0,ti.type.blockType.buildTime+type.destroyTime,0,7)],
            selectBlock.x*tw,selectBlock.y*th);
        }
      }
        break;
      case BlockPointer.use: {
        p.tint(255,191);
        p.image(
          ImageAsset.tiles[7][8],
          selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      default:
        break;
    }
    p.noTint();
  }
  public static void boxTwoLine(Screen0011 p,float r,float tx1,float ty1,float tw1,float th1,boolean a) {
    float tx2=tx1+tw1+r;
    float ty2=ty1+th1+r;
    tx1-=r;
    ty1-=r;
    tw1+=r*2;
    th1+=r*2;
    if(a) {
      p.rect(tx1,ty1,tw1,r);
      p.rect(tx1,ty2-r,tw1,r);
    }else {
      p.rect(tx1,ty1,r,th1);
      p.rect(tx2-r,ty1,r,th1);
    }
  }
  public static void drawSelectBlockTouchScreen(Screen0011 p,ControllerBlockPointer selectBlock,float tw,float th,float scale) {
    MetaBlock type=selectBlock.block==null?null:selectBlock.block.type;
    if(selectBlock.task!=BlockPointer.use) {
      float tr=scale<1?2:2/scale;
      float tf=2/scale;
      p.beginBlend();
      if(type!=null) {
        p.fill(127,191);
        boxTwoLine(p,tr,(selectBlock.x-selectBlock.block.xOff)*tw,(selectBlock.y-selectBlock.block.yOff-tf)*th,tw*type.width,(tf*2+type.height)*th,false);
        p.fill(127,191);
        boxTwoLine(p,tr,(selectBlock.x-selectBlock.block.xOff-tf)*tw,(selectBlock.y-selectBlock.block.yOff)*th,(tf*2+type.width)*tw,th*type.height,true);
      }else {
        p.fill(127,191);
        boxTwoLine(p,tr,selectBlock.x*tw,(selectBlock.y-tf)*th,tw,(1+tf*2)*th,false);
        p.fill(127,191);
        boxTwoLine(p,tr,(selectBlock.x-tf)*tw,selectBlock.y*th,(1+tf*2)*tw,th,true);
      }
      p.endBlend();
    }
    float tf_2=0.8f/scale;
    switch(selectBlock.task) {
      case BlockPointer.destroy: {
        p.tint(255,191);
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,type.destroyTime,0,7)],
          (selectBlock.x-tf_2)*tw,(selectBlock.y-tf_2)*th,(1+tf_2*2)*tw,(1+tf_2*2)*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,
              0,ti.type.blockType.buildTime+type.destroyTime,0,7)],
            (selectBlock.x-tf_2)*tw,(selectBlock.y-tf_2)*th,(1+tf_2*2)*tw,(1+tf_2*2)*th);
        }
      }
        break;
      case BlockPointer.use: {
        p.tint(255,191);
        p.image(
          ImageAsset.tiles[7][8],
          selectBlock.x*tw,selectBlock.y*th);
        // (selectBlock.x-tf_2)*tw,(selectBlock.y-tf_2)*th,(1+tf_2*2)*tw,(1+tf_2*2)*th);
      }
        break;
      default:
        break;
    }
    p.noTint();
  }
}
