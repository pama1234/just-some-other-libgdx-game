package pama1234.gdx.game.text.style;

import static pama1234.gdx.util.app.UtilScreenColor.color;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.font.TextStyleBase;
import pama1234.math.Tools;

public class RainbowTextStyle extends TextStyleBase{
  public Color backgroundDefault=color(255),foregroundDefault=color(0);
  public Color[] rainbow;
  public boolean doBackground;
  // Color a=color(255,127),d=color(0),
  //   b=color(255,0,0),c=color(0,0,255,127);
  public GetTextColor[] colors;
  public char[] separator;
  public int fcount,bcount;
  public int ty;
  {
    rainbow=new Color[12];
    for(int i=0;i<rainbow.length;i++) rainbow[i]=UtilScreen.newColorFromInt(Tools.hsbColor(255/rainbow.length*i,255,255));
    colors=new GetTextColor[] {(x,y,i)-> {
      if(charAt(i-1)==' '||charAt(i)==' ') {
        bcount+=1;
        if(bcount>=rainbow.length) bcount=0;
      }
      return rainbow[bcount];
    },(x,y,i)->backgroundDefault
    };
    backgroundDefault=color(0);
    // separator=new char[] {' ','	','\n'};
    separator=new char[] {' '};
  }
  @Override
  public Color background(int x,int y,int i) {
    return colors[doBackground?0:1].get(x,y,i);
    // return backgroundDefault;
  }
  @Override
  public Color foreground(int x,int y,int i) {
    // System.out.println(x);
    if(testChar(charAt(i))) changeColor();
    else if(y!=ty) {
      ty=y;
      changeColor();
    }
    return rainbow[fcount];
    // return foregroundDefault;
  }
  private void changeColor() {
    fcount+=1;
    if(fcount>=rainbow.length) fcount=0;
  }
  @Override
  public int style(int x,int y) {
    return 0;
  }
  @Override
  public void text(String in) {
    super.text(in);
    fcount=0;
    bcount=6;
  }
  public boolean testChar(char in) {
    for(char i:separator) if(in==i) return true;
    return false;
  }
}
