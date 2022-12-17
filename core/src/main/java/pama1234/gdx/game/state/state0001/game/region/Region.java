package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave{
  public RegionCenter pr;
  public FileHandle dataLocation;
  //---
  public int x,y;
  public int chunkWidth=64,chunkHeight=64;
  public Chunk[][] data;
  public Region(Screen0011 p,RegionCenter pr,FileHandle dataLocation) {
    super(p);
    this.pr=pr;
    this.dataLocation=dataLocation;
  }
  @Override
  public void load() {}
  @Override
  public void save() {}
  @Override
  public void display() {
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        Block[][] blockData=chunk.data;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            MetaBlock blockType=block.type;
            if(!blockType.display) continue;
            p.image(ImageAsset.tiles[blockType.tileX][blockType.tileY],((x*pr.regionWidth+i)*chunkWidth+n)*pr.pw.blockWidth,((y*pr.regionHeight+j)*chunkWidth+m)*pr.pw.blockHeight);
          }
        }
      }
    }
  }
}
