package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pama1234.game.app.server.server0002.net.SocketData;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.physics.MassPoint;
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
        SocketData socketData;
        try {
          socketData=new SocketData(new SocketWrapper(serverSocketData.accept()));
          socketCenter.add.add(socketData);
          //---
          ClientLink link=createLink(socketData);
          link.init();
          ServerWrite serverWrite=new ServerWrite(link,this);
          serverWrite.start();
          serverWritePool.add.add(serverWrite);
          //---
          ServerRead serverRead=new ServerRead(link,this);
          serverRead.start();
          serverReadPool.add.add(serverRead);
        }catch(IOException e) {
          e.printStackTrace();
          stop=true;
        }
      }
    },"AcceptSocket");
  }
  public ClientLink createLink(SocketData socketData) {
    return new ClientLink(this,socketData);
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
    public int sleep=-1;
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
      }
      link.socketData.dispose();
      p.serverWritePool.remove.add(this);
      disconnect();
    }
    public void connect() {
      p.world.entities.players.add.add(link.player);
    }
    public void execute() {
      MassPoint point=link.player.point;
      output.writeFloat(point.pos.x);
      output.writeFloat(point.pos.y);
    }
    public void disconnect() {
      p.world.entities.players.remove.add(link.player);
    }
  }
  public static class ServerRead extends Thread{
    public ClientLink link;
    ServerCore p;
    public Input input;
    public ServerRead(ClientLink link,ServerCore p) {
      this.link=link;
      this.p=p;
      input=new Input(link.socketData.i);
    }
    @Override
    public void run() {
      connect();
      try {
        while(!p.stop) execute();
      }catch(RuntimeException e) {
        e.printStackTrace();
        p.stop=true;
      }
      link.socketData.dispose();
      p.serverReadPool.remove.add(this);
      disconnect();
    }
    public void connect() {}
    public void execute() {
      skip(3);
      link.player.ctrlCore.left=input.readBoolean();
      link.player.ctrlCore.right=input.readBoolean();
      link.player.ctrlCore.jump=input.readBoolean();
    }
    public void disconnect() {}
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
  public static class ClientLink{
    public SocketData socketData;
    public ServerCore p;
    //---
    public Player player;
    public ClientLink(ServerCore p,SocketData socketData) {
      this.p=p;
      this.socketData=socketData;
    }
    public void init() {
      player=new Player(p.game.p,p.world,0,0,p.world.metaEntitys.player);
      player.init();
      player.innerInit();
    }
  }
}