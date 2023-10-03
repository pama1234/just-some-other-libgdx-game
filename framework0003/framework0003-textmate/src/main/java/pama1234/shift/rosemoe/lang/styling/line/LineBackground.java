package pama1234.shift.rosemoe.lang.styling.line;

import pama1234.shift.rosemoe.lang.styling.color.ResolvableColor;

public class LineBackground extends LineAnchorStyle{
  private ResolvableColor color;
  public LineBackground(int line,ResolvableColor color) {
    super(line);
    this.color=color;
  }
}
