package pama1234.gdx.util.cam;

import static com.badlogic.gdx.Input.Keys.ALT_LEFT;
import static com.badlogic.gdx.Input.Keys.*;
import static pama1234.math.UtilMath.min;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.PathPoint3D;

public abstract class CameraController extends Entity<UtilScreen>{
  public Camera camera;
  public PerspectiveCamera pcam;
  public OrthographicCamera ocam;

  // public PathPoint point;
  public PathPoint3D point;

  public float frameU;
  public float frameScale;
  public float scx,scy;
  public float asox,asoy;
  public float bsox,bsoy;
  public float bavgsox,bavgsoy;
  public float iScale,iDist;
  public boolean activeUpdate;
  public boolean activeGrab=true;
  public int coolingCount;
  @Deprecated
  public CameraController(UtilScreen p) {
    super(p);
  }
  public CameraController(UtilScreen p,float x,float y,float z) {
    super(p);
    point=new PathPoint3D(x,y,z);
  }
  public abstract void preResizeEvent(int w,int h);
  //TODO
  @Deprecated
  public boolean inbox(float x,float y,float z) {
    return false;
  }
  @Override
  public void frameResized(int w,int h) {
    frameScale=frameU/min(w,h);
  }
  // public abstract float x();
  // public abstract float y();
  // public abstract float z();
  public abstract float scale();
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
  public float z() {
    return point.z();
  }
  public float isx(float ftScale) {
    return MathUtils.floor(x()/ftScale)*ftScale;
  }
  public float isy(float ftScale) {
    return MathUtils.floor(y()/ftScale)*ftScale;
  }
  public int ix() {
    return MathUtils.floor(x());
  }
  public int iy() {
    return MathUtils.floor(y());
  }
  public float fx() {
    return Tools.fractionalPart(x());
  }
  public float fy() {
    return Tools.fractionalPart(y());
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(activeGrab) {
      if(keyCode==ALT_LEFT) {
        p.changeGrab();
        coolingCount=1;
      }
      if(keyCode==ESCAPE) {
        p.noGrab();
        coolingCount=1;
      }
    }
  }
  @Override
  public void focusLost() {
    if(p.alt) p.changeGrab();
  }
}
