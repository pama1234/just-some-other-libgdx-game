package pama1234.gdx.util.app;

import static pama1234.math.UtilMath.floor;
import static pama1234.math.UtilMath.max;
import static pama1234.math.UtilMath.min;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ScreenUtils;

import pama1234.gdx.util.element.CameraController;
import pama1234.gdx.util.element.MultiChunkFont;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.InputListener;
import pama1234.gdx.util.listener.LifecycleListener;
import pama1234.gdx.util.listener.ServerEntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.ServerEntityCenter;

public abstract class UtilScreen implements Screen,InputListener,LifecycleListener{
  public boolean flip=true;
  public int width,height;
  public int frameCount;
  public float frameRate;
  //---
  public boolean mouseMoved;
  public MouseInfo mouse;
  public Vector3 vectorCache;
  public int touchCount;
  public TouchInfo[] touches=new TouchInfo[16];
  public boolean keyPressed;
  /** normally "a" and "A" will be treat as 'A' */
  public char key;
  /** see com.badlogic.gdx.Input.Keys for keyCodes */
  public int keyCode;
  public boolean shift,ctrl,alt;
  public IntArray keyPressedArray;
  //---
  public CameraController cam;
  //---
  public OrthographicCamera screenCam;
  public Camera usedCamera;
  public SpriteBatch fontBatch,imageBatch;
  public MultiChunkFont font;
  public Color textColor,fillColor,strokeColor;
  public boolean fill=true,stroke=true;
  public float strokeWeight;
  public ShapeRenderer rFill,rStroke;
  public boolean background=true;
  public Color backgroundColor;
  public EntityCenter<EntityListener> center;
  public ServerEntityCenter<ServerEntityListener> serverCenter;
  public UtilInputProcesser inputProcessor;
  public Random rng=new Random();
  public float u,pu;
  public int pus;
  public boolean stop;
  @Override
  public void init() {}
  public abstract void setup();
  @Override
  public void render(float delta) {
    frameRate=delta;
    mouse.update(this);
    for(TouchInfo i:touches) i.update(this);
    inputProcessor.update();
    center.update();
    serverCenter.update();
    update();
    beginDraw();
    if(background) background(backgroundColor);
    withCam();
    center.display();
    serverCenter.display();
    display();
    inputProcessor.display();
    endDraw();
    frameCount++;
  }
  public void beginDraw() {
    rFill.begin(ShapeType.Filled);
    rStroke.begin(ShapeType.Line);
  }
  public void endDraw() {
    rFill.end();
    rStroke.end();
  }
  public void setCamera(Camera in) {
    if(usedCamera!=in) usedCamera=in;
    fontBatch.setProjectionMatrix(in.combined);
    imageBatch.setProjectionMatrix(in.combined);
    rFill.setProjectionMatrix(in.combined);
    rStroke.setProjectionMatrix(in.combined);
  }
  public abstract Vector3 unproject(float x,float y);
  public abstract void update();
  public abstract void display();
  @Override
  public void resize(int w,int h) {
    width=w;
    height=h;
    if(Gdx.app.getType()==ApplicationType.Android) u=min(w,h)/8;
    else u=min(w,h)/16;
    pus=max(1,floor(u/16f));
    pu=pus*16;
    // camResizeEvent(w,h);
    cam.preResizeEvent(w,h);
    // System.out.println("a");
    screenCam.setToOrtho(flip,w,h);
    center.frameResized(w,h);
    frameResized();
  }
  // public void camResizeEvent(int w,int h) {
  //   cam.o.setToOrtho(flip,w,h);
  //   cam.oInt.setToOrtho(flip,w,h);
  // }
  public abstract void frameResized();
  @Override
  public void pause() {
    center.pause();
  }
  @Override
  public void resume() {
    center.resume();
    serverCenter.resume();
  }
  @Override
  public void hide() {}
  @Override
  public void dispose() {
    stop=true;
    fontBatch.dispose();
    font.dispose();
    // fontGenerator.dispose();
    center.dispose();
    serverCenter.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void touchStarted(TouchInfo info) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
  public void strokeWeight(float in) {
    Gdx.gl.glLineWidth(strokeWeight=in);
  }
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
  public void textColor(int r,int g,int b) {
    textColor.set(r/255f,g/255f,b/255f,1);
    font.color(textColor);
  }
  public void textColor(int in) {
    textColor(in,in,in);
  }
  public void textColor(int alpha,int gray) {
    textColor.set(alpha/255f,alpha/255f,alpha/255f,gray/255f);
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
  public void beginBlend() {
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
  public void textScale(float in) {
    font.textScale(in);
  }
  public void text(String in,float x,float y) {
    fontBatch.begin();
    font.text(in==null?"null":in,x,y);
    fontBatch.end();
  }
  public void textSize(float in) {
    font.size(in);
  }
  public final float fontGridSize=4;
  public float fontScale(float ts) {
    if(ts>=1) return MathUtils.floor(ts);
    else return Math.max(MathUtils.floor(ts*fontGridSize)/fontGridSize,1/fontGridSize);
  }
  //----------------------------------------------------
  public boolean isKeyPressed(int in) {
    return keyPressedArray.contains(in);
  }
  //----------------------------------------------------
  public float random(int max) {
    return rng.nextFloat()*max;
  }
  public float random(int min,int max) {
    max-=min;
    return rng.nextFloat()*max+min;
  }
  public void println(String in) {
    Gdx.app.log("print S",in);
  }
  public void println(int in) {
    Gdx.app.log("print I",Integer.toString(in));
  }
  public void println(float in) {
    Gdx.app.log("print F",Float.toString(in));
  }
  public void println(Object... ins) {
    StringBuilder sb=new StringBuilder();
    for(Object i:ins) {
      sb.append(i);
      sb.append(" ");
    }
    Gdx.app.log("print[A",sb.toString());
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
  public void clear() {
    ScreenUtils.clear(0,0,0,0,true);
  }
  public <T> T debug(T out) {
    System.out.println(out);
    return out;
  }
}
