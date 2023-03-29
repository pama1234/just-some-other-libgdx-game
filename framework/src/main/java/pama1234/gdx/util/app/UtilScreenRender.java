package pama1234.gdx.util.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.gdxtinyvg.TinyVG;
import pama1234.math.UtilMath;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenRender extends UtilScreenColor{
  @Deprecated
  public float[] polygonCache=new float[8];
  //---------------------------------------------------------------------------
  public void image(Texture in,float x,float y) {
    imageBatch.begin();
    // imageBatch.draw(in,x,y-in.getHeight(),in.getWidth(),-in.getHeight());//nop
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void tvg(TinyVG in) {//TODO
    imageBatch.begin();
    in.draw(tvgDrawer);
    imageBatch.end();
  }
  public void image(Texture in,float x,float y,float z) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void image(TextureRegion in,float x,float y) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void image(Texture in,float x,float y,float w,float h) {
    imageBatch.begin();
    imageBatch.draw(in,x,y,w,h);
    imageBatch.end();
  }
  public void imageCenterPos(Texture in,float x,float y,float w,float h) {
    imageBatch.begin();
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
    imageBatch.end();
  }
  @Deprecated
  public void imageCenterPos(Texture in,float x,float y,float z,float w,float h) {
    // imageBatch.begin();
    // imageBatch.draw(in,x-w/2,y-h/2,z,w,h);
    // imageBatch.end();
  }
  public void image(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.begin();
    innerImage(in,x,y,w,h);
    imageBatch.end();
  }
  public void innerImage(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.draw(in,x,y,w,h);
  }
  public void sprite(Sprite in) {
    imageBatch.begin();
    in.draw(imageBatch);
    imageBatch.end();
  }
  //---------------------------------------------------------------------------
  public void text(String in,float x,float y) {
    fontBatch.begin();
    font.text(in==null?"null":in,x,y);
    fontBatch.end();
  }
  public float textWidth(String in) {
    return font.textWidth(in);
  }
  public float textWidthNoScale(String in) {
    return font.textWidthNoScale(in);
  }
  public void textScale(float in) {
    font.textScale(in);
  }
  public float textScale() {
    return font.scale;
  }
  public void textSize(float in) {
    font.size(in);
  }
  public float textSize() {
    return font.size;
  }
  public float fontScale(float ts) {
    if(ts>=1) return MathUtils.floor(ts);
    else return Math.max(MathUtils.floor(ts*fontGridSize)/fontGridSize,1/fontGridSize);
  }
  //---------------------------------------------------------------------------
  public void drawText(String in,float x,float y) {
    fontBatch.begin();
    // font.setColor(1,1,1,1);
    font.draw(fontBatch,in==null?"null":in,x,y);
    fontBatch.end();
  }
  @Deprecated
  public void drawTextCenter(String in,float x,float y) {
    fontBatch.begin();
    font.draw(fontBatch,in==null?"null":in,x,y);
    fontBatch.end();
  }
  public void setTextScale(float in) {
    font.getData().setScale(in);
  }
  //---------------------------------------------------------------------------
  public void clear() {
    ScreenUtils.clear(0,0,0,0,true);
  }
  public void background(int r,int g,int b) {
    ScreenUtils.clear(r/255f,g/255f,b/255f,1,true);
  }
  public void background(int gray,int a) {
    ScreenUtils.clear(gray/255f,gray/255f,gray/255f,a/255f,true);
  }
  public void background(int in) {
    background(in,in,in);
  }
  public void background(Color in) {
    ScreenUtils.clear(in,true);
  }
  //---------------------------------------------------------------------------
  public void circle(float x,float y,float s) {//TODO
    int seg=circleSeg(s);
    circle(x,y,s,seg);
  }
  public static int circleSeg(float s) {
    return UtilMath.max((int)(MathUtils.PI*s),6);
  }
  public void circle(float x,float y,float s,int seg) {
    if(fill) {
      rFill.circle(x,y,s,seg);
      rFill.flush();
    }
    if(stroke) {
      rStroke.circle(x,y,s,seg);
      rStroke.flush();
    }
  }
  //TODO
  @Deprecated
  public void circle(float x,float y,float z,float s,int seg) {
    if(fill) {
      // rFill.circle(x,y,z,s,seg);
      rFill.flush();
    }
    if(stroke) {
      // rStroke.circle(x,y,z,s,seg);
      rStroke.flush();
    }
  }
  //---------------------------------------------------------------------------
  public void rect(float x,float y,float w,float h) {
    if(fill) {
      rFill.rect(x,y,w,h);
      rFill.flush();
    }
    if(stroke) {
      rStroke.rect(x,y,w,h);
      rStroke.flush();
    }
  }
  public void triangle(float x1,float y1,float x2,float y2,float x3,float y3) {
    if(fill) {
      rFill.triangle(x1,y1,x2,y2,x3,y3);
      rFill.flush();
    }
    if(stroke) {
      rStroke.triangle(x1,y1,x2,y2,x3,y3);
      rStroke.flush();
    }
  }
  public void setPolygonCache(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4) {
    polygonCache[0]=x1;
    polygonCache[1]=y1;
    polygonCache[2]=x2;
    polygonCache[3]=y2;
    //---
    polygonCache[4]=x3;
    polygonCache[5]=y3;
    polygonCache[6]=x4;
    polygonCache[7]=y4;
  }
  @Deprecated
  public void quad(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4) {
    // if(fill|stroke) setPolygonCache(x1,y1,x2,y2,x3,y3,x4,y4);
    // if(fill) {
    //   rFill.polygon(polygonCache);
    //   rFill.flush();
    // }
    if(stroke) {
      setPolygonCache(x1,y1,x2,y2,x3,y3,x4,y4);
      rStroke.polygon(polygonCache);
      rStroke.flush();
    }
  }
  //---------------------------------------------------------------------------
  @Deprecated
  public void arc(float x1,float y1,float x2,float y2,float a,float b) {
    //TODO
  }
  public void arc(float x,float y,float radius,float start,float degrees) {
    if(stroke) {
      rStroke.arcNoBorder(x,y,radius,start,degrees,UtilMath.max(1,(int)(6*(float)Math.cbrt(radius)*(degrees/360))));
      rStroke.flush();
    }
  }
  public void dot(float x,float y,int color) {
    rFill.getColor().set(color);
    rFill.rect(x-0.5f,y-0.5f,1,1);
    rFill.flush();
  }
  public void line(float x1,float y1,float x2,float y2) {
    if(stroke) {
      rStroke.line(x1,y1,x2,y2);
      rStroke.flush();
    }
  }
  public void cross(float x,float y,float w,float h) {
    line(x-w,y,x+w,y);
    line(x,y-h,x,y+h);
  }
  @Deprecated
  public void border(float x,float y,float w,float h,float weight) {
    // beginBlend();
    // fillRect(0,0,w,h);
    fill(128,128,128,204);
    fillRect(x,y,w,weight);
    fillRect(x,y,weight,h);
    fill(255,255,255,204);
    fillRect(x,y+h-weight,w,weight);
    fillRect(x+w-weight,y,weight,h);
    // endBlend();
  }
  public void fillRect(float x,float y,float w,float h) {
    rFill.rect(x,y,w,h);
    rFill.flush();
  }
}