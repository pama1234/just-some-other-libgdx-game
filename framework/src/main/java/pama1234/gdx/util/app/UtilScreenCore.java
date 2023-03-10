package pama1234.gdx.util.app;

import static pama1234.math.UtilMath.floor;
import static pama1234.math.UtilMath.max;
import static pama1234.math.UtilMath.min;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.element.CameraController;
import pama1234.gdx.util.element.MultiChunkFont;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.InputListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.util.listener.LifecycleListener;
import pama1234.util.listener.ServerEntityListener;
import pama1234.util.wrapper.ServerEntityCenter;

/**
 * UtilScreen太大了，因此抽离了一部分内容到此类，抽离的规则未确定
 * 
 * @see UtilScreen
 */
public abstract class UtilScreenCore implements Screen,InputListener,LifecycleListener{
  public final float fontGridSize=4;
  public boolean flip=true;
  public int width,height;
  public int frameCount;
  public float frameRate;
  // public float frameDelta;
  //---
  public boolean mouseMoved;
  public MouseInfo mouse;
  public Vector3 vectorCache;
  public int touchCount;
  public TouchInfo[] touches=new TouchInfo[16];
  public boolean keyPressed;
  /** normally "a" and "A" will be treat as 'A' */
  public char key;
  /** see {@link com.badlogic.gdx.Input.Keys gdx.Input.Keys} for keyCodes */
  public int keyCode;
  public boolean shift,ctrl,alt;
  public IntArray keyPressedArray;
  public CameraController cam;
  public OrthographicCamera screenCam;
  public Camera usedCamera;
  public SpriteBatch fontBatch,imageBatch;
  public TinyVGShapeDrawer tvgDrawer;
  public MultiChunkFont font;
  public Color textColor,fillColor,strokeColor;
  public boolean fill=true,stroke=true;
  public float defaultStrokeWeight,strokeWeight;
  public ShapeRenderer rFill,rStroke;
  public boolean background=true;
  public Color backgroundColor;
  public EntityCenter<UtilScreen,EntityListener> center,centerCam,centerScreen;
  public ServerEntityCenter<ServerEntityListener> serverCenter;
  public UtilInputProcesser inputProcessor;
  public Random rng=new Random();
  /**
   * 不好形容
   */
  public float u;
  /**
   * 文本的大小
   */
  public float pu;
  /**
   * 文本的放大倍数，为了清晰显示因此只能是整数
   */
  public int pus;
  public boolean stop;
  //---
  // public boolean isAndroid=true;
  public boolean isAndroid=Gdx.app.getType()==ApplicationType.Android;
  public boolean isKeyPressed(int in) {
    return keyPressedArray.contains(in);
  }
  public MultiChunkFont genMultiChunkFont() {
    return new MultiChunkFont(new FileHandle[] {
      Gdx.files.internal("unifont/0/unifont-0.fnt"),
      Gdx.files.internal("unifont/1/unifont-1.fnt"),
      Gdx.files.internal("unifont/2/unifont-2.fnt"),
      Gdx.files.internal("unifont/3/unifont-3.fnt"),
      Gdx.files.internal("unifont/4/unifont-4.fnt"),
      Gdx.files.internal("unifont/5/unifont-5.fnt"),
      Gdx.files.internal("unifont/6/unifont-6.fnt"),
      Gdx.files.internal("unifont/7/unifont-7.fnt"),
      Gdx.files.internal("unifont/8/unifont-8.fnt"),
      Gdx.files.internal("unifont/9/unifont-9.fnt"),
      Gdx.files.internal("unifont/10/unifont-10.fnt"),
      Gdx.files.internal("unifont/11/unifont-11.fnt"),
      Gdx.files.internal("unifont/12/unifont-12.fnt"),
      Gdx.files.internal("unifont/13/unifont-13.fnt"),
      null,
      Gdx.files.internal("unifont/15/unifont-15.fnt"),
    },true);
  }
  public void beginShape() {
    rFill.begin(ShapeType.Filled);
    rStroke.begin(ShapeType.Line);
  }
  public void endShape() {
    rFill.end();
    rStroke.end();
  }
  public void setCamera(Camera in) {
    if(usedCamera!=in) usedCamera=in;
    else return;
    setMatrix(in.combined);
  }
  public void setMatrix(Matrix4 combined) {
    fontBatch.setProjectionMatrix(combined);
    imageBatch.setProjectionMatrix(combined);
    rFill.setProjectionMatrix(combined);
    rStroke.setProjectionMatrix(combined);
  }
  @Override
  public void init() {}
  public abstract void setup();
  public abstract void update();
  public abstract void display();
  public abstract void frameResized();
  public abstract Vector3 unproject(float x,float y);
  @Override
  public void resize(int w,int h) {
    innerResize(w,h);
    // serverCenter.frameResized(w,h);
    frameResized();
  }
  public void innerResize(int w,int h) {
    width=w;
    height=h;
    if(isAndroid) u=min(w,h)/8f;
    else u=min(w,h)/16f;
    pus=max(1,floor(u/16f));
    pu=pus*16;
    cam.preResizeEvent(w,h);
    screenCam.setToOrtho(flip,w,h);
    //---
    center.frameResized(w,h);
  }
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
    // center.refresh();
    center.dispose();
    serverCenter.dispose();
  }
  public void strokeWeight(float in) {
    if(in<=0) return;
    Gdx.gl.glLineWidth(strokeWeight=in);
  }
  @Deprecated //use FileUtil instead
  public Texture loadTexture(String in) {
    return new Texture(Gdx.files.internal(in));
  }
  public void sleep(long i) {
    try {
      Thread.sleep(i);
    }catch(InterruptedException e) {
      e.printStackTrace();
      // System.out.println(e);
    }
  }
  public float random(float max) {
    return rng.nextFloat()*max;
  }
  public float random(float min,float max) {
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
    for(Object i:ins) sb.append(i).append(" ");
    Gdx.app.log("print[A",sb.toString());
  }
  public <T> T debug(T out) {
    System.out.println(out);
    return out;
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
}
