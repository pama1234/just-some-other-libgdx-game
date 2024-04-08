package pama1234.gdx.game.duel;

import pama1234.util.function.GetFloatWith;

public class TextUtil{
  public static TextUtil used;

  public TextWithWidth win;
  public TextWithWidth lose;

  public TextWithWidth restart;
  public TextWithWidth go;
  public static TextUtil gen_ch(GetFloatWith<String> textWidth) {
    TextUtil out=new TextUtil();
    out.win=new TextWithWidth("你赢了",textWidth);
    out.lose=new TextWithWidth("你输了",textWidth);
    out.restart=new TextWithWidth("按 X 键重新开始",textWidth);
    out.go=new TextWithWidth("冲啊！！",textWidth);
    return out;
  }
  public static class TextWithWidth{
    public String text;
    public float width;
    public TextWithWidth(String text,float width) {
      this.text=text;
      this.width=width;
    }
    public TextWithWidth(String text,GetFloatWith<String> textWidth) {
      this.text=text;
      this.width=textWidth.getWith(text);
    }
  }
}