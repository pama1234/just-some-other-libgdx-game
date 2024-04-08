package hhs.game.sort;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

public class CameraControl extends GestureDetector{

  private float degreesPerPixel=1;
  OrthographicCamera cam;
  Listener l;

  public CameraControl(OrthographicCamera cam) {
    super(new Listener(cam));
    this.cam=cam;
  }
  @Override
  public boolean touchDragged(int screenX,int screenY,int pointer) {
    if(!super.touchDragged(screenX,screenY,pointer)&&!Gdx.input.isTouched(1)) {
      float deltaX=-Gdx.input.getDeltaX()*degreesPerPixel*cam.zoom;
      float deltaY=Gdx.input.getDeltaY()*degreesPerPixel*cam.zoom;
      cam.position.add(deltaX,deltaY,0);
    }
    return true;
  }

  static class Listener extends GestureAdapter{
    OrthographicCamera cam;
    boolean iszoom;
    float zoom=0;
    public Listener(OrthographicCamera cam) {
      this.cam=cam;
    }
    @Override
    public boolean zoom(float initialDistance,float distance) {
      cam.zoom=zoom*initialDistance/distance;
      return false;
    }
    @Override
    public boolean touchDown(float arg0,float arg1,int arg2,int arg3) {
      zoom=cam.zoom;
      return true;
    }

  }
}
