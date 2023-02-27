package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import pama1234.gdx.game.state.state0001.game.KryoNetUtil;
import pama1234.gdx.game.state.state0001.game.net.NetState.ClientToServer;
import pama1234.gdx.game.state.state0001.game.region.Chunk;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.math.physics.Point;
import pama1234.util.function.ExecuteF;

public class ClientRead extends Thread{
  public ClientCore p;
  public Input input;
  public ExecuteF[] executeFs;
  public ClientRead(ClientCore p) {
    this.p=p;
    input=new Input(p.socketData.i);
    executeFs=new ExecuteF[] {this::readPlayerPos,this::readChunkData,this::readAuthInfo};
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
    // readPlayerPos();
  }
  public void readPlayerPos() {
    skip(8);
    float x=input.readFloat(),y=input.readFloat();
    Point point=p.world.yourself.point;
    if(point.pos.dist(x,y)>36) point.pos.set(x,y);
  }
  public void readChunkData() {
    int cx=input.readInt(),cy=input.readInt();
    Chunk chunk=KryoNetUtil.read(WorldKryoUtil.kryo,input,Chunk.class);
    p.world.regions.addChunk(cx,cy,chunk);
  }
  public void readAuthInfo() {
    String serverInfo=input.readString();
    if(serverInfo.equals("pseudo-server-info")) {
      p.clientWrite.state=ClientToServer.playerAuth;
    }else System.out.println("ClientRead.readAuthInfo()");
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