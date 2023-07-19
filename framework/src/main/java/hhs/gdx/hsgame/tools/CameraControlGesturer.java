package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;

public class CameraControlGesturer extends GestureDetector.GestureAdapter{
  OrthographicCamera cam;
  float zoom=0;
  public CameraControlGesturer(OrthographicCamera cam) {
    this.cam=cam;
  }
  @Override
  public boolean zoom(float initialDistance,float distance) {
    zoom=initialDistance/distance*cam.zoom;
    cam.zoom=MathUtils.lerp(cam.zoom,zoom,0.05f);
    return false;
  }
  //  @Override
  //  public boolean zoom(float arg0, float arg1) {
  //    zoom = arg0 - arg1;
  //    cam.zoom += zoom * cam.zoom / 25000;
  //    return super.zoom(arg0, arg1);
  //  }
}
