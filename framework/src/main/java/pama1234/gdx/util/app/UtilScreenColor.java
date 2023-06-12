package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * 此中间类主要放颜色赋值方面的方法
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenColor extends UtilScreenCore{
  public void textColor(int r,int g,int b,int a) {
    textColor.set(r/255f,g/255f,b/255f,a/255f);
    font.color(textColor);
  }
  public void textColor(int r,int g,int b) {
    textColor.set(r/255f,g/255f,b/255f,1);
    font.color(textColor);
  }
  public void textColor(int in) {
    textColor(in,in,in);
  }
  public void textColor(int gray,int alpha) {
    textColor.set(gray/255f,gray/255f,gray/255f,alpha/255f);
    font.color(textColor);
  }
  public void setTextColor(int gray) {
    font.getColor().set(gray/255f,gray/255f,gray/255f,1);
    // setTextColor(gray,255);
  }
  public void setTextColor(Color in) {
    font.getColor().set(in);
  }
  public void setTextColor(int gray,int alpha) {
    font.getColor().set(gray/255f,gray/255f,gray/255f,alpha/255f);
  }
  //---------------------------------------------------------------------------
  public void fill(Color in) {
    fillColor.set(in);
    rFill.setColor(fillColor);
  }
  public void fill(int gray) {
    fill(gray,255);
  }
  public void fill(int gray,int a) {
    fillColor.set(gray/255f,gray/255f,gray/255f,a/255f);
    rFill.setColor(fillColor);
  }
  public void fill(int r,int g,int b) {
    fillColor.set(r/255f,g/255f,b/255f,1);
    rFill.setColor(fillColor);
  }
  public void fill(int r,int g,int b,int a) {
    fillColor.set(r/255f,g/255f,b/255f,a/255f);
    rFill.setColor(fillColor);
  }
  public void fillHex(int argb) {
    fill((argb>>16)&0xff,(argb>>8)&0xff,argb&0xff,(argb>>24)&0xff);
  }
  public void noFill() {
    fill=false;
  }
  public void doFill() {
    fill=true;
  }
  //---------------------------------------------------------------------------
  public void tint(Color in) {
    Color tc=imageBatch.getColor();
    tc.set(in);
    imageBatch.setColor(tc);
  }
  public void tint(int gray) {
    tint(gray,255);
  }
  public void tint(int gray,int a) {//TODO
    Color tc=imageBatch.getColor();
    tc.set(gray/255f,gray/255f,gray/255f,a/255f);
    imageBatch.setColor(tc);
  }
  public void tint(int r,int g,int b) {
    tint(r,g,b,255);
  }
  public void tint(int r,int g,int b,int a) {
    Color tc=imageBatch.getColor();
    tc.set(r/255f,g/255f,b/255f,a/255f);
    imageBatch.setColor(tc);
  }
  public void noTint() {
    imageBatch.setColor(imageBatch.getColor().set(1,1,1,1));
  }
  //---------------------------------------------------------------------------
  public void stroke(Color in) {
    strokeColor.set(in);
    rStroke.setColor(strokeColor);
  }
  public void stroke(Color in,int alpha) {
    strokeColor.set(in);
    strokeColor.a=alpha/255f;
    rStroke.setColor(strokeColor);
  }
  public void stroke(int gray) {
    stroke(gray,255);
  }
  public void stroke(int gray,int a) {
    strokeColor.set(gray/255f,gray/255f,gray/255f,a/255f);
    rStroke.setColor(strokeColor);
  }
  public void stroke(int r,int g,int b) {
    strokeColor.set(r/255f,g/255f,b/255f,1);
    rStroke.setColor(strokeColor);
  }
  public void stroke(int r,int g,int b,int a) {
    strokeColor.set(r/255f,g/255f,b/255f,a/255f);
    rStroke.setColor(strokeColor);
  }
  public void noStroke() {
    stroke=false;
  }
  public void doStroke() {
    stroke=true;
  }
  public void strokeWeight(float in) {
    if(in<=0) return;
    Gdx.gl.glLineWidth(strokeWeight=in);
  }
  //---------------------------------------------------------------------------
  public void color(Color c,float gray) {
    c.set(gray/255f,gray/255f,gray/255f,1);
  }
  public void color(Color c,float gray,float a) {
    c.set(gray/255f,gray/255f,gray/255f,a/255f);
  }
  public void color(Color c,float r,float g,float b) {
    c.set(r/255f,g/255f,b/255f,1);
  }
  public void color(Color c,float r,float g,float b,float a) {
    c.set(r/255f,g/255f,b/255f,a/255f);
  }
  public static Color color(float gray) {
    return new Color(gray/255f,gray/255f,gray/255f,1);
  }
  public static Color color(float gray,float a) {
    return new Color(gray/255f,gray/255f,gray/255f,a/255f);
  }
  public static Color color(float r,float g,float b) {
    return new Color(r/255f,g/255f,b/255f,1);
  }
  public static Color color(float r,float g,float b,float a) {
    return new Color(r/255f,g/255f,b/255f,a/255f);
  }
  @Deprecated
  public static Color colorFromInt(int argb) {
    return colorFromInt(new Color(),argb);
  }
  public static Color colorFromInt(Color c,int argb) {
    Color.argb8888ToColor(c,argb);
    return c;
  }
  // TODO 移动到Tools
  @Deprecated
  public static Color lerpColor(int a,int b,float pos) {
    Color out=new Color();
    lerpColor(new Color(a),new Color(b),out,pos);
    return out;
  }
  @Deprecated
  public static Color lerpColor(Color a,Color b,float pos) {
    Color out=new Color();
    lerpColor(a,b,out,pos);
    return out;
  }
  public static void lerpColor(Color a,Color b,Color out,float pos) {
    if(pos==0) out.set(a);
    else if(pos==1) out.set(b);
    float tr=b.r-a.r,
      tg=b.g-a.g,
      tb=b.b-a.b,
      ta=b.a-a.a;
    out.set(a.r+tr*pos,a.g+tg*pos,a.b+tb*pos,a.a+ta*pos);
  }
  //---------------------------------------------------------------------------
  public void backgroundColor(int r,int g,int b) {
    color(backgroundColor,r,g,b);
  }
  public void backgroundColor(int gray,int a) {
    color(backgroundColor,gray,a);
  }
  public void backgroundColor(int in) {
    color(backgroundColor,in);
  }
  public void backgroundColor(Color in) {
    backgroundColor.set(in);
  }
}