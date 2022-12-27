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
  public Region(Screen0011 p,RegionCenter pr,FileHandle dataLocation) {
    super(p);
    this.pr=pr;
    this.dataLocation=dataLocation;
  }
  @Override
  public void load() {
    pr.generator.get(this);
  }
  @Override
  public void save() {}
  @Override
  public void update() {
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        // int tcw=pr.chunkWidth*pr.pw.blockWidth,
        //   tch=pr.chunkHeight*pr.pw.blockHeight;
        // int tcx=(x*pr.regionWidth+i)*tcw,
        //   tcy=(y*pr.regionHeight+j)*tch;
        // if(!p.cam2d.boxIntersect(tcx,tcy,tcw,tch)) continue;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            // if(!blockType.display) continue;
            int tx=((x*pr.regionWidth+i)*pr.chunkWidth+n),
              ty=((y*pr.regionHeight+j)*pr.chunkWidth+m);
            // if(!p.cam2d.boxIntersect(tx,ty,pr.pw.blockWidth,pr.pw.blockHeight)) continue;
            blockType.update(block,tx,ty);
          }
        }
      }
    }
  }
  @Override
  public void display() {
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        int tcw=pr.chunkWidth*pr.pw.blockWidth,
          tch=pr.chunkHeight*pr.pw.blockHeight;
        int tcx=(x*pr.regionWidth+i)*tcw,
          tcy=(y*pr.regionHeight+j)*tch;
        if(!p.cam2d.boxIntersect(tcx,tcy,tcw,tch)) continue;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            if(!blockType.display) continue;
            int txi=(x*pr.regionWidth+i)*pr.chunkWidth+n,
              tyi=(y*pr.regionHeight+j)*pr.chunkWidth+m;
            int tx=txi*pr.pw.blockWidth,
              ty=tyi*pr.pw.blockHeight;
            if(!p.cam2d.boxIntersect(tx,ty,pr.pw.blockWidth,pr.pw.blockHeight)) continue;
            blockType.updateDisplay(block,txi,tyi);
            blockType.display(p,block,tx,ty);
          }
        }
      }
    }
    p.noTint();
  }
  public void updateDisplay() {
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            int tx=((x*pr.regionWidth+i)*pr.chunkWidth+n),
              ty=((y*pr.regionHeight+j)*pr.chunkWidth+m);
            blockType.updateDisplay(block,tx,ty);
          }
        }
      }
    }
  }
}
