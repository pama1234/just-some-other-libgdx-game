package pama1234.gdx.game.util.legacy;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;

public class UITools{
  public static final Color selectLine=UtilScreenColor.colorFromInt(0xff6FEDFB),background=UtilScreenColor.colorFromInt(0xffDDF4C4);
  public static int weight=1;
  public static void reversedBorder(final Graphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,UtilScreenColor.colorFromInt(0x80000000),UtilScreenColor.colorFromInt(0x80ffffff));
  }
  public static void border(final Graphics l,final float x,final float y,float w,float h,Color a,Color b) {
    final Color tc=l.p.fillColor.cpy();
    final boolean ts=l.p.stroke;
    l.p.noStroke();
    l.p.doFill();
    l.p.fill(a);
    l.p.rect(x,y,w,weight);
    l.p.rect(x,y,weight,h);
    l.p.fill(b);
    l.p.rect(x,y+h-weight,w,weight);
    l.p.rect(x+w-weight,y,weight,h);
    l.p.fill(tc);
    l.p.stroke=ts;
    // l.p.noFill();
  }
  public static void border(final Graphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,UtilScreenColor.colorFromInt(0x80ffffff),UtilScreenColor.colorFromInt(0x80000000));
  }
  public static void rectBorder(final Graphics l) {
    border(l,0,0,l.width(),l.height());
  }
  public static void cross(final Graphics l,float x,float y,float a,float b) {
    l.p.line(x-a,y,x+a,y);
    l.p.line(x,y-b,x,y+b);
  }
  public static void arrow(final Graphics l,float x,float y,float a,float b,float s) {
    a*=-1;
    b*=-1;
    float mag=s/UtilMath.mag(a,b);
    float ang=(b>0?UtilMath.atan(a/b)+UtilMath.QUARTER_PI+UtilMath.HALF_PI:UtilMath.atan(a/b)+UtilMath.QUARTER_PI);
    a*=mag;
    b*=mag;
    l.p.line(x+a,y+b,x+UtilMath.sin(ang)*s,y+UtilMath.cos(ang)*s);
    ang+=b>0?UtilMath.HALF_PI:-UtilMath.HALF_PI;
    l.p.line(x+a,y+b,x+UtilMath.sin(ang)*s,y+UtilMath.cos(ang)*s);
  }
  public static void rectArrow(Graphics g,float x,float y,float w,float h,float size) {
    float scale=UtilMath.dist(0,0,w,h);
    drawRectArraw(g,x,y,w/scale*size,h/scale*size);
  }
  public static void drawRectArraw(Graphics g,float x,float y,float w,float h) {
    g.p.line(x+h,y-w,x+w,y+h);
    g.p.line(x-h,y+w,x+w,y+h);
  }
  @FunctionalInterface
  public interface LineTextWidthConsumer{
    public void put(int xPos,float charWidth,float TextWidth);
  }
}
