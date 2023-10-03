package pama1234.shift.rosemoe.util;

public class TemporaryCharBuffer{
  private static char[] sTemp=null;
  public static char[] obtain(int len) {
    char[] buf;
    synchronized(TemporaryCharBuffer.class) {
      buf=sTemp;
      sTemp=null;
    }
    if(buf==null||buf.length<len) {
      buf=new char[len];
    }
    return buf;
  }
  public static void recycle(char[] temp) {
    if(temp.length>1000) return;
    synchronized(TemporaryCharBuffer.class) {
      sTemp=temp;
    }
  }
}