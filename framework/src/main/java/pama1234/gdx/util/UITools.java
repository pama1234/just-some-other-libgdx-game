package pama1234.gdx.util;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.element.MoveableCross;
import pama1234.math.UtilMath;
import pama1234.util.function.GetFloat;

public class UITools{
  public static final Color selectLine=UtilScreenColor.newColorFromInt(0xff6FEDFB),background=UtilScreenColor.newColorFromInt(0xffDDF4C4);
  private static final Color borderColor_1=UtilScreenColor.newColorFromInt(0x80ffffff),borderColor_2=UtilScreenColor.newColorFromInt(0x80000000);
  public static int weight=1;
  public static void reversedBorder(final Graphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,borderColor_2,borderColor_1);
  }
  public static void border(final Graphics l,final float x,final float y,float w,float h,Color a,Color b) {
    UtilScreen p=l.p;
    final Color tc=p.fillColor.cpy();
    final boolean ts=p.stroke;
    p.beginBlend();
    p.noStroke();
    p.doFill();
    p.fill(a);
    p.rect(x,y,w,weight);
    p.rect(x,y,weight,h);
    p.fill(b);
    p.rect(x,y+h-weight,w,weight);
    p.rect(x+w-weight,y,weight,h);
    p.fill(tc);
    p.stroke=ts;
    p.endBlend();
    // l.p.noFill();
  }
  public static void border(final Graphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,borderColor_1,borderColor_2);
  }
  public static void rectBorder(final Graphics l) {
    border(l,0,0,l.width(),l.height());
  }
  public static void cross(UtilScreen p,float x,float y,float size) {
    cross(p,x,y,size,size);
  }
  public static void cross(final UtilScreen p,float x,float y,float a,float b) {
    p.line(x-a,y,x+a,y);
    p.line(x,y-b,x,y+b);
  }
  public static void cross(final Graphics l,float x,float y,float a,float b) {
    cross(l.p,x,y,a,b);
  }
  public static void arrow(final Graphics l,float x,float y,float a,float b,float s) {
    arrow(l.p,x,y,a,b,s);
  }
  public static void arrow(UtilScreen p,float x,float y,float a,float b,float s) {
    a*=-1;
    b*=-1;
    float mag=s/UtilMath.mag(a,b);
    float ang=(b>0?UtilMath.atan(a/b)+UtilMath.QUARTER_PI+UtilMath.HALF_PI:UtilMath.atan(a/b)+UtilMath.QUARTER_PI);
    a*=mag;
    b*=mag;
    p.line(x+a,y+b,x+UtilMath.sin(ang)*s,y+UtilMath.cos(ang)*s);
    ang+=b>0?UtilMath.HALF_PI:-UtilMath.HALF_PI;
    p.line(x+a,y+b,x+UtilMath.sin(ang)*s,y+UtilMath.cos(ang)*s);
  }
  public static void rectArrow(Graphics g,float x,float y,float w,float h,float size) {
    float scale=UtilMath.dist(0,0,w,h);
    drawRectArraw(g,x,y,w/scale*size,h/scale*size);
  }
  public static void drawRectArraw(Graphics g,float x,float y,float w,float h) {
    g.p.line(x+h,y-w,x+w,y+h);
    g.p.line(x-h,y+w,x+w,y+h);
  }
  public static MoveableCross createCross(UtilScreen2D p,Color strokeColor) {
    GetFloat gf;
    if(p.isAndroid) gf=()->UtilMath.constrain(64f/p.cam2d.scale(),16,32);
    else gf=()->UtilMath.constrain(16f/p.cam2d.scale(),16,32);
    MoveableCross out=new MoveableCross(gf,gf);
    out.strokeColor=strokeColor;
    return out;
  }
  @FunctionalInterface
  public interface LineTextWidthConsumer{
    public void put(int xPos,float charWidth,float TextWidth);
  }
}
