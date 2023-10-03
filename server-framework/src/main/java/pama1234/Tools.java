package pama1234;

import java.lang.reflect.Array;

import pama1234.util.ColorTools;

public class Tools extends ColorTools{
  // Android特供修正
  public static boolean isEmpty(CharSequence in) {
    return in.length()==0;
  }
  // Android特供修正，因为目前安卓中的String没有indent方法
  public static String indent(String in,int l) {
    StringBuilder sb=new StringBuilder();
    for(int i=0;i<l;i++) {
      sb.append(' ');
    }
    sb.append(in);
    return sb.toString();
  }
  public static <T> T[] concat(T[] a,T[] b) {
    int aLen=a.length;
    int bLen=b.length;
    @SuppressWarnings("unchecked")
    T[] c=(T[])Array.newInstance(a.getClass().getComponentType(),aLen+bLen);
    System.arraycopy(a,0,c,0,aLen);
    System.arraycopy(b,0,c,aLen,bLen);
    return c;
  }
  public static long timeData;
  public static long timeM() {
    return System.currentTimeMillis();
  }
  public static long time() {
    return timeData=System.currentTimeMillis();
  }
  public static long period() {
    long out=System.currentTimeMillis()-timeData;
    time();
    return out;
  }
  public static void println(Object... in) {
    StringBuilder sb=new StringBuilder();
    for(Object o:in) {
      if(sb.length()!=0) sb.append(" ");
      if(o==null) sb.append("null");
      else sb.append(o.toString());
    }
    sb.append('\n');
    System.out.print(sb.toString());
    System.out.flush();
  }
  public static String getMillisString(long in) {
    return String.format("%03d",in);
  }
  public static String getMillisString(long in,int l) {
    return String.format("%0"+l+"d",in);
  }
  public static String getFloatString(float in) {
    return String.format("%05.2f",in);
  }
  public static String getFloatString(float in,int l) {
    return String.format("%0"+l+".2f",in);
  }
  @Deprecated
  public static String getFloatStringDepc(float in,int l,int l2) {
    return String.format("%0"+l+"."+l2+"f",in);
  }
  public static String getFloatString(float in,int l,int l2) {
    return String.format("%0"+(l+l2+1)+"."+l2+"f",in);
  }
  public static String getIntString(int in,int l) {
    return String.format("%0"+l+"d",in);
  }
  public static String getMemory() {
    return Tools.cutToLastDigitString((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024));
  }
}