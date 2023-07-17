package pama1234.shift.rosemoe.util;

public class Floats{
  public static boolean withinDelta(float a,float b,float delta) {
    return Math.abs(a-b)<Math.abs(delta);
  }
}
