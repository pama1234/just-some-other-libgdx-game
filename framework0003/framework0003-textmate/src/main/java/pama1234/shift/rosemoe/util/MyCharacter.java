package pama1234.shift.rosemoe.util;

import java.util.Arrays;

public class MyCharacter{
  private static int[] bitsIsStart;
  private static int[] bitsIsPart;
  static {
    initMapInternal();
  }
  private static boolean get(int[] values,int bitIndex) {
    return ((values[bitIndex/32]&(1<<(bitIndex%32)))!=0);
  }
  private static void set(int[] values,int bitIndex) {
    values[bitIndex/32]|=(1<<(bitIndex%32));
  }
  @Deprecated
  public static void initMap() {
    // Empty
  }
  private static void initMapInternal() {
    if(bitsIsStart!=null) {
      return;
    }
    bitsIsPart=new int[2048];
    bitsIsStart=new int[2048];
    Arrays.fill(bitsIsPart,0);
    Arrays.fill(bitsIsStart,0);
    for(int i=0;i<=65535;i++) {
      if(Character.isJavaIdentifierPart((char)i)) {
        set(bitsIsPart,i);
      }
      if(Character.isJavaIdentifierStart((char)i)) {
        set(bitsIsStart,i);
      }
    }
  }
  public static boolean isJavaIdentifierPart(char key) {
    return get(bitsIsPart,key);
  }
  public static boolean isJavaIdentifierStart(char key) {
    return get(bitsIsStart,key);
  }
  public static boolean couldBeEmoji(int cp) {
    return cp>=0x1F000&&cp<=0x1FAFF;
  }
  public static boolean isFitzpatrick(int cp) {
    return cp>=0x1F3FB&&cp<=0x1F3FF;
  }
  public static boolean isZWJ(int cp) {
    return cp==0x200D;
  }
  public static boolean isZWNJ(int cp) {
    return cp==0x200C;
  }
  public static boolean isVariationSelector(int cp) {
    return cp==0xFE0E||cp==0xFE0F;
  }
  public static boolean isAlpha(char c) {
    return (c>='a'&&c<='z')||(c>='A'&&c<='Z');
  }
}
