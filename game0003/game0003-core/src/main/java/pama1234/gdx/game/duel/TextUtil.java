package pama1234.gdx.game.duel;

import pama1234.util.function.GetFloatWith;

public class TextUtil{
  public static TextUtil used;
  public String win,lose;
  public String restart;
  public float restartTextWidth;
  public static TextUtil gen_ch(GetFloatWith<String> textWidth) {
    TextUtil out=new TextUtil();
    out.win="你赢了";
    out.lose="你输了";
    out.restart="按 X 键重新开始";
    out.restartTextWidth=textWidth.getWith(out.restart);
    return out;
  }
}