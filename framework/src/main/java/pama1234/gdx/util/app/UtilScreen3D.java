package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.element.CameraController;
import pama1234.gdx.util.element.CameraController3D;

public abstract class UtilScreen3D extends UtilScreen{
  public CameraController3D cam3d;
  public DecalBatch decalBatch;
  public ModelBatch modelBatch;
  // public CameraGroupStrategy cameraGroupStrategy;
  @Override
  public void show() {
    preInit();
    init();
    postInit();
    setup();
  }
  @Override
  public void createRenderUtil() {
    super.createRenderUtil();
    decalBatch=new DecalBatch(new CameraGroupStrategy(cam.camera));//TODO
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
  //TODO this do nothing
  public Vector3 unproject(float x,float y) {
    vectorCache.set(x,y,0);
    return vectorCache;
  }
  //---------------------------------------------------------------------------
  public void decal(Decal in) {
    decalBatch.add(in);
    // System.out.println(in);
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