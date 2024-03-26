package pama1234.gdx.game.element;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Matrix4;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.util.entity.EntityNeo;
import pama1234.math.transform.Pose3D;
import pama1234.math.vec.Vec3f;

public abstract class Entity3D extends EntityNeo<Screen0055> implements RenderableProvider{
  public Matrix4 beforeMatrix;
  public Entity3D(Screen0055 p) {
    super(p);
  }

  public Pose3D pose=new Pose3D(0,0,0);

  @Override
  public void displayCam() {
    beforeMatrix=p.matrix();
    p.pushMatrix();

    //    beforePose();

    applyPose();

    displayPose();

    p.popMatrix();
  }

  public void applyPose() {
    p.pose(pose);
  }

  //  public void beforePose() {}

  public abstract void displayPose();

  public Vec3f getPosition() {
    return pose.pos;
  }

  public boolean isOpaque() {
    return false;
  }

  @Override
  public void getRenderables(Array<Renderable> renderables,Pool<Renderable> pool) {
//    var r=pool.obtain();
//    renderables.add(r);
  }
}
