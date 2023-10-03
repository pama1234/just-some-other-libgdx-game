package org.eclipse.tm4e.core.theme;

public class RGB{
  public final int red;
  public final int green;
  public final int blue;
  public RGB(final int red,final int green,final int blue) {
    this.red=red;
    this.green=green;
    this.blue=blue;
  }
  @Override
  public String toString() {
    return "RGB("+red+","+green+","+blue+")";
  }
}
