package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import pama1234.gdx.util.ShaderUtil;
import pama1234.gdx.util.info.TouchInfo;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreen extends UtilScreenRender{
  public Matrix4[] matrixStack=new Matrix4[10];
  public int matrixStackPointer=-1;
  //---
  public static SpriteBatch createSpriteBatch() {
    return new SpriteBatch(1000,ShaderUtil.createDefaultShader());
  }
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
  public abstract void displayWithCam();
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
  public void withScreen() {
    setCamera(screenCam);
    textScale(pus);
    strokeWeight(defaultStrokeWeight=u);
  }
  public abstract void withCam();
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