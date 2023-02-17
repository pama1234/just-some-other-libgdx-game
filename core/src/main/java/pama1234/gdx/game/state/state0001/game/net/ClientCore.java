package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Input;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.KryoNetUtil;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.math.physics.MassPoint;
import pama1234.util.net.SocketData;

public class ClientCore{
  public boolean stop;
  public Game game;
  public World0001 world;
  //---
  public SocketData socketData;
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  public Thread connectThread;
  public ClientCore(Game game,World0001 world,SocketData socketData) {
    this.game=game;
    this.world=world;
    this.socketData=socketData;
    connectThread=new Thread(()-> {
      while(!(stop||socketData.s.isConnected())) game.p.sleep(200);
      if(stop) return;
      (clientRead=new ClientRead(this)).start();
      (clientWrite=new ClientWrite(this)).start();
    },"ConnectThread");
  }
  public void start() {
    connectThread.start();
  }
  public void stop() {
    stop=true;
    socketData.dispose();
    connectThread.interrupt();
  }
  public static class ClientRead extends Thread{
    public ClientCore p;
    public Input input;
    public ClientRead(ClientCore p) {
      this.p=p;
      input=new Input(p.socketData.i);
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
      MassPoint in=KryoNetUtil.read(WorldKryoUtil.kryo,input,MassPoint.class);
      if(in!=null) p.world.yourself.point.cloneFrom(in);
      else p.stop=true;
    }
  }
  public static class ClientWrite extends Thread{
    public ClientCore p;
    public ClientWrite(ClientCore p) {
      this.p=p;
    }
  }
}