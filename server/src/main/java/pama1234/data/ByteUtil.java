package pama1234.data;

public class ByteUtil{
  public static int byteToInt(byte a,byte b,byte c,byte d) {
    return (a<<24)|(b<<16)|(c<<8)|d;
  }
  public static int byteToInt(byte[] in) {
    return byteToInt(in[0],in[1],in[2],in[3]);
  }
  public static int byteToInt(byte[] in,int offset) {
    return byteToInt(in[offset],in[offset+1],in[offset+2],in[offset+3]);
  }
}
