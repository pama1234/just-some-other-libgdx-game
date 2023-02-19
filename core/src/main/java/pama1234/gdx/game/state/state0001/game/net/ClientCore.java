package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.player.PlayerControllerCore;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.physics.Point;
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
      skip(8);
      float x=input.readFloat(),y=input.readFloat();
      Point point=p.world.yourself.point;
      if(point.pos.dist(x,y)>36) point.pos.set(x,y);
      // point.pos.set(x,y);
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
  public static class ClientWrite extends Thread{
    public ClientCore p;
    public Output output;
    public int sleep=0;
    public boolean left,right,jump;
    public ClientWrite(ClientCore p) {
      this.p=p;
      output=new Output(p.socketData.o);
    }
    @Override
    public void run() {
      try {
        connect();
        while(!p.stop) {
          execute();
          if(sleep>=0) sleep(sleep);
        }
      }catch(RuntimeException|InterruptedException e) {
        e.printStackTrace();
        p.stop=true;
      }finally {
        disconnect();
      }
    }
    public void connect() {}
    public void execute() {
      PlayerControllerCore ctrl=p.world.yourself.ctrl;
      boolean a=left!=ctrl.left;
      boolean b=right!=ctrl.right;
      boolean c=jump!=ctrl.jump;
      // System.out.println(a+" "+b+" "+c);
      if(a||b||c) {
        // System.out.println("ClientCore.ClientWrite.execute()");
        putPlayerCtrl(a,b,c);
        output.flush();
        left=ctrl.left;
        right=ctrl.right;
        jump=ctrl.jump;
      }
    }
    public void disconnect() {}
    public void putPlayerCtrl(boolean a,boolean b,boolean c) {
      // PlayerControllerCore ctrl=p.world.yourself.ctrl;
      output.writeBoolean(a);
      output.writeBoolean(b);
      output.writeBoolean(c);
    }
  }
}