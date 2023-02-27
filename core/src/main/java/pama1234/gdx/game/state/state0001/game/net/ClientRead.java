package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import pama1234.math.physics.Point;

public class ClientRead extends Thread{
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