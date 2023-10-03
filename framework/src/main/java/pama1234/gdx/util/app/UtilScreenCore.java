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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import hhs.gdx.hsgame.tools.LoopThread;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.MultiChunkFont;
import pama1234.gdx.util.graphics.ShapeRendererBase.ShapeType;
import pama1234.gdx.util.graphics.UtilPolygonSpriteBatch;
import pama1234.gdx.util.graphics.UtilShapeRenderer;
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
  public boolean doUpdateThread;
  public LoopThread updateThread;
  // public float frameDelta;
  //---------------------------------------------------------------------------
  public boolean mouseMoved;
  public MouseInfo mouse;
  public Vector3 vectorCache;
  public int touchCount;
  public TouchInfo[] touches=new TouchInfo[16];
  public boolean grabCursor;
  public boolean is3d;
  //---------------------------------------------------------------------------
  public boolean keyPressed;
  /** normally "a" and "A" will be treat as 'A' */
  public char key;
  /** see {@link com.badlogic.gdx.Input.Keys gdx.Input.Keys} for keyCodes */
  public int keyCode;
  public boolean shift,ctrl,alt;
  public IntArray keyPressedArray;

  public boolean focus;

  public CameraController cam;
  public OrthographicCamera screenCam;
  public Camera usedCamera;
  public SpriteBatch fontBatch,imageBatch;
  public TinyVGShapeDrawer tvgDrawer;
  public MultiChunkFont font;
  //---------------------------------------------------------------------------
  public FontStyle fontStyle=new FontStyle();
  public Color textColor,fillColor,strokeColor;
  public boolean fill=true,stroke=true;
  public float defaultStrokeWeight,strokeWeight;
  public UtilShapeRenderer rFill,rStroke;
  public UtilPolygonSpriteBatch pFill;
  public boolean background=true;
  public Color backgroundColor;
  /**
   * 仅会执行存放在list中的所有实体的update方法和监听事件，不会执行display方法
   */
  public EntityCenter<UtilScreen,EntityListener> center;
  /**
   * 执行update和display方法，以相机视角为坐标变幻标准
   * </p>
   * TODO 很明显，这东东很丑，应当改为更高效的实现
   */
  public EntityCenter<UtilScreen,EntityListener> centerCam;
  /**
   * 执行update和display方法，以屏幕为坐标变幻标准
   * </p>
   * TODO 很明显，这东东很丑，应当改为更高效的实现
   */
  public EntityCenter<UtilScreen,EntityListener> centerScreen;
  /**
   * 类似center但是存放的是ServerEntityListener
   */
  public ServerEntityCenter<ServerEntityListener> serverCenter;
  public UtilInputProcesser inputProcessor;
  public Random rng=new Random();
  /**
   * 按钮和其他UI的基本单位长度
   */
  public float u;
  /**
   * 文本的大小
   */
  public float pu;
  /**
   * 文本的放大倍数，为了清晰显示因此只能是整数
   */
  // TODO 电脑端Android调试模式下无法在init环节初始化pus值，调整执行顺序
  public int pus=1;
  public boolean stop;
  //---------------------------------------------------------------------------
  // public boolean isAndroid=true;
  public boolean isAndroid=Gdx.app.getType()==ApplicationType.Android;
  //---------------------------------------------------------------------------
  public Matrix4[] matrixStack=new Matrix4[10];
  public int matrixStackPointer=-1;
  //---------------------------------------------------------------------------
  public Stage screenStage,camStage;
  public Viewport screenViewport,camViewport;

  public float multDist=1;
  public Button<?>[] buttons;
  public TextButton<?>[] textButtons;
  public int bu;
  // TODO
  public boolean fullSettings;
  //---------------------------------------------------------------------------
  public boolean isKeyPressed(int in) {
    return keyPressedArray.contains(in);
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
    pFill.setProjectionMatrix(combined);
    rStroke.setProjectionMatrix(combined);
  }
  @Override
  public void init() {}
  public abstract void setup();
  public abstract void update();
  public abstract void display();
  public abstract void displayWithCam();
  public abstract void frameResized();
  public abstract Vector3 screenToWorld(float x,float y);
  @Override
  public void resize(int w,int h) {
    innerResize(w,h);
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
    // fontBatch.dispose();
    // font.dispose();
    center.dispose();
    serverCenter.dispose();
    if(doUpdateThread) updateThread.stop=true;
  }
  //---------------------------------------------------------------------------
  /**
   * 移动至FileUtil
   * 
   * @param in
   * @return
   */
  @Deprecated
  public Texture loadTexture(String in) {
    return new Texture(Gdx.files.internal(in));
  }
  public void sleep(long i) {
    try {
      Thread.sleep(i);
    }catch(InterruptedException e) {
      e.printStackTrace();
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
  //---------------------------------------------------------------------------
  public int getButtonUnitLength() {
    return bu;
  }
  //---------------------------------------------------------------------------
  public void changeGrab() {
    Gdx.input.setCursorCatched(grabCursor=!grabCursor);
  }
  public void doGrab() {
    Gdx.input.setCursorCatched(grabCursor=true);
  }
  public void noGrab() {
    Gdx.input.setCursorCatched(grabCursor=false);
  }
  //---------------------------------------------------------------------------
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
  @Override
  public void focusGained() {}
  @Override
  public void focusLost() {}
  //---------------------------------------------------------------------------
  public void focusGainedOuter() {
    focus=true;
    center.focusGained();
    focusGained();
  }
  public void focusLostOuter() {
    focus=false;
    center.focusLost();
    focusLost();
  }
}