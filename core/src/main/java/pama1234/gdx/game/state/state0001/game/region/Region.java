package pama1234.gdx.game.state.state0001.game.region;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.util.entity.Entity;

public class Region extends Entity<Screen0011> implements LoadAndSave{
  public static final Kryo kryo=new Kryo();
  static {
    kryo.setDefaultSerializer(TaggedFieldSerializer.class);
    kryo.register(Region.class);
    kryo.register(Chunk[][].class);
    kryo.register(Chunk[].class);
    kryo.register(Chunk.class);
    kryo.register(Block[][].class);
    kryo.register(Block[].class);
    kryo.register(Block.class);
  }
  public RegionCenter pr;
  public FileHandle dataLocation;
  //---
  public int x,y;
  @Tag(0)
  public Chunk[][] data;
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
  @Override
  public void load() {//TODO
    if(p.debugInfo) System.out.println("inner load region "+x+" "+y);
    try(Input input=new Input(new FileInputStream(dataLocation.file()))) {
      Region out=kryo.readObject(input,Region.class);
      input.close();
      data=out.data;
      // for(int i=0;i<data.length;i++) {
      //   for(int j=0;j<data[i].length;j++) {
      //     Block[][] blockData=data[i][j].data;
      //     for(int n=0;n<blockData.length;n++) {
      //       for(int m=0;m<blockData[n].length;m++) {
      //         Block block=blockData[n][m];
      //         if(block!=null) p.println(i,j,n,m,block.typeId);
      //       }
      //     }
      //   }
      // }
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void save() {//TODO
    if(p.debugInfo) System.out.println("inner save region "+x+" "+y);
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Block[][] blockData=data[i][j].data;
        boolean flag=true;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            if(!blockData[n][m].changed) blockData[n][m]=null;
            else flag=false;
            // else p.println(i,j,n,m,blockData[n][m].type.name);
          }
        }
        if(flag) data[i][j]=null;
      }
    }
    try(Output output=new Output(new FileOutputStream(dataLocation.file()))) {
      kryo.writeObject(output,this);
      output.close();
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
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
        if(chunk==null||!chunk.update) continue;//TODO
        Block[][] blockData=chunk.data;
        int tx_2=(tx_1+i)*pr.chunkWidth,
          ty_2=(ty_1+j)*pr.chunkHeight;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            if(block==null) continue;//TODO 性能
            MetaBlock blockType=block.type;
            if(blockType==null) continue;
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
  public void updateDisplay(long sleep) {
    int tx_1=x*pr.regionWidth,
      ty_1=y*pr.regionHeight;
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        Chunk chunk=data[i][j];
        if(chunk==null||!chunk.update) continue;//TODO
        Block[][] blockData=chunk.data;
        int tx_2=(tx_1+i)*pr.chunkWidth,
          ty_2=(ty_1+j)*pr.chunkHeight;
        for(int n=0;n<blockData.length;n++) {
          for(int m=0;m<blockData[n].length;m++) {
            Block block=blockData[n][m];
            if(block==null) continue;//TODO 浪费性能
            MetaBlock blockType=block.type;
            if(blockType==null) continue;
            int tx=tx_2+n,
              ty=ty_2+m;
            blockType.updateDisplay(block,tx,ty);
          }
          // p.sleep(sleep);
          try {
            Thread.sleep(sleep);
          }catch(InterruptedException e) {
            // e.printStackTrace();
            sleep=0;//迅速完成执行
          }
        }
      }
    }
  }
}
