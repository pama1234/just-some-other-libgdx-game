package pama1234.gdx.textmate;

import com.badlogic.gdx.graphics.Color;

public class StyledText{
  public Color background,
    foreground,
    selectionBackground,
    selectionForeground;
  public StyledText(Color background,Color foreground,Color selectionBackground,Color selectionForeground) {
    this.background=background;
    this.foreground=foreground;
    this.selectionBackground=selectionBackground;
    this.selectionForeground=selectionForeground;
  }
  public void setBackground(Color color) {
    this.background=color;
  }
  public void setForeground(Color color) {
    this.foreground=color;
  }
  public void setSelectionBackground(Color color) {
    this.selectionBackground=color;
  }
  public void setSelectionForeground(Color color) {
    this.selectionForeground=color;
  }
}
