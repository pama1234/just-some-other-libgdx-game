package pama1234.shift.rosemoe.lang.styling.line;

public abstract class LineAnchorStyle implements Comparable<LineAnchorStyle>{
  private int line;
  protected Object customData=null;
  public LineAnchorStyle(int line) {
    this.line=line;
  }
  public int getLine() {
    return line;
  }
  public void setLine(int line) {
    this.line=line;
  }
  public Object getCustomData() {
    return customData;
  }
  public void setCustomData(Object customData) {
    this.customData=customData;
  }
  @Override
  public int compareTo(LineAnchorStyle other) {
    return Integer.compare(line,other.line);
  }
}
