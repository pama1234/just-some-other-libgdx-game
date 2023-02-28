package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.physics.PathPoint;

public abstract class Component<T extends UtilScreen>extends PointEntity<T,PathPoint>{
  public FrameBuffer buffer;
  public OrthographicCamera cam;
  // public TextureRegion texture;
  public Texture texture;
  public boolean loop=true;
  public Component(T p,float x,float y,int w,int h) {
    super(p,new PathPoint(0,0,x,y));
    buffer=new FrameBuffer(Format.RGBA4444,w,h,false);
    // texture=new TextureRegion(buffer.getColorBufferTexture());
    texture=buffer.getColorBufferTexture();
    texture.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    // texture.getTexture().setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    cam=new OrthographicCamera();
    cam.setToOrtho(true,w,h);
  }
  public abstract void draw();
  @Deprecated
  public int w() {
    return buffer.getWidth();
  }
  @Deprecated
  public int h() {
    return buffer.getHeight();
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
    p.beginShape();
  }
  public void endDraw() {
    p.endShape();
    buffer.end();
  }
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {
    buffer.dispose();
  }
  @Override
  public void update() {
    super.update();
    if(loop) refresh();
  }
  public void refresh() {
    beforeDraw();
    Camera tc=p.usedCamera;
    beginDraw();
    draw();
    p.setCamera(tc);
    endDraw();
  }
  public abstract void beforeDraw();
  @Override
  public void display() {
    p.image(texture,point.x(),point.y());
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void touchStarted(TouchInfo info) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
}
