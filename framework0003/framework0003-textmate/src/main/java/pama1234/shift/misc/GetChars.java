package pama1234.shift.misc;

public interface GetChars
  extends CharSequence{
  public void getChars(int start,int end,char[] dest,int destoff);
}