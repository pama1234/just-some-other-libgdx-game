package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.ShaderUtil;
import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.element.UtilPolygonSpriteBatch;
import pama1234.gdx.util.element.UtilShapeRenderer;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.util.wrapper.ServerEntityCenter;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreen extends UtilScreenRender{
  public static SpriteBatch createSpriteBatch() {
    // if(Gdx.app.getType()!=ApplicationType.HeadlessDesktop) 
    return new SpriteBatch(1000,ShaderUtil.createDefaultShader());
    // else return new SpriteBatch(1000,new ShaderProgram("",""));
  }
  public void createRenderUtil() {
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
    pFill=new UtilPolygonSpriteBatch();
    pFill.setColor(fillColor);
  }
  public void createInputUtil() {
    vectorCache=new Vector3();
    mouse=new MouseInfo(this);
    for(int i=0;i<touches.length;i++) touches[i]=new TouchInfo(this);
    keyPressedArray=new IntArray(false,12);
    backgroundColor=new Color(1,1,1,0);
  }
  //---------------------------------------------------------------------------
  public void preInit() {
    // if(Gdx.app.getType()==ApplicationType.HeadlessDesktop) Gdx.gl20=Gdx.gl=new EmptyGL20();
    screenCam=new OrthographicCamera();
    // if(Gdx.app.getType()!=ApplicationType.HeadlessDesktop) {
    imageBatch=createSpriteBatch();
    // tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    // }else {
    //   imageBatch=new SpriteBatch();
    //   // tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    // }
    tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    Gdx.input.setInputProcessor(inputProcessor=new UtilInputProcesser(this));
    serverCenter=new ServerEntityCenter<>();
    center=new EntityCenter<>(this);
    center.list.add(cam=createCamera());
    center.list.add(centerCam=new EntityCenter<>(this));
    center.list.add(centerScreen=new EntityCenter<>(this));
    //---
    screenStage=new Stage(screenViewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    camStage=new Stage(camViewport=new ScalingViewport(Scaling.fit,width,height,cam.camera),imageBatch);
    inputProcessor.sub.add.add(screenStage);
    inputProcessor.sub.add.add(camStage);
    center.list.add(new EntityListener() {
      @Override
      public void update() {
        screenStage.act();
        camStage.act();
      }
      @Override
      public void mousePressed(MouseInfo info) {
        screenStage.setKeyboardFocus(null);
        camStage.setKeyboardFocus(null);
      }
      @Override
      public void frameResized(int w,int h) {
        bu=pus*24;
        screenViewport.setWorldSize(width,height);
        screenViewport.update(width,height);
        camViewport.setWorldSize(width,height);
        camViewport.update(width,height);
      }
    });
    centerScreen.list.add(new EntityListener() {
      @Override
      public void display() {
        screenStage.draw();
      }
    });
    centerCam.list.add(new EntityListener() {
      @Override
      public void display() {
        camStage.draw();
      }
    });
  }
  public void postInit() {
    createRenderUtil();
    createInputUtil();
    withCam();
    innerResize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
  }
  public abstract CameraController createCamera();
  //---------------------------------------------------------------------------
  @Override
  public void render(float delta) {
    frameRate=delta;
    // textScale(pus);
    doUpdate();
    doDraw();
    frameCount++;
  }
  public void doUpdate() {
    mouse.update(this);
    for(TouchInfo i:touches) i.update(this);
    inputProcessor.update();
    center.update();
    serverCenter.update();
    update();
  }
  public void doDraw() {
    beginShape();
    if(background) background(backgroundColor);
    withCam();
    serverCenter.display();
    centerCam.display();
    displayWithCam();
    withScreen();
    centerScreen.display();
    display();
    inputProcessor.display();
    endShape();
  }
  //---------------------------------------------------------------------------
  public void beginBlend() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    // Gdx.gl.glBlendFuncSeparate(
    //   GL20.GL_SRC_ALPHA,
    //   GL20.GL_ONE_MINUS_SRC_ALPHA,
    //   GL20.GL_SRC_ALPHA,
    //   GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  public void endBlend() {
    Gdx.gl.glDisable(GL20.GL_BLEND);
  }
  //---------------------------------------------------------------------------
  public void withScreen() {
    setCamera(screenCam);
    textScale(pus);
    strokeWeight(defaultStrokeWeight=u);
  }
  public abstract void withCam();
}