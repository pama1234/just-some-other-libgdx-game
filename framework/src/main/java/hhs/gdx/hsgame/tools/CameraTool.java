package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CameraTool{
  public static int multSpeed=50;
  public static Rectangle camRect=new Rectangle();
  public static void smoothMove(Camera cam,Vector2 pos) {
    smoothMove(cam,pos.x,pos.y);
  }
  public static void smoothMove(Camera cam,float x,float y,int multSpeed) {
    float dx,dy;
    dx=(x-cam.position.x)/Resource.width*multSpeed;
    dy=(y-cam.position.y)/Resource.height*multSpeed;
    cam.position.add(dx,dy,0);
  }
  public static void smoothMove(Camera cam,float x,float y) {
    smoothMove(cam,x,y,multSpeed);
  }
  public static Rectangle getCameraRect(OrthographicCamera cam) {
    float cw=cam.viewportWidth*cam.zoom/2;
    float ch=cam.viewportHeight*cam.zoom/2;
    return camRect.set(cam.position.x-cw,cam.position.y-ch,cw*2,ch*2);
  }
}
