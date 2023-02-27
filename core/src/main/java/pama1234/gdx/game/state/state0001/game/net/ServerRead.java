package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import pama1234.gdx.game.state.state0001.game.net.ServerCore.ClientLink;
import pama1234.gdx.game.state.state0001.game.player.PlayerControllerCore;

public class ServerRead extends Thread{
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
    try {
      connect();
      while(!p.stop) execute();
    }catch(RuntimeException e) {
      e.printStackTrace();
      p.stop=true;
    }finally {
      link.socketData.dispose();
      p.serverReadPool.remove.add(this);
      disconnect();
    }
  }
  public void connect() {}
  public void execute() {
    // skip(3);
    readPlayerCtrl();
  }
  public void readPlayerCtrl() {
    PlayerControllerCore ctrlCore=link.player.ctrlCore;
    if(input.readBoolean()) ctrlCore.left=!ctrlCore.left;
    if(input.readBoolean()) ctrlCore.right=!ctrlCore.right;
    if(input.readBoolean()) ctrlCore.jump=!ctrlCore.jump;
    if(input.readBoolean()) ctrlCore.jumpDown=!ctrlCore.jumpDown;
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