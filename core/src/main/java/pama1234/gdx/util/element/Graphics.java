package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import pama1234.gdx.util.app.UtilScreen;

// @Deprecated
public class Graphics{
  public UtilScreen p;
  public FrameBuffer buffer;
  public OrthographicCamera cam;
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
    buffer.begin();
    p.setCamera(cam);
    p.beginDraw();
  }
  public void endDraw() {
    p.endDraw();
    buffer.end();
  }
  //TODO
  @Deprecated
  public void resize() {}
}
