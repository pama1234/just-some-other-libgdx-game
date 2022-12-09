package pama1234.gdx.util.app;

import static pama1234.math.UtilMath.max;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import pama1234.gdx.util.info.TouchInfo;

/**
 * UtilScreenCore -> UtilScreen 此中间类主要放渲染相关的东东
 */
public abstract class UtilScreen extends UtilScreenCore{
  @Override
  public void render(float delta) {
    frameRate=delta;
    mouse.update(this);
    for(TouchInfo i:touches) i.update(this);
    inputProcessor.update();
    center.update();
    // centerScreen.update();
    serverCenter.update();
    update();
    beginDraw();
    if(background) background(backgroundColor);
    withCam();
    serverCenter.display();
    centerCam.display();
    displayWithCam();
    withScreen();
    centerScreen.display();
    display();
    inputProcessor.display();
    endDraw();
    frameCount++;
  }
  public abstract void displayWithCam();
  public void image(Texture in,float x,float y) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
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
    imageBatch.draw(in,x,y,w,h);
    imageBatch.end();
  }
  public void sprite(Sprite in) {
    imageBatch.begin();
    in.draw(imageBatch);
    imageBatch.end();
  }
  public void text(String in,float x,float y) {
    fontBatch.begin();
    font.text(in==null?"null":in,x,y);
    fontBatch.end();
  }
  public void textScale(float in) {
    font.textScale(in);
  }
  public void textSize(float in) {
    font.size(in);
  }
  public float fontScale(float ts) {
    if(ts>=1) return MathUtils.floor(ts);
    else return Math.max(MathUtils.floor(ts*fontGridSize)/fontGridSize,1/fontGridSize);
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
  public void stroke(Color in) {
    strokeColor.set(in);
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
  public void beginBlend() {//TODO
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  public void endBlend() {
    Gdx.gl.glDisable(GL20.GL_BLEND);
  }
  public void circle(float x,float y,float s) {//TODO
    int seg=circleSeg(s);
    circle(x,y,s,seg);
  }
  public static int circleSeg(float s) {
    return max((int)(MathUtils.PI*s),6);
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
  public void withScreen() {
    setCamera(screenCam);
    textScale(pus);
  }
  public void withCam() {
    setCamera(cam.camera);
    textScale(1);
  }
  public void fillRect(float x,float y,float w,float h) {
    rFill.rect(x,y,w,h);
    rFill.flush();
  }
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
  public Color color(float gray) {
    return new Color(gray/255f,gray/255f,gray/255f,1);
  }
  public Color color(float gray,float a) {
    return new Color(gray/255f,gray/255f,gray/255f,a/255f);
  }
  public Color color(float r,float g,float b) {
    return new Color(r/255f,g/255f,b/255f,1);
  }
  public Color color(float r,float g,float b,float a) {
    return new Color(r/255f,g/255f,b/255f,a/255f);
  }
  @Deprecated
  public void border(float x,float y,float w,float h,float weight) {
    beginBlend();
    // fillRect(0,0,w,h);
    fill(128,128,128,204);
    fillRect(x,y,w,weight);
    fillRect(x,y,weight,h);
    fill(255,255,255,204);
    fillRect(x,y+h-weight,w,weight);
    fillRect(x+w-weight,y,weight,h);
    endBlend();
  }
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
