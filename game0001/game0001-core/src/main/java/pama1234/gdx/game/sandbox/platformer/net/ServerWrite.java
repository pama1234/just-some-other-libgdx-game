package pama1234.gdx.game.sandbox.platformer.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.KryoNetUtil;
import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.entity.center.MultiGameEntityCenter0001;
import pama1234.gdx.game.sandbox.platformer.net.NetState.ServerToClient;
import pama1234.gdx.game.sandbox.platformer.net.ServerCore.ClientLink;
import pama1234.gdx.game.sandbox.platformer.region.Chunk;
import pama1234.gdx.game.sandbox.platformer.region.Region;
import pama1234.gdx.game.sandbox.platformer.region.RegionCenter;
import pama1234.gdx.game.sandbox.platformer.world.WorldKryoUtil;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.util.function.ExecuteFunction;
import pama1234.util.wrapper.Center;

public class ServerWrite extends Thread{
  public ClientLink link;
  public ServerCore p;
  public Output output;
  public int sleep=-1;
  public ExecuteFunction[] executeFs;
  public Center<NetChunkData> chunks;
  public Center<GamePointEntity<?>> entities;
  public int state=ServerToClient.needAuth;
  public ServerWrite(ClientLink link,ServerCore p) {
    super("ServerWrite");
    this.link=link;
    this.p=p;
    output=new Output(link.socketData.o);
    executeFs=new ExecuteFunction[] {this::writePlayerPos,this::writeChunks,this::writeNeedAuth,this::writeWorldData,this::writeEntities,this::writeChunksUpdate,this::writeEntitiesUpdate};
    chunks=new Center<>();
    entities=new Center<>();
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
    // entities.add.add(p.world.yourself);
  }
  public void execute() {
    updateChunks();
    updateEntities();

    if(entities.add.size()>0||entities.remove.size()>0) state=ServerToClient.entityData;
    else if(chunks.add.size()>0||chunks.remove.size()>0) state=ServerToClient.chunkData;
    output.writeByte(state);
    executeFs[state].execute();
    output.flush();
  }
  /**
   * 向客户端传递玩家位置
   */
  public void writePlayerPos() {
    MassPoint point=link.player.point;
    output.writeFloat(point.pos.x);
    output.writeFloat(point.pos.y);
  }
  public void updateEntities() {
    float tcx=link.player.cx()/p.world.settings.blockWidth,
      tcy=link.player.cy()/p.world.settings.blockHeight;
    MultiGameEntityCenter0001 pe=p.world.entities;
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:pe.list) for(GamePointEntity<?> e:l.list) {
      float dist=UtilMath.dist(e.x(),e.y(),tcx,tcy);
      if(dist<p.world.regions.data.netTransferDist) {
        if(!entities.list.contains(e)) entities.add.add(e);
      }
    }
    // for(GamePointEntity<?> e:entities.list) if(UtilMath.dist(e.x(),e.y(),tcx,tcy)>p.world.regions.data.netRemoveDist&&
    //   !entities.remove.contains(e)) entities.remove.add(e);
    for(GamePointEntity<?> e:entities.list) {
      if(UtilMath.dist(e.x(),e.y(),tcx,tcy)>p.world.regions.data.netRemoveDist) entities.remove.add(e);
    }
  }
  /**
   * 当有实体诞生或消失时，传递：
   * </p>
   * 1. 要删除的实体的坐标
   * </p>
   * 2. 要添加的整个实体的数据
   */
  public void writeEntities() {//TODO
    output.writeInt(entities.remove.size());
    for(GamePointEntity<?> e:entities.remove) {
      output.writeFloat(e.x());
      output.writeFloat(e.y());
      output.writeInt(e.id);
    }
    output.writeInt(entities.add.size());
    for(GamePointEntity<?> e:entities.add) {
      KryoNetUtil.write(WorldKryoUtil.kryo,output,e);
    }
    entities.refresh();
    state=ServerToClient.playerPos;
  }
  public void updateChunks() {
    float tcx=link.player.cx()/p.world.settings.blockWidth,
      tcy=link.player.cy()/p.world.settings.blockHeight;
    RegionCenter pr=p.world.regions;
    for(Region e:pr.list) {
      for(int i=0;i<e.data.length;i++) for(int j=0;j<e.data[i].length;j++) {
        Chunk chunk=e.data[i][j];
        float tx_2=((e.x*pr.data.regionWidth+(i+0.5f))*pr.data.chunkWidth),
          ty_2=((e.y*pr.data.regionHeight+(j+0.5f))*pr.data.chunkHeight);
        float dist=UtilMath.dist(tx_2,ty_2,tcx,tcy);
        if(dist<pr.data.netTransferDist) {
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
    // for(NetChunkData n:chunks.list) if(UtilMath.dist(n.x,n.y,tcx,tcy)>p.world.regions.data.netRemoveDist&&
    //   !chunks.remove.contains(n)) chunks.remove.add(n);
    for(NetChunkData n:chunks.list) {
      if(UtilMath.dist(n.x,n.y,tcx,tcy)>p.world.regions.data.netRemoveDist) chunks.remove.add(n);
    }
  }
  /**
   * 当有区块加载或卸载时，传递：
   * </p>
   * 1. 要卸载的区块的坐标
   * </p>
   * 2. 要加载的整个区块的数据
   */
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
  public void writeChunksUpdate() {}
  public void writeEntitiesUpdate() {}
  /**
   * 传递表示“需要客户端传递登录信息”的信息
   */
  public void writeNeedAuth() {
    output.writeString("v0.0.1-testWorld");
  }
  /**
   * 传递服务端的世界的时间等
   */
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