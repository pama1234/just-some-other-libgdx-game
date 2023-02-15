package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import pama1234.gdx.util.app.UtilScreen;

public class Graphics{
  public UtilScreen p;
  public FrameBuffer buffer;
  public OrthographicCamera cam;
  public Camera tempCam;
  public Texture texture;
  public Graphics(UtilScreen p,int w,int h) {
    this.p=p;
    buffer=new FrameBuffer(Format.RGBA4444,w,h,false);
    texture=buffer.getColorBufferTexture();
    texture.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    cam=new OrthographicCamera();
    cam.setToOrtho(true,w,h);
  }
  public float width() {
    return texture.getWidth();
  }
  public float height() {
    return texture.getHeight();
  }
  public void beginDraw() {
    begin();
    p.beginDraw();
  }
  public void endDraw() {
    p.endDraw();
    end();
  }
  public void begin() {
    buffer.begin();
    tempCam=p.usedCamera;
    p.setCamera(cam);
  }
  public void end() {
    buffer.end();
    p.setCamera(tempCam);
    tempCam=null;
  }
  //TODO
  @Deprecated
  public void resize() {}
  public void dispose() {
    buffer.dispose();
    texture.dispose();
  }
}
