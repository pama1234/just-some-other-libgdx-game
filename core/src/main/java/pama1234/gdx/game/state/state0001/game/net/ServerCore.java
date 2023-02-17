package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.game.app.server.server0002.net.SocketData;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.KryoNetUtil;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldKryoUtil;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketWrapper;
import pama1234.util.wrapper.Center;

public class ServerCore{
  public boolean stop;
  public Game game;
  public World0001 world;
  //---
  public ServerSocketData serverSocketData;
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public Thread acceptThread;
  public ServerCore(Game game,World0001 world,ServerSocketData serverSocketData) {
    this.game=game;
    this.world=world;
    this.serverSocketData=serverSocketData;
    socketCenter=new Center<>();
    serverReadPool=new Center<>();
    serverWritePool=new Center<>();
    acceptThread=new Thread(()-> {
      while(!stop) {
        SocketData socketData=new SocketData(new SocketWrapper(serverSocketData.accept()));
        socketCenter.add.add(socketData);
        //---
        ServerWrite serverWrite=new ServerWrite(createLink(socketData),this);
        serverWrite.start();
        serverWritePool.add.add(serverWrite);
        //---
        ServerRead serverRead=new ServerRead(socketData,this);
        serverRead.start();
        serverReadPool.add.add(serverRead);
      }
    },"AcceptSocket");
  }
  public ClientLink createLink(SocketData socketData) {
    Player player=new Player(game.p,world,0,0,world.metaEntitys.player);
    return new ClientLink(socketData,player);
  }
  public void start() {
    acceptThread.start();
  }
  public void stop() {
    stop=true;
    serverSocketData.close();
    acceptThread.interrupt();
  }
  public static class ServerWrite extends Thread{
    public ClientLink link;
    public ServerCore p;
    public Output output;
    public ServerWrite(ClientLink link,ServerCore p) {
      this.link=link;
      this.p=p;
      output=new Output(link.socketData.o);
    }
    @Override
    public void run() {
      try {
        while(!p.stop) execute();
      }catch(RuntimeException e) {
        e.printStackTrace();
        p.stop=true;
      }
      disconnect();
    }
    public void connect() {
      p.world.entities.players.add.add(link.player);
    }
    public void execute() {
      KryoNetUtil.write(WorldKryoUtil.kryo,output,link.player.point);
    }
    public void disconnect() {
      p.world.entities.players.remove.add(link.player);
    }
  }
  public static class ServerRead extends Thread{
    SocketData socketData;
    ServerCore p;
    public ServerRead(SocketData socketData,ServerCore p) {
      this.p=p;
    }
  }
  public static class ClientLink{
    public SocketData socketData;
    //---
    public Player player;
    public ClientLink(SocketData socketData,Player player) {
      this.socketData=socketData;
      this.player=player;
    }
  }
}