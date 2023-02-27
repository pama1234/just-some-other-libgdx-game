package pama1234.gdx.game.state.state0001.game.net;

import com.esotericsoftware.kryo.io.Output;

import pama1234.gdx.game.state.state0001.game.player.PlayerControllerCore;

public class ClientWrite extends Thread{
  public ClientCore p;
  public Output output;
  public int sleep=-1;
  public boolean left,right,jump,jumpDown;
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
  public void connect() {
    sleep=0;
  }
  public void execute() {
    updatePlayerCtrl();
  }
  public void updatePlayerCtrl() {
    PlayerControllerCore ctrl=p.world.yourself.ctrl;
    boolean a=left!=ctrl.left;
    boolean b=right!=ctrl.right;
    boolean c=jump!=ctrl.jump;
    boolean d=jumpDown!=ctrl.jumpDown;
    if(a||b||c) {
      putPlayerCtrl(a,b,c,d);
      output.flush();
      clearPlayerCtrlCache(ctrl);
    }
  }
  public void clearPlayerCtrlCache(PlayerControllerCore ctrl) {
    left=ctrl.left;
    right=ctrl.right;
    jump=ctrl.jump;
    jumpDown=ctrl.jumpDown;
  }
  public void disconnect() {}
  public void putPlayerCtrl(boolean a,boolean b,boolean c,boolean d) {
    output.writeBoolean(a);
    output.writeBoolean(b);
    output.writeBoolean(c);
    output.writeBoolean(d);
  }
}