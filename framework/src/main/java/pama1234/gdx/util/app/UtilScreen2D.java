package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.element.CameraController;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.element.Style;
import pama1234.math.UtilMath;
import pama1234.util.function.ExecuteF;

/**
 * @see ScreenCore2D
 */
public abstract class UtilScreen2D extends UtilScreen{
  public Style[] styleStack=new Style[10];
  public int styleStackPointer=-1;
  //---
  public CameraController2D cam2d;//TODO do we need this?
  @Override
  public void show() {
    preInit();
    init();
    postInit();
    setup();
  }
  @Override
  public CameraController createCamera() {
    return cam2d=new CameraController2D(this,false,0,0,1,0,Gdx.app.getType()==ApplicationType.Desktop?640:160);
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
  public void drawWithMatrix(float dx,float dy,float deg,ExecuteF e) {
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
}