package pama1234.gdx.util;

public abstract class AbstractControlBindUtil{
  @FunctionalInterface
  public interface GetKeyPressedBoolean{
    public boolean get(int keyCode);
  }
}
