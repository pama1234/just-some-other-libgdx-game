package pama1234.gdx.util;

public abstract class AbstractControlBindUtil{
  public int[][] keyCodeArray;
  public boolean isKeyPressed(int type,GetKeyPressedBoolean f) {
    final int[] tia=keyCodeArray[type];
    for(final int is:tia) if(f.get(is)) return true;
    return false;
  }
  public boolean isKey(int type,int keyCode) {
    final int[] tia=keyCodeArray[type];
    for(final int is:tia) if(keyCode==is) return true;
    return false;
  }
  @FunctionalInterface
  public interface GetKeyPressedBoolean{
    public boolean get(int keyCode);
  }
}
