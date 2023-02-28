package pama1234.gdx.util.element;

public class RangeChar implements CharSequence{
  int from,to;
  public RangeChar(int from,int to) {
    this.from=from;
    this.to=to;
  }
  @Override
  public CharSequence subSequence(int start,int end) {
    return null;
  }
  @Override
  public int length() {
    return to-from;
  }
  @Override
  public char charAt(int index) {
    return (char)(from+index);
  }
}