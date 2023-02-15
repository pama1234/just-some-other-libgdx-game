package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.player.PlayerController.ControllerBlockPointer;
import pama1234.math.UtilMath;

public class ControllerDisplayUtil{
  public static void drawSelectEntity(Screen0011 p,LivingEntity entity) {
    // LivingEntity entity=selectEntity.entity;
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
    // int tw=player.pw.settings.blockWidth,
    //   th=player.pw.settings.blockHeight;
    switch(selectBlock.task) {
      case BlockPointer.idle: {
        p.fill(0,127);
        // float r=1;
        // float tx1=selectBlock.x*tw-r;
        // float ty1=selectBlock.y*th-r;
        // float tx2=(selectBlock.x+1)*tw+r;
        // float ty2=(selectBlock.y+1)*th+r;
        // float tw1=tw+r*2;
        // float th1=th+r*2;
        // p.rect(tx1,ty1,1,th1);
        // p.rect(tx1,ty1,tw1,1);
        // p.rect(tx2-1,ty1,1,th1);
        // p.rect(tx1,ty2-1,tw1,1);
        Game.boxStroke(p,1,selectBlock.x*tw,selectBlock.y*th,tw,th);
      }
        break;
      case BlockPointer.destroy: {
        p.tint(255,191);
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,
              0,ti.type.blockType.buildTime+selectBlock.block.type.destroyTime,0,7)],
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
  public static void drawSelectBlockTouchScreen(Screen0011 p,ControllerBlockPointer selectBlock,float tw,float th) {
    // int tw=player.pw.settings.blockWidth,
    //   th=player.pw.settings.blockHeight;
    switch(selectBlock.task) {
      case BlockPointer.idle: {
        p.fill(0,127);
        float r=1;
        float tx1=selectBlock.x*tw-r;
        float ty1=selectBlock.y*th-r;
        float tx2=(selectBlock.x+1)*tw+r;
        float ty2=(selectBlock.y+1)*th+r;
        float tw1=tw+r*2;
        float th1=th+r*2;
        p.rect(tx1,ty1,1,th1);
        p.rect(tx1,ty1,tw1,1);
        p.rect(tx2-1,ty1,1,th1);
        p.rect(tx1,ty2-1,tw1,1);
      }
        break;
      case BlockPointer.destroy: {
        p.tint(255,191);
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,
              0,ti.type.blockType.buildTime+selectBlock.block.type.destroyTime,0,7)],
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
}
