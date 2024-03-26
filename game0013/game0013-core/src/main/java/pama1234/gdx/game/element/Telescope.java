package pama1234.gdx.game.element;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.PathVar;

public class Telescope extends Entity<UtilScreen3D>{
  public float min=0.15f,max=1;
  public PathVar scale=new PathVar(1);
  public int fov=60;

  public Telescope(UtilScreen3D p) {
    super(p);
  }

  @Override
  public void update() {
    if(p.isKeyPressed(Keys.C)) {
      scale.des=min;
      //      System.out.println();
    }else {
      scale.des=max;
    }
    scale.update();
    p.cam3d.moveSpeed=scale.pos;
  }

  @Override
  public void display() {
    PerspectiveCamera cam=p.cam3d.pcam;
    //    cam.viewportWidth=p.width*scale.pos;
    //    cam.viewportHeight=p.height*scale.pos;
    //    cam.update();
    cam.fieldOfView=fov*scale.pos;
    cam.update();
    p.setCamera(cam);
  }
}
