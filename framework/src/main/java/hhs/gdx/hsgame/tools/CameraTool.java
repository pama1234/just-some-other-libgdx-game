package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

public class CameraTool{
  public static int multSpeed=50;
  public static void smoothMove(Camera cam,Vector2 pos) {
    smoothMove(cam,pos.x,pos.y);
  }
  public static void smoothMove(Camera cam,float x,float y) {
    float dx,dy;
    dx=(x-cam.position.x)/Resourse.width*multSpeed;
    dy=(y-cam.position.y)/Resourse.height*multSpeed;
    cam.position.add(dx,dy,0);
  }
}
