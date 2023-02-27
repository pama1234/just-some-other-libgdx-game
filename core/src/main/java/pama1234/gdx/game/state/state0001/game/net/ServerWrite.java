package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.state.state0001.game.net.ServerCore.ClientLink;
import pama1234.gdx.game.state.state0001.game.region.Region;
import pama1234.math.physics.MassPoint;
import pama1234.util.wrapper.Center;

public class ServerWrite extends Thread{
  public ClientLink link;
  public ServerCore p;
  public Output output;
  public int sleep=-1;
  public Center<Region> regions;
  public ServerWrite(ClientLink link,ServerCore p) {
    this.link=link;
    this.p=p;
    output=new Output(link.socketData.o);
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
    p.world.entities.players.add.add(link.player);
    sleep=50;
  }
  public void execute() {
    writePlayerPos();
    output.flush();
  }
  public void writePlayerPos() {
    MassPoint point=link.player.point;
    output.writeFloat(point.pos.x);
    output.writeFloat(point.pos.y);
  }
  public void updateRegions() {
    // for(Region e:p.world.regions.list) {
    // }
  }
  public void writeRegions() {}
  public void disconnect() {
    p.world.entities.players.remove.add(link.player);
  }
}