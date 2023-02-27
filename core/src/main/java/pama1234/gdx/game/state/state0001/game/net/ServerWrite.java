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
    executeFs=new ExecuteF[] {this::writePlayerPos,this::writeChunks,this::writeNeedAuth};
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
    output.writeByte(state);
    executeFs[state].execute();
    // writePlayerPos();
    output.flush();
  }
  public void writePlayerPos() {
    MassPoint point=link.player.point;
    output.writeFloat(point.pos.x);
    output.writeFloat(point.pos.y);
  }
  public void updateChunks() {
    chunks.list.clear();
    float tcx=link.player.cx()/p.world.settings.blockWidth,
      tcy=link.player.cy()/p.world.settings.blockHeight;
    RegionCenter pr=p.world.regions;
    for(Region e:p.world.regions.list) {
      // float tx_1=(((e.x+0.5f)*pr.regionWidth)*pr.chunkWidth),
      //   ty_1=(((e.y+0.5f)*pr.regionHeight)*pr.chunkHeight);
      for(int i=0;i<e.data.length;i++) {
        for(int j=0;j<e.data[i].length;j++) {
          Chunk chunk=e.data[i][j];
          // if(!chunk.update) continue;
          // BlockData[][] blockData=chunk.data;
          float tx_2=((e.x*pr.regionWidth+(i+0.5f))*pr.chunkWidth),
            ty_2=((e.y*pr.regionHeight+(j+0.5f))*pr.chunkHeight);
          // if(chunk)
          float dist=UtilMath.dist(tx_2,ty_2,tcx,tcy);
          if(dist<120) {
            chunks.list.add(
              new NetChunkData(
                (e.x*pr.regionWidth+i)*pr.chunkWidth,
                (e.y*pr.regionHeight+j)*pr.chunkHeight,
                chunk));
          }
        }
      }
    }
  }
  public void writeChunks() {
    output.writeInt(chunks.list.size());
    for(NetChunkData e:chunks.list) {
      output.writeInt(e.x);
      output.writeInt(e.y);
      KryoNetUtil.write(WorldKryoUtil.kryo,output,e.chunk);
    }
    chunks.list.clear();
    state=ServerToClient.playerPos;
  }
  public void writeNeedAuth() {
    output.writeString("pseudo-server-info");
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
  }
}