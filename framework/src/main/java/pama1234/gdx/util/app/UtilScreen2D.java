package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.element.Style;
import pama1234.util.function.GetFloat;

/**
 * @see ScreenCore2D
 */
public abstract class UtilScreen2D extends UtilScreen{
  public Style[] styleStack=new Style[10];
  public int styleStackPointer=-1;
  //---
  public CameraController2D cam2d;//TODO do we need this?
  //---
  public GetFloat camStrokeWeight=()->u/16*cam2d.scale.pos;
  @Override
  public void show() {
    preInit();
    init();
    postInit();
    setup();
  }
  @Override
  public CameraController createCamera() {
    return cam2d=new CameraController2D(this,CameraController2D.NONE,0,0,1,0,Gdx.app.getType()==ApplicationType.Desktop?640:160);
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
    strokeWeight(defaultStrokeWeight=camStrokeWeight.get());
  }
}