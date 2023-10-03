package pama1234.game.app.server.server0002.game.metainfo.io;

import java.io.InputStream;

/**
 * 用于序列化的数据，运行时被释放
 */
public class StoredAttribute{
  public static class ProgramUnit{
    public String source;
    public InputStream javaBytecode,dalvikBytecode;
  }
}
