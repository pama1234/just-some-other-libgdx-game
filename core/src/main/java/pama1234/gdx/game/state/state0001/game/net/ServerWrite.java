package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.state.state0001.game.KryoNetUtil;
import pama1234.gdx.game.state.state0001.game.net.NetState.ServerToClient;
import pama1234.gdx.game.state.state0001.game.net.ServerCore.ClientLink;
import pama1234.gdx.game.state.state0001.game.region.Chunk;
import pama1234.gdx.game.state.state0001.game.region.Region;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.util.function.ExecuteF;
import pama1234.util.wrapper.Center;

public class ServerWrite extends Thread{
  public ClientLink link;
  public ServerCore p;
  public Output output;
  public int sleep=-1;
  public ExecuteF[] executeFs;
  public Center<NetChunkData> chunks;
  public int state=ServerToClient.needAuth;
  public ServerWrite(ClientLink link,ServerCore p) {
    this.link=link;
    this.p=p;
    output=new Output(link.socketData.o);
    executeFs=new ExecuteF[] {this::writePlayerPos,this::writeChunks,this::writeNeedAuth,this::writeWorldData};
    chunks=new Center<>();
  }
  @Override
  public void run() {
    connect();
    try {
      while(!p.stop) {
        execute();
        if(sleep>=0) sleep(sleep);
      }
    }catch(RuntimeException|InterruptedException e) {
      e.printStackTrace();
      p.stop=true;
    }finally {
      link.socketData.dispose();
      p.serverWritePool.remove.add(this);
      disconnect();
    }
  }
  public void connect() {
    sleep=50;
  }
  public void execute() {
    updateChunks();
    //---
    if(chunks.add.size()>0||chunks.remove.size()>0) state=ServerToClient.chunkData;
    output.writeByte(state);
    executeFs[state].execute();
    output.flush();
  }
  public void writePlayerPos() {
    MassPoint point=link.player.point;
    output.writeFloat(point.pos.x);
    output.writeFloat(point.pos.y);
  }
  public void updateChunks() {
    float tcx=link.player.cx()/p.world.settings.blockWidth,
      tcy=link.player.cy()/p.world.settings.blockHeight;
    RegionCenter pr=p.world.regions;
    for(Region e:p.world.regions.list) {
      for(int i=0;i<e.data.length;i++) for(int j=0;j<e.data[i].length;j++) {
        Chunk chunk=e.data[i][j];
        float tx_2=((e.x*pr.data.regionWidth+(i+0.5f))*pr.data.chunkWidth),
          ty_2=((e.y*pr.data.regionHeight+(j+0.5f))*pr.data.chunkHeight);
        float dist=UtilMath.dist(tx_2,ty_2,tcx,tcy);
        if(dist<p.world.regions.data.netTransferDist) {
          boolean flag=true;
          int tx=e.x*pr.data.regionWidth+i,
            ty=e.y*pr.data.regionHeight+j;
          for(NetChunkData n:chunks.list) {
            if(n.is(tx,ty)) {
              flag=false;
              break;
            }
          }
          if(flag) {
            for(NetChunkData n:chunks.add) {
              if(n.is(tx,ty)) {
                flag=false;
                break;
              }
            }
          }
          if(flag) chunks.add.add(
            new NetChunkData(tx,ty,chunk));
        }
      }
    }
    for(NetChunkData n:chunks.list) if(UtilMath.dist(n.x,n.y,tcx,tcy)>p.world.regions.data.netRemoveDist&&
      !chunks.remove.contains(n)) chunks.remove.add(n);
  }
  public void writeChunks() {
    output.writeInt(chunks.remove.size());
    for(NetChunkData e:chunks.remove) {
      output.writeInt(e.x);
      output.writeInt(e.y);
    }
    output.writeInt(chunks.add.size());
    for(NetChunkData e:chunks.add) {
      output.writeInt(e.x);
      output.writeInt(e.y);
      KryoNetUtil.write(WorldKryoUtil.kryo,output,e.chunk);
    }
    chunks.refresh();
    state=ServerToClient.playerPos;
  }
  public void writeNeedAuth() {
    output.writeString("pseudo-server-info");
  }
  public void writeWorldData() {
    KryoNetUtil.write(WorldKryoUtil.kryo,output,p.world.data);
    state=ServerToClient.chunkData;
  }
  public void disconnect() {}
  public static class NetChunkData{
    public int x,y;
    public Chunk chunk;
    public NetChunkData(int x,int y,Chunk chunk) {
      this.x=x;
      this.y=y;
      this.chunk=chunk;
    }
    public boolean is(int xIn,int yIn) {
      return x==xIn&&y==yIn;
    }
  }
}