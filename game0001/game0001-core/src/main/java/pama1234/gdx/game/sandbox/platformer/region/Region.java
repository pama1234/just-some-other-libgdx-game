package pama1234.gdx.game.sandbox.platformer.region;

import static pama1234.gdx.game.sandbox.platformer.world.WorldKryoUtil.kryo;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import hhs.gdx.hslib.util.Rect;
import pama1234.gdx.SystemSetting;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.KryoUtil;
import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.region.Chunk.BlockData;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldKryoUtil;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave,Rect{
  public RegionCenter pr;
  public FileHandle dataLocation;

  public int x,y;
  @Tag(0)
  public Chunk[][] data;
  @Tag(1)
  public GamePointEntity<?>[] entities;
  // public LivingEntity[] entities;
  public boolean keep;//用于测试是否需要加载卸载
  @Deprecated
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
    if(SystemSetting.data.printLog) System.out.println("inner load region "+x+" "+y);
    WorldKryoUtil.regionInstance=this;
    KryoUtil.load(kryo,dataLocation,Region.class);
    WorldKryoUtil.regionInstance=null;
  }
  @Override
  public void save() {//TODO
    if(SystemSetting.data.printLog) System.out.println("inner save region "+x+" "+y);
    boolean needSaveChunk=removeUnchanged();
    if(!needSaveChunk) data=null;
    float w=pr.data.chunkWidth*pr.data.regionWidth*pr.pw.settings.blockWidth;
    float h=pr.data.chunkHeight*pr.data.regionHeight*pr.pw.settings.blockHeight;
    // System.out.println(w+" "+h);
    entities=pr.pw.entities.getAllInRect(x*w,y*h,w,h,true);
    if(SystemSetting.data.printLog) {
      System.out.println("entities="+entities.length);
      for(GamePointEntity<?> e:entities) System.out.println(e.getClass().getSimpleName()+" "+e.point.pos);
    }
    KryoUtil.save(kryo,dataLocation,this);
  }
  public boolean removeUnchanged() {
    boolean out=false;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      BlockData[][] blockData=data[i][j].data;
      boolean tb_2=true;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        if(!blockData[n][m].block.changed) blockData[n][m]=null;
        else tb_2=false;
      }
      if(tb_2) data[i][j]=null;
      else out=true;
    }
    return out;
  }
  @Override
  public void update() {
    int tx_1=x*pr.data.regionWidth,
      ty_1=y*pr.data.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(chunk==null) continue;
      if(!chunk.update) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.data.chunkWidth,
        ty_2=(ty_1+j)*pr.data.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // if(tbd==null) continue;
        Block block=tbd==null?pr.nullBlock:tbd.block;
        // Block block=tbd.block;
        MetaBlock<?,?> blockType=block.type;
        if(blockType==null) continue;
        int tx_3=tx_2+n,
          ty_3=ty_2+m;
        blockType.update(pr.pw,block,tx_3,ty_3);
      }
    }
  }
  public void netClientUpdate() {
    int tx_1=x*pr.data.regionWidth,
      ty_1=y*pr.data.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(chunk==null) continue;
      // if(!chunk.update) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.data.chunkWidth,
        ty_2=(ty_1+j)*pr.data.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // Block block=tbd==null?pr.nullBlock:tbd.block;
        Block block=tbd.block;
        int tx_3=tx_2+n,
          ty_3=ty_2+m;
        MetaBlock.lightUpdater.update(pr.pw,block,tx_3,ty_3);
      }
    }
  }
  @Override
  public void display() {
    forLoopDisplay();
  }
  public void forLoopDisplay() {
    World0001 world=pr.pw;
    int tcw=pr.data.chunkWidth*world.settings.blockWidth,
      tch=pr.data.chunkHeight*world.settings.blockHeight;
    int rx=x*pr.data.regionWidth,
      ry=y*pr.data.regionHeight;
    if(!p.cam2d.boxIntersect(rx*tcw,ry*tch,tcw*pr.data.regionWidth,tch*pr.data.regionHeight)) return;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      BlockData[][] blockData=chunk.data;
      int tcx=(rx+i)*tcw,
        tcy=(ry+j)*tch;
      if(!p.cam2d.boxIntersect(tcx,tcy,tcw,tch)) continue;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        Block block=blockData[n][m].block;
        MetaBlock<?,?> blockType=block.type;
        int txi=(rx+i)*pr.data.chunkWidth+n,
          tyi=(ry+j)*pr.data.chunkHeight+m;
        int tx=txi*world.settings.blockWidth,
          ty=tyi*world.settings.blockHeight;
        blockType.updateDisplay(world,block,txi,tyi);
        if(!blockType.attr.display) continue;
        if(!p.cam2d.boxIntersect(tx,ty,world.settings.blockWidth,world.settings.blockHeight)) continue;
        blockType.display(pr.tilemapRenderer,p,world,block,tx,ty);
      }
    }
    p.noTint();
  }
  public void updateDisplay(long sleep) {
    int tx_1=x*pr.data.regionWidth,
      ty_1=y*pr.data.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(!chunk.update) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.data.chunkWidth,
        ty_2=(ty_1+j)*pr.data.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        // if(tbd==null) continue;
        Block block=tbd==null?pr.nullBlock:tbd.block;
        // Block block=tbd.block;
        MetaBlock<?,?> blockType=block.type;
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
    int tx_1=x*pr.data.regionWidth,
      ty_1=y*pr.data.regionHeight;
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk chunk=data[i][j];
      if(!chunk.update||chunk.priority>pr.data.chunkUpdateDisplayDist) continue;//TODO
      BlockData[][] blockData=chunk.data;
      int tx_2=(tx_1+i)*pr.data.chunkWidth,
        ty_2=(ty_1+j)*pr.data.chunkHeight;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        if(tbd==null) continue;//TODO
        Block block=tbd.block;
        MetaBlock<?,?> blockType=block.type;
        if(blockType==null) continue;
        int tx=tx_2+n,
          ty=ty_2+m;
        blockType.updateDisplay(pr.pw,block,tx,ty);
      }
    }
  }
  @Override
  public float getX() {
    return x;
  }
  @Override
  public float getY() {
    return y;
  }
  @Override
  public float getWidth() {
    return 1;
  }
  @Override
  public float getHeight() {
    return 1;
  }
}