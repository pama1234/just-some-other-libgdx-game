package pama1234.bytecode;

public class MemClassLoader extends ClassLoader{
  public byte[] data;
  @Override
  public Class<?> findClass(String name) {
    byte[] tb=data;
    return defineClass(name,tb,0,tb.length);
  }
}
