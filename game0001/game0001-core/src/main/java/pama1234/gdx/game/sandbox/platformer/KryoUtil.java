package pama1234.gdx.game.sandbox.platformer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoUtil{
  public static <T> T load(Kryo kryo,FileHandle file,Class<T> classIn) {
    if(!file.exists()) return null;
    try(Input input=new Input(new FileInputStream(file.file()))) {
      T out=kryo.readObject(input,classIn);
      input.close();
      return out;
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static <T> void save(Kryo kryo,FileHandle file,T in) {
    try(Output output=new Output(new FileOutputStream(file.file()))) {
      kryo.writeObject(output,in);
      output.close();
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
  }
}