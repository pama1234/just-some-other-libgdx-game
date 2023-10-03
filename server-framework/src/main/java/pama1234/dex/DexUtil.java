package pama1234.dex;

public interface DexUtil{
  public ClassLoader getClassLoader();
  public DexCompiler getDexCompiler();
  public interface DexCompiler{
  }
}
