package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.player.BlockPointer;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class RegionWrapper{
  public World0001 pw;
  public RegionWrapper(World0001 pw) {
    this.pw=pw;
  }
  public void destroyBlock(MainPlayer player,Block block,int x,int y) {
    placeBlock(player,block,pw.metaBlocks.air,x,y);
  }
  public void destroyBlock(BlockPointer bp,Block block,int x,int y) {
    placeBlock(bp,block,pw.metaBlocks.air,x,y);
  }
  public void destroyBlock(Block block,int x,int y) {
    placeBlock(block,pw.metaBlocks.air,x,y);
  }
  public void placeBlock(MainPlayer player,Block block,MetaBlock in,int x,int y) {
    placeBlock(block,in,x,y);
  }
  public void placeBlock(BlockPointer bp,Block block,MetaBlock in,int x,int y) {
    placeBlock(block,in,x,y);
  }
  public void placeBlock(Block block,MetaBlock in,int x,int y) {
    if(block.xOff!=0||block.yOff!=0) destroyBlock(
      block.origin==null?pw.getBlock(x-block.xOff,y-block.yOff):block.origin,x-block.xOff,y-block.yOff);
    if(block.type.width==1&&block.type.height==1) block.doItemDrop(pw.p,x,y,in.empty);
    removeAreaIfOff(block,x,y,in);
    putIfOffBlock(block,x,y,in);
    block.type(in);
  }
  public void removeAreaIfOff(Block block,int x,int y,MetaBlock in) {
    MetaBlock type=block.type;
    if(type!=null&&(type.width>1||type.height>1)) {
      block.doItemDrop(pw.p,x,y,in.empty);
      allOffAreaToAir(x,y,type);
    }
  }
  public void testAndRemoveOffBlock(MetaBlock type,int x,int y,MetaBlock in) {
    for(int i=0;i<type.width;i++) for(int j=0;j<type.height;j++) {
      int tx=x+i,
        ty=y+j;
      Block blockOff=pw.getBlock(tx,ty);
      if(blockOff.xOff!=0||blockOff.yOff!=0) removeAreaIfOff(blockOff,tx-blockOff.xOff,ty-blockOff.yOff,in);
      else if(blockOff.type.width>1||blockOff.type.height>1) removeAreaIfOff(blockOff,tx,ty,in);
    }
  }
  public void allOffAreaToAir(int x,int y,MetaBlock type) {
    for(int i=0;i<type.width;i++) for(int j=0;j<type.height;j++) {
      int tx=x+i,
        ty=y+j;
      Block blockOff=pw.getBlock(tx,ty);
      blockOff.type(pw.metaBlocks.air);
      blockOff.clearOrigin();
    }
  }
  public void putIfOffBlock(Block block,int x,int y,MetaBlock in) {
    if(in!=null&&(in.width>1||in.height>1)) {
      for(int i=0;i<in.width;i++) for(int j=0;j<in.height;j++) {
        int tx=x+i,
          ty=y+j;
        Block blockOff=pw.getBlock(tx,ty);
        testAndRemoveOffBlock(blockOff.type,tx,ty,in);
        blockOff.doItemDrop(pw.p,tx,ty,in.empty);
        blockOff.type(in);
        blockOff.origin(block,i,j);
      }
    }
  }
  public void setBlock(Block block,MetaBlock in,int x,int y) {
    block.type(in);
  }
  public void setBlock(MetaBlock in,int x,int y) {
    setBlock(pw.getBlock(x,y),in,x,y);
  }
}