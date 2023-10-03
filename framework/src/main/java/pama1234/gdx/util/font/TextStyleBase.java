package pama1234.gdx.util.font;

public abstract class TextStyleBase implements TextStyleSupplier{
  public String data;
  @Override
  public void text(String in) {
    data=in;
  }
  public char charAt(int pos) {
    if(pos<0||pos>=data.length()) return 0;
    return data.charAt(pos);
  }
  public int style(int x,int y) {
    return 0;
  }
}