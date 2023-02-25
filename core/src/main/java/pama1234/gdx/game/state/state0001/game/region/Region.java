package pama1234.gdx.game.state.state0001.game.region;

import static pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil.kryo;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.KryoUtil;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.Chunk.BlockData;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave{
  public RegionCenter pr;
  public FileHandle dataLocation;
  //---
  public int x,y;
  @Tag(0)
  public Chunk[][] data;
  public GamePointEntity<?>[] entities;
  public boolean keep;//用于测试是否需要加载卸载
  public Region() {//只能用于kryo
    super(null);
  }
  public Region(Screen0011 p,RegionCenter pr,int x,int y,FileHandle dataLocation) {
    super(p);
    this.pr=pr;
    this.x=x;
    this.y=y;
    this.dataLocation=dataLocation;
  }
  public boolean posIs(int a,int b) {
    return x==a&&y==b;
  }
  @Override
  public void load() {//TODO
    if(p.settings.debugInfo) System.out.println("inner load region "+x+" "+y);
    WorldKryoUtil.regionInstance=this;
    KryoUtil.load(kryo,dataLocation,Region.class);
    WorldKryoUtil.regionInstance=null;
  }
  @Override
  public void save() {//TODO
    if(p.settings.debugInfo) System.out.println("inner save region "+x+" "+y);
    boolean tb_1=false;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      BlockData[][] blockData=data[i][j].data;
      boolean tb_2=true;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        if(!blockData[n][m].block.changed) blockData[n][m]=null;
        else tb_2=false;
      }
      if(tb_2) data[i][j]=null;
      else tb_1=true;
    }
    if(tb_1) KryoUtil.save(kryo,dataLocation,this);
  }
  @Override
  public void update() {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(!chunk.update) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.chunkWidth,
        ty_2=(ty_1+j)*pr.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // if(tbd==null) continue;
        Block block=tbd==null?pr.nullBlock:tbd.block;
        // Block block=tbd.block;
        MetaBlock blockType=block.type;
        if(blockType==null) continue;
        int tx_3=tx_2+n,
          ty_3=ty_2+m;
        blockType.update(pr.pw,block,tx_3,ty_3);
      }
    }
  }
  @Override
  public void display() {
    forLoopDisplay();
  }
  public void forLoopDisplay() {
    int tcw=pr.chunkWidth*pr.pw.settings.blockWidth,
      tch=pr.chunkHeight*pr.pw.settings.blockHeight;
    int rx=x*pr.regionWidth,
      ry=y*pr.regionHeight;
    if(!p.cam2d.boxIntersect(rx*tcw,ry*tch,tcw*pr.regionWidth,tch*pr.regionHeight)) return;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      BlockData[][] blockData=chunk.data;
      int tcx=(rx+i)*tcw,
        tcy=(ry+j)*tch;
      if(!p.cam2d.boxIntersect(tcx,tcy,tcw,tch)) continue;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        Block block=blockData[n][m].block;
        MetaBlock blockType=block.type;
        int txi=(rx+i)*pr.chunkWidth+n,
          tyi=(ry+j)*pr.chunkHeight+m;
        int tx=txi*pr.pw.settings.blockWidth,
          ty=tyi*pr.pw.settings.blockHeight;
        blockType.updateDisplay(pr.pw,block,txi,tyi);
        if(!blockType.display) continue;
        if(!p.cam2d.boxIntersect(tx,ty,pr.pw.settings.blockWidth,pr.pw.settings.blockHeight)) continue;
        blockType.display(pr.tilemapRenderer,p,block,tx,ty);
      }
    }
    p.noTint();
  }
  public void updateDisplay(long sleep) {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(!chunk.update) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.chunkWidth,
        ty_2=(ty_1+j)*pr.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // if(tbd==null) continue;
        Block block=tbd==null?pr.nullBlock:tbd.block;
        // Block block=tbd.block;
        MetaBlock blockType=block.type;
        if(blockType==null) continue;
        int tx=tx_2+n,
          ty=ty_2+m;
        blockType.updateDisplay(pr.pw,block,tx,ty);
      }
      try {
        Thread.sleep(sleep);
      }catch(InterruptedException e) {
        return;
      }
    }
  }
  public void updateDisplay() {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(!chunk.update||chunk.priority>pr.chunkUpdateDisplayDist) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.chunkWidth,
        ty_2=(ty_1+j)*pr.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // if(tbd==null) continue;
        Block block=tbd.block;
        MetaBlock blockType=block.type;
        if(blockType==null) continue;
        int tx=tx_2+n,
          ty=ty_2+m;
        blockType.updateDisplay(pr.pw,block,tx,ty);
      }
    }
  }
}