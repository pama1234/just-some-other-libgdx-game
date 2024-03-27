package pama1234.gdx.game.util.ui;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreenColor;

public interface ColorUtil{

  Color background=color("DDF4C4");

  /** we all know this means class */
  Color clas=color("FB6104");
  /** we all know this means interface */
  Color interfase=color("6D5CB4");
  Color enume=color("A77554");
  Color generic = color("644632");

  Color keyword=color("248888");

  Color data=color("D53569");
  Color function=color("005984");

  Color number=color("2A00EC");
  Color string=color("7F4794");
  /** 例如/t */
  Color stringToken=color("D37ADB");

  Color comment=color("008000");
  Color doc=color("3F5FBF");

  Color cursorLine=color("6FEDFB");
  Color select=color("FFCC00");

  Color unused=color("808080");

  /**
   * A function that generates a Color object from a hexadecimal string representation.
   * 输入AARRGGBB格式的十六进制字符串。将考虑加入{@link UtilScreenColor}中
   *
   * @param hex the hexadecimal string representing the color
   * @return the Color object created from the hexadecimal string
   */
  public static Color color(String hex) {
    // 去除可能存在的#符号
    if(hex.startsWith("#")) {
      hex=hex.substring(1);
    }

    // 解析颜色值
    int colorValue=(int)Long.parseLong(hex,16);

    // 提取颜色通道
    float alpha=hex.length()>6?((colorValue>>24)&0xFF)/255f:1;
    float red=((colorValue>>16)&0xFF)/255f;
    float green=((colorValue>>8)&0xFF)/255f;
    float blue=(colorValue&0xFF)/255f;

    return new Color(red,green,blue,alpha);
  }
}
