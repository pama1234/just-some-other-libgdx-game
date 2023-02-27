package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.state.state0001.game.net.NetState.ServerToClient;
import pama1234.gdx.game.state.state0001.game.net.ServerCore.ClientLink;
import pama1234.gdx.game.state.state0001.game.region.Chunk;
import pama1234.math.physics.MassPoint;
import pama1234.util.function.ExecuteF;
import pama1234.util.wrapper.Center;

public class ServerWrite extends Thread{
  public ClientLink link;
  public ServerCore p;
  public Output output;
  public int sleep=-1;
  public ExecuteF[] executeFs;
  public Center<Chunk> chunks;
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
  public void updateChunks() {}
  public void writeChunks() {
    // for(Region e:p.world.regions.list) {
    // }
  }
  public void writeNeedAuth() {
    output.writeString("pseudo-server-info");
  }
  public void disconnect() {}
}