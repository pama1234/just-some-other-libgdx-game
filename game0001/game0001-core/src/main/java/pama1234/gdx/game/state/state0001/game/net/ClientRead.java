package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import pama1234.gdx.game.state.state0001.game.KryoNetUtil;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.net.NetState.ClientToServer;
import pama1234.gdx.game.state.state0001.game.region.Chunk;
import pama1234.gdx.game.state.state0001.game.region.Chunk.BlockData;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldData;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.math.physics.Point;
import pama1234.util.function.ExecuteFunction;

public class ClientRead extends Thread{
  public ClientCore p;
  public Input input;
  public ExecuteFunction[] executeFs;
  public ClientRead(ClientCore p) {
    this.p=p;
    input=new Input(p.socketData.i);
    executeFs=new ExecuteFunction[] {this::readPlayerPos,this::readChunkData,this::readAuthInfo,this::readWorldData,this::readEntityData};
  }
  @Override
  public void run() {
    try {
      while(!p.stop) execute();
    }catch(RuntimeException e) {
      e.printStackTrace();
      p.stop=true;
    }
  }
  public void execute() {
    executeFs[input.readByteUnsigned()].execute();
  }
  public void readPlayerPos() {
    skip(8);
    float x=input.readFloat(),y=input.readFloat();
    Point point=p.world.yourself.point;
    if(point.pos.dist(x,y)>36) point.pos.set(x,y);
  }
  public void readChunkData() {
    MetaBlock<?,?>[] mblock=p.world.metaBlocks.array();
    MetaItem[] mitem=p.world.metaItems.array();
    int count=input.readInt();
    RegionCenter pr=p.world.regions;
    while(count>0) {
      count--;
      int cx=input.readInt(),cy=input.readInt();
      pr.removeChunk(cx,cy);
    }
    count=input.readInt();
    while(count>0) {
      count--;
      int cx=input.readInt(),cy=input.readInt();
      // System.out.println(count+" "+cx+" "+cy);
      Chunk chunk=KryoNetUtil.read(WorldKryoUtil.kryo,input,Chunk.class);
      BlockData[][] blockData=chunk.data;
      for(int i=0;i<blockData.length;i++) {
        for(int j=0;j<blockData[i].length;j++) {
          Block tb=blockData[i][j].block;
          tb.innerInit(p.world,mblock[tb.typeId]);
          ItemSlot[] itemData=tb.itemData;
          if(itemData!=null) for(ItemSlot e:itemData) if(e!=null) {
            Item ti=e.item;
            if(ti!=null) ti.type=mitem[ti.typeId];
          }
          tb.changed=true;
        }
      }
      pr.addChunk(cx,cy,chunk);
    }
    pr.refresh();
  }
  public void readEntityData() {
    int count=input.readInt();
    while(count>0) {
      count--;
      // int cx=input.readInt(),cy=input.readInt();
      // p.world.regions.removeChunk(cx,cy);
    }
    count=input.readInt();
    while(count>0) {
      count--;
      p.world.entities.accept(KryoNetUtil.read(WorldKryoUtil.kryo,input,GamePointEntity.class));
    }
  }
  public void readAuthInfo() {
    String serverInfo=input.readString();
    if(serverInfo.equals("v0.0.1-testWorld")) {
      p.clientWrite.state=ClientToServer.playerAuth;
    }else System.err.println("ClientRead.readAuthInfo() "+serverInfo);
  }
  public void readWorldData() {
    p.world.data=KryoNetUtil.read(WorldKryoUtil.kryo,input,WorldData.class);
  }
  public void skip(int in) {
    try {
      int available=input.available();
      if(available>in) input.skip((available/in)*in);
    }catch(KryoException|IOException e) {
      e.printStackTrace();
      p.stop=true;
    }
  }
}