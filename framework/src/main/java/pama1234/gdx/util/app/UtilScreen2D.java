package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.element.Style;
import pama1234.gdx.util.element.UtilShapeRenderer;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.util.function.ExecuteF;
import pama1234.util.wrapper.ServerEntityCenter;

/**
 * @see ScreenCore2D
 */
public abstract class UtilScreen2D extends UtilScreen{
  public Matrix4[] matrixStack=new Matrix4[10];
  public int matrixStackPointer=-1;
  //---
  public Style[] styleStack=new Style[10];
  public int styleStackPointer=-1;
  public CameraController2D cam2d;//TODO do we need this?
  @Override
  public void show() {
    screenCam=new OrthographicCamera();
    imageBatch=createSpriteBatch();
    tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    Gdx.input.setInputProcessor(inputProcessor=new UtilInputProcesser(this));
    center=new EntityCenter<>(this);
    center.list.add(cam=cam2d=new CameraController2D(this,false,0,0,1,0,Gdx.app.getType()==ApplicationType.Desktop?640:160));
    center.list.add(centerCam=new EntityCenter<>(this));
    center.list.add(centerScreen=new EntityCenter<>(this));
    init();
    fontBatch=createSpriteBatch();
    font=genMultiChunkFont();
    font.fontBatch=fontBatch;
    textColor=new Color(0,0,0,1);
    font.color(textColor);
    // genFont();
    fillColor=new Color(1,1,1,1);
    strokeColor=new Color(0,0,0,1);
    rFill=new UtilShapeRenderer();
    rStroke=new UtilShapeRenderer();
    rFill.setColor(fillColor);
    rStroke.setColor(strokeColor);
    vectorCache=new Vector3();
    mouse=new MouseInfo(this);
    for(int i=0;i<touches.length;i++) touches[i]=new TouchInfo(this);
    keyPressedArray=new IntArray(false,12);
    backgroundColor=new Color(1,1,1,0);
    serverCenter=new ServerEntityCenter<>();
    withCam();
    innerResize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    setup();
  }
  @Override
  public Vector3 unproject(float x,float y) {
    vectorCache.set(x,y,0);
    cam.camera.unproject(vectorCache);
    return vectorCache;
  }
  @Override
  public void withCam() {
    setCamera(cam.camera);
    textScale(1);
    strokeWeight(defaultStrokeWeight=u/16*cam2d.scale.pos);
  }
  @Deprecated
  public void pushMatrix(float dx,float dy,float deg) {
    pushMatrix();
    translate(dx,dy);
    rotate(deg);
  }
  @Deprecated
  public void clearMatrix() {
    matrixStackPointer=0;
    // Arrays.fill(matrixStack,null);
    setMatrix(usedCamera.combined);
  }
  public Matrix4 matrix() {
    if(matrixStackPointer<0) return usedCamera.combined;
    return matrixStack[matrixStackPointer];
  }
  @Deprecated
  public void drawWithMatrix(float dx,float dy,float deg,ExecuteF e) {
    pushMatrix(dx,dy,deg);
    e.execute();
    popMatrix();
  }
  @Deprecated
  public void pushMatrix() {
    matrixStack[matrixStackPointer+1]=matrixStackPointer<0?usedCamera.combined:matrixStack[matrixStackPointer].cpy();
    matrixStackPointer++;
    setMatrix(matrix());
  }
  @Deprecated
  public void popMatrix() {
    matrixStackPointer--;
    setMatrix(matrix());
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
}