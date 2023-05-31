package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.ShaderUtil;
import pama1234.gdx.util.element.CameraController;
import pama1234.gdx.util.element.UtilShapeRenderer;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.util.wrapper.ServerEntityCenter;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreen extends UtilScreenRender{
  public Matrix4[] matrixStack=new Matrix4[10];
  public int matrixStackPointer=-1;
  //---------------------------------------------------------------------------
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
    pFill=new PolygonSpriteBatch();
    pFill.setColor(fillColor);
  }
  public void createInputUtil() {
    vectorCache=new Vector3();
    mouse=new MouseInfo(this);
    for(int i=0;i<touches.length;i++) touches[i]=new TouchInfo(this);
    keyPressedArray=new IntArray(false,12);
    backgroundColor=new Color(1,1,1,0);
    serverCenter=new ServerEntityCenter<>();
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
    center=new EntityCenter<>(this);
    center.list.add(cam=createCamera());
    center.list.add(centerCam=new EntityCenter<>(this));
    center.list.add(centerScreen=new EntityCenter<>(this));
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
}