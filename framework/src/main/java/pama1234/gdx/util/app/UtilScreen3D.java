package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.cam.CameraController3D;

public abstract class UtilScreen3D extends UtilScreen{
  public CameraController3D cam3d;
  public DecalBatch decalBatch;
  public ModelBatch modelBatch;
  public CameraGroupStrategy cameraGroupStrategy;

  public Ray rayCache=new Ray();
  public Plane planeCache=new Plane();
  public Vector3 intersectionCache=new Vector3();
  {
    is3d=true;
  }
  @Override
  public void show() {
    preInit();
    init();
    postInit();
    setup();
  }
  // @Override
  // public void doUpdate() {
  //   mouse.update(this);
  //   // if(grabCursor) {
  //   //   Vector3 tv=screenToWorld(width/2f,height/2f);
  //   //   mouse.set(tv.x,tv.y);
  //   // }
  //   for(TouchInfo i:touches) i.update(this);
  //   inputProcessor.update();
  //   center.update();
  //   serverCenter.update();
  //   update();
  // }
  @Override
  public void createRenderUtil() {
    super.createRenderUtil();
    cameraGroupStrategy=new CameraGroupStrategy(cam.camera);
    decalBatch=new DecalBatch(cameraGroupStrategy);//TODO
    modelBatch=new ModelBatch();
  }
  @Override
  public CameraController createCamera() {
    return cam3d=new CameraController3D(this,0,0,0,1,0,Gdx.app.getType()==ApplicationType.Desktop?640:160);
  }
  @Override
  public void withCam() {
    setCamera(cam.camera);
    textScale(1);
    // strokeWeight(defaultStrokeWeight=u/16*cam2d.scale.pos);
    strokeWeight(defaultStrokeWeight=u/16);
  }
  //TODO fix 3d screen vec unproject to world vec
  @Override
  public Vector3 screenToWorld(float x,float y) {
    Vector3 out=screenToWorld(x,y,0);
    if(out==null) {
      // TODO fix this
      // vectorCache.set(Float.NaN,Float.NaN,Float.NaN);
      // var pos=cam3d.point.pos;
      // vectorCache.set(pos.x,pos.y,pos.z);
      // vectorCache.set(0,0,0);
      return vectorCache;
    }
    return out;
  }
  // @Null
  public Vector3 screenToWorld(float x,float y,float zPlain) {
    vectorCache.set(x,y,0);
    cam.camera.unproject(vectorCache);
    var pos=cam3d.point.pos;
    rayCache.set(pos.x,pos.y,pos.z,vectorCache.x-pos.x,vectorCache.y-pos.y,vectorCache.z-pos.z);
    // rayCache.set(cam.camera.getPickRay(x,y));
    // 减少计算开销
    // planeCache.set(0,0,zPlain,0,0,zPlain+1);
    planeCache.set(0,0,zPlain+1,zPlain*(zPlain+1));
    if(Intersector.intersectRayPlane(rayCache,planeCache,intersectionCache)) {
      return intersectionCache;// 射线与平面相交，intersection变量保存了交点的坐标
    }else return null;// 射线与平面不相交
  }
  //---------------------------------------------------------------------------
  public void decal(Decal in) {
    decalBatch.add(in);
  }
  public void decalFlush(Decal in) {
    decal(in);
    flushDecal();
  }
  public void flushDecal() {
    endShape();
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // Gdx.gl.glEnable(GL20.GL_BLEND);
    // Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    decalBatch.flush();
    beginShape();
  }
  //---------------------------------------------------------------------------
  public void model(ModelInstance in) {
    modelBatch.begin(usedCamera);
    modelBatch.render(in);
    modelBatch.end();
  }
  public void modelFlush(ModelInstance in) {
    model(in);
    flushModel();
  }
  public void flushModel() {
    endShape();
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // Gdx.gl.glEnable(GL20.GL_BLEND);
    // Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    modelBatch.flush();
    beginShape();
  }
  //---------------------------------------------------------------------------
  @Override
  public void dispose() {
    super.dispose();
    decalBatch.dispose();
  }
}