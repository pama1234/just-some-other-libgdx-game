package pama1234.gdx.game.element;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

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

  Vector3 tmp=new Vector3(),tmp2=new Vector3(),dir=new Vector3(),tmp3=new Vector3();
  Quaternion rotation=new Quaternion();
  //  Vector3 up=new Vector3(0,1,0);
  public void rotateToCam() {
    var des=p.cam3d.point.pos;
    Vec4f v=GdxMath.rotateToFace(pose.pos,des,up);

    //    var des=p.cam3d.camera.position;
    //    var pos=pose.pos;
    //    //    var up=cam.up;
    //
    //    //    Decal d=null;
    //    //    d.lookAt(cam.position,cam.up);
    //
    //    tmp3.set(pos.x,pos.y,pos.z);
    //
    //    dir.set(des).sub(tmp3).nor();
    //
    //    tmp.set(up.x,up.y,up.z).crs(dir).nor();
    //    tmp2.set(dir).crs(tmp).nor();
    //
    //    rotation.setFromAxes(true,
    //      tmp.x,tmp2.x,dir.x,
    //      tmp.y,tmp2.y,dir.y,
    //      tmp.z,tmp2.z,dir.z);
    //
    //    pose.rotate.set(rotation.x,rotation.y,rotation.z,rotation.w);

    pose.rotate.set(v.x,v.y,v.z,v.w);

  }

}
