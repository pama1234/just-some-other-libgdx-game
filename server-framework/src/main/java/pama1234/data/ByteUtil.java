package pama1234.data;

public class ByteUtil{
  public static int byteToInt(int a,int b,int c,int d) {
    return (a<<24)|(b<<16)|(c<<8)|d;
  }
  public static int byteToInt(byte a,byte b,byte c,byte d) {//TODO
    return byteToInt(
      Byte.toUnsignedInt(a),
      Byte.toUnsignedInt(b),
      Byte.toUnsignedInt(c),
      Byte.toUnsignedInt(d));
  }
  public static int byteToInt(byte[] in) {
    return byteToInt(in[0],in[1],in[2],in[3]);
  }
  public static int byteToInt(byte[] in,int offset) {
    return byteToInt(in[offset],in[offset+1],in[offset+2],in[offset+3]);
  }
  //--------------------------------------------------------- TODO
  public static byte[] intToByte(int in) {
    return new byte[] {(byte)((in>>24)&0xff),(byte)((in>>16)&0xff),(byte)((in>>8)&0xff),(byte)(in&0xff)};
  }
  public static byte[] intToByte(int in,byte[] out) {
    return intToByte(in,out,0);
  }
  public static byte[] intToByte(int in,byte[] out,int offset) {
    out[offset]=(byte)((in>>>24)&0xff);
    out[offset+1]=(byte)((in>>16)&0xff);
    out[offset+2]=(byte)((in>>>8)&0xff);
    out[offset+3]=(byte)(in&0xff);
    return out;
  }
  //---------------------------------------------------------
  public static float byteToFloat(byte a,byte b,byte c,byte d) {
    return Float.intBitsToFloat(byteToInt(a,b,c,d));
  }
  public static float byteToFloat(byte[] in) {
    return Float.intBitsToFloat(byteToInt(in));
  }
  public static float byteToFloat(byte[] in,int offset) {
    return Float.intBitsToFloat(byteToInt(in,offset));
  }
  //---------------------------------------------------------
  public static byte[] floatToByte(float in) {
    return intToByte(Float.floatToIntBits(in));
  }
  public static byte[] floatToByte(float in,byte[] out) {
    return intToByte(Float.floatToIntBits(in),out);
  }
  public static byte[] floatToByte(float in,byte[] out,int offset) {
    return intToByte(Float.floatToIntBits(in),out,offset);
  }
  // public static void main(String[] args) {
  //   System.out.println(byteToInt((byte)0,(byte)0,(byte)0,(byte)0xff));
  // }
}