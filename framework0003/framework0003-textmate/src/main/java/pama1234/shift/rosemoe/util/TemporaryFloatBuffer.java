package pama1234.shift.rosemoe.util;

public class TemporaryFloatBuffer{
  private static float[] sTemp=null;
  public static float[] obtain(int len) {
    float[] buf;
    synchronized(TemporaryFloatBuffer.class) {
      buf=sTemp;
      sTemp=null;
    }
    if(buf==null||buf.length<len) {
      buf=new float[len];
    }
    return buf;
  }
  public static void recycle(float[] temp) {
    if(temp.length>1000) return;
    synchronized(TemporaryFloatBuffer.class) {
      sTemp=temp;
    }
  }
}
