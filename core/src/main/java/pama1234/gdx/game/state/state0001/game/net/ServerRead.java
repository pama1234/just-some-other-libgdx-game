package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import pama1234.gdx.game.state.state0001.game.net.NetState.ServerToClient;
import pama1234.gdx.game.state.state0001.game.net.ServerCore.ClientLink;
import pama1234.gdx.game.state.state0001.game.player.PlayerControllerCore;
import pama1234.util.function.ExecuteF;

public class ServerRead extends Thread{
  public ClientLink link;
  ServerCore p;
  public Input input;
  public ExecuteF[] executeFs;
  public ServerRead(ClientLink link,ServerCore p) {
    this.link=link;
    this.p=p;
    input=new Input(link.socketData.i);
    executeFs=new ExecuteF[] {this::readPlayerCtrl,this::readPlayerAuth};
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
    executeFs[input.readByteUnsigned()].execute();
    // readPlayerCtrl();
  }
  public void readPlayerCtrl() {
    PlayerControllerCore ctrlCore=link.player.ctrlCore;
    if(input.readBoolean()) ctrlCore.left=!ctrlCore.left;
    if(input.readBoolean()) ctrlCore.right=!ctrlCore.right;
    if(input.readBoolean()) ctrlCore.jump=!ctrlCore.jump;
    if(input.readBoolean()) ctrlCore.jumpDown=!ctrlCore.jumpDown;
  }
  public void readPlayerAuth() {
    link.player.name=input.readString();
    p.world.entities.players.add.add(link.player);
    link.serverWrite.state=ServerToClient.playerPos;
  }
  public void disconnect() {
    p.world.entities.players.remove.add(link.player);
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