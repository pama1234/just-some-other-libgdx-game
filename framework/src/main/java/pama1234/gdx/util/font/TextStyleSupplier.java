package pama1234.gdx.util.font;

import com.badlogic.gdx.graphics.Color;

public interface TextStyleSupplier{
  public void text(String in);
  /**
   * i是目前字符在字符串中的位置
   * 
   * @param x
   * @param y
   * @param i
   * @return
   */
  public Color background(int x,int y,int i);
  /**
   * i是目前字符在字符串中的位置
   * 
   * @param x
   * @param y
   * @param i
   * @return
   */
  public Color foreground(int x,int y,int i);
  /**
   * 未实现，可直接输出0
   * 
   * @param x
   * @param y
   * @return
   */
  public int style(int x,int y);
  @FunctionalInterface
  public interface GetTextColor{
    public Color get(int x,int y,int i);
  }
}
