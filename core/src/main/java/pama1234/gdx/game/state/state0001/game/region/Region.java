package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave{
  public RegionCenter pr;
  public FileHandle dataLocation;
  //---
  public int x,y;
  public Chunk[][] data;
  public boolean flag;//用于测试是否需要加载卸载
  public Region(Screen0011 p,RegionCenter pr,FileHandle dataLocation) {
    super(p);
    this.pr=pr;
    this.dataLocation=dataLocation;
  }
  @Override
  public void load() {//TODO
    // pr.pool.get(this);
  }
  @Override
  public void save() {//TODO
  }
  public boolean posIs(int a,int b) {
    return x==a&&y==b;
  }
  @Override
  public void update() {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        if(!chunk.update) continue;
        Block[][] blockData=chunk.data;
        int tx_2=(tx_1+i)*pr.chunkWidth,
          ty_2=(ty_1+j)*pr.chunkHeight;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            int tx_3=tx_2+n,
              ty_3=ty_2+m;
            blockType.update(block,tx_3,ty_3);
          }
        }
      }
    }
  }
  @Override
  public void display() {
    forLoopDisplay();
  }
  public void forLoopDisplay() {
    int tcw=pr.chunkWidth*pr.pw.blockWidth,
      tch=pr.chunkHeight*pr.pw.blockHeight;
    int rx=x*pr.regionWidth,
      ry=y*pr.regionHeight;
    if(!p.cam2d.boxIntersect(rx*tcw,ry*tch,tcw*pr.regionWidth,tch*pr.regionHeight)) return;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        int tcx=(rx+i)*tcw,
          tcy=(ry+j)*tch;
        if(!p.cam2d.boxIntersect(tcx,tcy,tcw,tch)) continue;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            int txi=(rx+i)*pr.chunkWidth+n,
              tyi=(ry+j)*pr.chunkHeight+m;
            int tx=txi*pr.pw.blockWidth,
              ty=tyi*pr.pw.blockHeight;
            blockType.updateDisplay(block,txi,tyi);
            if(!blockType.display) continue;
            if(!p.cam2d.boxIntersect(tx,ty,pr.pw.blockWidth,pr.pw.blockHeight)) continue;
            blockType.display(p,block,tx,ty);
          }
        }
      }
    }
    p.noTint();
  }
  public void updateDisplay() {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        if(!chunk.update) continue;
        Block[][] blockData=chunk.data;
        int tx_2=(tx_1+i)*pr.chunkWidth,
          ty_2=(ty_1+j)*pr.chunkHeight;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            int tx=tx_2+n,
              ty=ty_2+m;
            blockType.updateDisplay(block,tx,ty);
          }
        }
      }
    }
  }
}
