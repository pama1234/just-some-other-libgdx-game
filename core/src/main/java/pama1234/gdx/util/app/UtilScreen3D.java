package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;

import pama1234.gdx.util.element.CameraController3D;
import pama1234.gdx.util.element.MultiChunkFont;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.ServerEntityCenter;

public abstract class UtilScreen3D extends UtilScreen{
  public CameraController3D cam3d;
  public DecalBatch decalBatch;
  // public CameraGroupStrategy cameraGroupStrategy;
  @Override
  public void show() {
    init();
    screenCam=new OrthographicCamera();
    cam=cam3d=new CameraController3D(this,0,0,0,1,0,Gdx.app.getType()==ApplicationType.Desktop?640:160);
    fontBatch=new SpriteBatch();
    imageBatch=new SpriteBatch();
    font=new MultiChunkFont(new FileHandle[] {
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
    font.fontBatch=fontBatch;
    textColor=new Color(0,0,0,1);
    font.color(textColor);
    // genFont();
    fillColor=new Color(1,1,1,1);
    strokeColor=new Color(0,0,0,1);
    rFill=new ShapeRenderer();
    rStroke=new ShapeRenderer();
    rFill.setColor(fillColor);
    rStroke.setColor(strokeColor);
    decalBatch=new DecalBatch(new CameraGroupStrategy(cam.camera));//TODO
    vectorCache=new Vector3();
    mouse=new MouseInfo(this);
    for(int i=0;i<touches.length;i++) touches[i]=new TouchInfo(this);
    keyPressedArray=new IntArray(false,12);
    Gdx.input.setInputProcessor(inputProcessor=new UtilInputProcesser(this));
    backgroundColor=new Color(1,1,1,0);
    center=new EntityCenter<>(this);
    center.list.add(cam);
    serverCenter=new ServerEntityCenter<>();
    withCam();
    setup();
  }
  //TODO this do nothing... strange
  public Vector3 unproject(float x,float y) {
    vectorCache.set(x,y,0);
    return vectorCache;
  }
  public void decal(Decal in) {
    decalBatch.add(in);
    // System.out.println(in);
  }
  public void decalFlush(Decal in) {
    decal(in);
    flushDecal();
  }
  public void flushDecal() {
    endDraw();
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // Gdx.gl.glEnable(GL20.GL_BLEND);
    // Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    decalBatch.flush();
    beginDraw();
  }
  @Override
  public void dispose() {
    super.dispose();
    decalBatch.dispose();
  }
}