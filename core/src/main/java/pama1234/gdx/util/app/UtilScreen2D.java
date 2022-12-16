package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;

import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.util.wrapper.ServerEntityCenter;

public abstract class UtilScreen2D extends UtilScreen{
  public CameraController2D cam2d;//TODO do we need this?
  @Override
  public void show() {
    screenCam=new OrthographicCamera();
    imageBatch=createSpriteBatch();
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
    rFill=new ShapeRenderer();
    rStroke=new ShapeRenderer();
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
  public Vector3 unproject(float x,float y) {
    vectorCache.set(x,y,0);
    cam.camera.unproject(vectorCache);
    return vectorCache;
  }
}
