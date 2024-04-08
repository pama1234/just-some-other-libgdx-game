package pama1234.gdx.game.element;

import com.badlogic.gdx.graphics.Camera;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

public abstract class OrientedEntity3D extends Entity3D{
  public Camera cam;
  public Vec3f up=new Vec3f(0,1,0);

  public OrientedEntity3D(Screen0055 p) {
    super(p);
    cam=p.cam3d.camera;
  }

  public void rotateToCam() {
    var des=p.cam3d.point.pos;
    Vec4f v=GdxMath.rotateToFace(pose.pos,des,up);

    pose.rotate.set(v.x,v.y,v.z,v.w);
  }
}
