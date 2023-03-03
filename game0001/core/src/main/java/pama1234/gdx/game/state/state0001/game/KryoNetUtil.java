package pama1234.gdx.game.state.state0001.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoNetUtil{
  public static <T> T read(Kryo kryo,Input input,Class<T> classIn) {
    T out=kryo.readObject(input,classIn);
    return out;
  }
  public static <T> void write(Kryo kryo,Output output,T in) {
    kryo.writeObject(output,in);
  }
}