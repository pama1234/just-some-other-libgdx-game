package hhs.hhshaohao.mygame2.games.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import hhs.hhshaohao.mygame2.games.MyScreen;

public abstract class GameScreen extends MyScreen{
  public InputMultiplexer input;
  public GameScreen() {
    input=new InputMultiplexer();
  }
  @Override
  public void show() {
    Gdx.input.setInputProcessor(input);
  }

  public abstract void reStart();
  public static class CameraControlGesturer extends GestureDetector.GestureAdapter{
    OrthographicCamera cam;
    float zoom=0;
    public CameraControlGesturer(OrthographicCamera cam) {
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
    // @Override
    // public boolean zoom(float arg0, float arg1) {
    // zoom = arg0 - arg1;
    // cam.zoom += zoom * cam.zoom / 25000;
    // return super.zoom(arg0, arg1);
    // }
  }

}
