package hhs.game.lost.games;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;

public class GameObject extends ModelInstance implements Disposable{

  public btRigidBody body;
  btMotionState state;
  private Vector3 localInertia=new Vector3();

  public final Vector3 center=new Vector3();
  public final Vector3 dimensions=new Vector3();
  public float radius;

  public final static BoundingBox bounds=new BoundingBox();

  public GameObject(Model model,btCollisionShape shape,int mass) {
    super(model);
    initBullet(shape,mass,new AllMotionState(transform));
    calculateBound();
  }
  public GameObject(Model model,String node,btCollisionShape shape,int mass) {
    super(model,node);
    initBullet(shape,mass,new AllMotionState(transform));
    calculateBound();
  }
  public GameObject(Model model,btCollisionShape shape,int mass,btMotionState state) {
    super(model);
    initBullet(shape,mass,state);
    calculateBound();
  }

  void calculateBound() {
    calculateBoundingBox(bounds);
    center.set(bounds.getCenter(Vector3.Zero));
    dimensions.set(bounds.getDimensions(Vector3.Zero));
    radius=dimensions.len()/2f;
  }
  void initBullet(btCollisionShape shape,int mass,btMotionState state) {
    shape.calculateLocalInertia(mass,localInertia);
    this.state=state;
    body=new btRigidBody(mass,state,shape,localInertia);
  }

  @Override
  public void dispose() {
    body.dispose();
    state.dispose();
  }

  public void resetState() {
    state.dispose();
    state=new MyMotionState(transform);
    body.setMotionState(state);
  }

  public static class AllMotionState extends btMotionState{
    Matrix4 trans;
    public AllMotionState(Matrix4 trans) {
      this.trans=trans;
    }

    @Override
    public void getWorldTransform(Matrix4 worldTrans) {
      worldTrans.set(trans);//TODO 搞清楚什么时候调用
    }
    @Override
    public void setWorldTransform(Matrix4 worldTrans) {
      trans.set(worldTrans);//TODO 搞清楚什么时候调用
    }
  }
  public static class MyMotionState extends btMotionState{
    Matrix4 trans;
    public MyMotionState(Matrix4 trans) {
      this.trans=trans;
    }

    @Override
    public void getWorldTransform(Matrix4 worldTrans) {
      worldTrans.set(trans);//TODO 搞清楚什么时候调用
    }
    @Override
    public void setWorldTransform(Matrix4 worldTrans) {
      trans.setToTranslation(worldTrans.getTranslation(Vector3.Zero));//TODO 搞清楚什么时候调用
    }
  }
  public enum Type{
    Position,Transform
  }
}
