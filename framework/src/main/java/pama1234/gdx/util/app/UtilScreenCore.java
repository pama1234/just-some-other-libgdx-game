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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.element.UtilPolygonSpriteBatch;
import pama1234.gdx.util.element.UtilShapeRenderer;
import pama1234.gdx.util.font.MultiChunkFont;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.InputListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.transform.Pose3D;
import pama1234.math.vec.Vec3f;
import pama1234.util.function.ExecuteFunction;
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
  public int pus;
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
  //---
  public float multDist=1;
  public Button<?>[] buttons;
  public TextButton<?>[] textButtons;
  public int bu;
  public boolean fullSettings;
  //---------------------------------------------------------------------------
  public boolean isKeyPressed(int in) {
    return keyPressedArray.contains(in);
  }
  public static MultiChunkFont genMultiChunkFont() {
    return genMultiChunkFont(true);
  }
  public static MultiChunkFont genMultiChunkFont(boolean flip) {
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
    },flip,true);
  }
  public void beginShape() {
    rFill.begin(ShapeType.Filled);
    rStroke.begin(ShapeType.Line);
    // pFill.begin();
  }
  public void endShape() {
    rFill.end();
    rStroke.end();
    // pFill.end();
  }
  public void setCamera(Camera in) {
    if(usedCamera!=in) usedCamera=in;
    else return;
    setMatrix(in.combined);
    // textScale(1);
    // strokeWeight(1);
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
    center.dispose();
    serverCenter.dispose();
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
  //---------------------------------------------------------------------------
  public Matrix4 matrix() {
    if(matrixStackPointer<0) return usedCamera.combined;
    return matrixStack[matrixStackPointer];
  }
  public void pushMatrix() {
    Matrix4 tmat=matrixStack[matrixStackPointer+1];
    Matrix4 matIn=matrixStackPointer<0?usedCamera.combined:matrixStack[matrixStackPointer];
    matrixStack[matrixStackPointer+1]=tmat==null?matIn.cpy():tmat.set(matIn);
    matrixStackPointer++;
    setMatrix(matrix());
  }
  public void popMatrix() {
    matrixStackPointer--;
    setMatrix(matrix());
  }
  public void clearMatrix() {
    matrixStackPointer=-1;
    // Arrays.fill(matrixStack,null);
    setMatrix(usedCamera.combined);
  }
  //---------------------------------------------------------------------------
  @Deprecated
  public void pushMatrix(float dx,float dy,float deg) {
    pushMatrix();
    translate(dx,dy);
    rotate(deg);
  }
  @Deprecated
  public void drawWithMatrix(float dx,float dy,float deg,ExecuteFunction e) {
    pushMatrix(dx,dy,deg);
    e.execute();
    popMatrix();
  }
  @Deprecated
  public void pushStyle() {
    //TODO
  }
  @Deprecated
  public void popStyle() {
    //TODO
  }
  @Deprecated
  public void clearStyle() {
    //TODO
  }
  public void translate(float dx,float dy) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,0);
    setMatrix(matrix);
  }
  public void rotate(float rad) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(rad));
    setMatrix(matrix);
  }
  public void scale(float in) {
    Matrix4 matrix=matrix();
    matrix.scale(in,in,in);
    setMatrix(matrix);
  }
  //---------------------------------------------------------------------------
  public void translate(float dx,float dy,float dz) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,dz);
    setMatrix(matrix);
  }
  public void translate(Vec3f vec) {
    translate(vec.x,vec.y,vec.z);
  }
  public void rotate(float rx,float ry,float rz) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,UtilMath.deg(rx));
    matrix.rotate(0,1,0,UtilMath.deg(ry));
    matrix.rotate(0,0,1,UtilMath.deg(rz));
    setMatrix(matrix);
  }
  public void rotate(Vec3f vec) {
    rotate(vec.x,vec.y,vec.z);
  }
  public void scale(float sx,float sy,float sz) {
    Matrix4 matrix=matrix();
    matrix.scale(sx,sy,sz);
    setMatrix(matrix);
  }
  public void scale(Vec3f vec) {
    scale(vec.x,vec.y,vec.z);
  }
  public void pose(Pose3D pose) {
    rotate(pose.rotate);
    translate(pose.pos);
    scale(pose.scale);
  }
  //---------------------------------------------------------------------------
  public int getButtonUnitLength() {
    return bu;
  }
  public void addScreenTextFields(TextField... in) {
    for(TextField e:in) screenStage.addActor(e);
  }
  public void addCamTextFields(TextField... in) {
    for(TextField e:in) camStage.addActor(e);
  }
  public void removeCamTextFields(TextField... in) {
    for(TextField e:in) camStage.getRoot().removeActor(e);
  }
}