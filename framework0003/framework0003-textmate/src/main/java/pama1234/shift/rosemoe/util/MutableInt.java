package pama1234.shift.rosemoe.util;

public class MutableInt{
  public int value;
  public MutableInt(int v) {
    value=v;
  }
  public int decreaseAndGet() {
    return --value;
  }
  public void increase() {
    value++;
  }
}
