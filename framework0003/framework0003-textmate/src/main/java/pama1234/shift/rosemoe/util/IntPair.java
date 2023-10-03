package pama1234.shift.rosemoe.util;

public class IntPair{
  private static long toUnsignedLong(int x) {
    return ((long)x)&0xffffffffL;
  }
  public static long pack(int first,int second) {
    return (toUnsignedLong(first)<<32L)|toUnsignedLong(second);
  }
  public static int getSecond(long packedValue) {
    return (int)(packedValue&0xFFFFFFFFL);
  }
  public static int getFirst(long packedValue) {
    return (int)(packedValue>>32L);
  }
}
