package pama1234.gdx.game.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.font.TextStyleSupplier;

/**
 * 简化的TextStyleSupplier功能
 */
public abstract class ColoredString implements TextStyleSupplier{
  public String string;
  public TextStyleSupplier style;

  public Color[] backgroundColors,foregroundColors;

  /**
   * 数字应当是递增的
   */
  public IntArray backgroundIntArray,foregroundIntArray;

  public int backgroundIntArrayTempCount,foregroundIntArrayTempCount;
  public int backgroundTempCount,foregroundTempCount;

  public ColoredString(
    String string,TextStyleSupplier style,
    Color[] backgroundColors,Color[] foregroundColors,
    IntArray backgroundIntArray,IntArray foregroundIntArray) {

    this.string=string;
    this.style=style;
    this.backgroundColors=backgroundColors;
    this.foregroundColors=foregroundColors;
    this.backgroundIntArray=backgroundIntArray;
    this.foregroundIntArray=foregroundIntArray;

  }

  /**
   * dont need this method in super class
   */
  @Override
  public void text(String s) {
    backgroundIntArrayTempCount=0;
    backgroundTempCount=0;
  }

  @Override
  public Color background(int x,int y,int i) {
    if(backgroundIntArray.get(backgroundIntArrayTempCount)==i) {
      backgroundTempCount++;
      backgroundIntArrayTempCount++;
    }
    return backgroundColors[backgroundTempCount];
  }

  @Override
  public Color foreground(int x,int y,int i) {
    if(backgroundIntArray.get(foregroundIntArrayTempCount)==i) {
      foregroundTempCount++;
      foregroundIntArrayTempCount++;
    }
    return foregroundColors[foregroundTempCount];
  }

  @Override
  public int style(int x,int y) {
    return 0;
  }

  public void draw(UtilScreen p) {
    p.textStyle(style);
    p.text(string);
    p.textStyle(null);
  }
}
