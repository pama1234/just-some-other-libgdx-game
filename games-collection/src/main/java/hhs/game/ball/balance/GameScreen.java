package hhs.game.ball.balance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hhs.game.lost.games.Constant;
import hhs.game.lost.games.GameObject;
import hhs.game.ball.balance.FallGuys;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import pama1234.math.UtilMath;

public abstract class GameScreen implements Screen{

  protected ArrayList<GameObject> gobject=new ArrayList<>();
  protected ArrayList<ModelInstance> robject=new ArrayList<>();

  protected Stage stage;
  protected InputMultiplexer inputs;

  protected ModelBatch batch=FallGuys.modelBatch,shadow=new ModelBatch(new DepthShaderProvider());
  protected DirectionalShadowLight light;
  protected PerspectiveCamera camera;
  protected Environment environment;

  protected btDynamicsWorld world;
  private btDefaultCollisionConfiguration collisionConfig;
  private btCollisionDispatcher dispatcher;
  private btDbvtBroadphase broadphase;
  private btSequentialImpulseConstraintSolver constraintSolver;

  protected Vector3 tmpVector=new Vector3();
  protected BoundingBox tmpBox;

  protected DebugDrawer debug;

  private Vector3 tempPosition=new Vector3(0,0,0);

  Shader shader;

  int timeSub=10,sub=0;

  /** 尺寸单元（unit），用于在不同大小的页面上保持UI等游戏物体被绘制时的大小一致 */
  public float u;

  public GameScreen() {
    inputs=new InputMultiplexer();
    stage=new Stage();

    inputs.addProcessor(stage);

    camera=new PerspectiveCamera(67,Constant.width,Constant.height);
    camera.near=.5f;
    camera.far=1000f;
    camera.position.set(0,20,20);
    camera.lookAt(0,0,0);
    camera.normalizeUp();
    camera.update();

    light=new DirectionalShadowLight(4096,4096,160,160,.1f,300f);
    environment=new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight,.4f,.4f,.4f,1f));
    environment.add(light.set(1f,1f,1f,40.0f,-35f,-35f));
    environment.shadowMap=light;

    FallGuys.clearColor.set(.4f,.4f,.4f,1f);
    collisionConfig=new btDefaultCollisionConfiguration();
    dispatcher=new btCollisionDispatcher(collisionConfig);
    broadphase=new btDbvtBroadphase();
    constraintSolver=new btSequentialImpulseConstraintSolver();
    world=new btDiscreteDynamicsWorld(dispatcher,broadphase,constraintSolver,collisionConfig);//Discrete离散
    world.setGravity(new Vector3(0,-10f,0));

    debug=new DebugDrawer();
    debug.setDebugMode(DebugDrawer.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
    debug.setSpriteBatch(FallGuys.batch);
    world.setDebugDrawer(debug);

  }

  public void addGameObject(GameObject go) {
    gobject.add(go);
    world.addRigidBody(go.body);
  }

  public void setCameraPos(float x,float y,float z) {
    camera.position.add((x-camera.position.x)/16,(y-camera.position.y)/8,(z-camera.position.z)/16);
  }
  public void setCameraPos(Vector3 pos) {
    setCameraPos(pos.x,pos.y,pos.z);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputs);
  }

  public abstract void act(float delta);

  protected boolean isVisible(final Camera cam,final GameObject instance) {
    instance.transform.getTranslation(tempPosition);
    tempPosition.add(instance.center);
    return cam.frustum.sphereInFrustum(tempPosition,instance.radius);
  }

  @Override
  public void render(float p1) {
    act(p1);
    world.stepSimulation(Gdx.graphics.getDeltaTime(),2,1/60f);

    camera.update();

    batch.begin(camera);
    for(GameObject go:gobject) {
      if(isVisible(camera,go)) batch.render(go,environment);
    }
    batch.render(robject,environment);
    batch.end();

    light.begin(Vector3.Zero,camera.direction);
    shadow.begin(light.getCamera());
    for(GameObject go:gobject) {
      if(isVisible(camera,go)) shadow.render(go,environment);
    }
    shadow.render(robject,environment);
    shadow.end();
    light.end();
    /*
     * debug.begin(camera); world.debugDrawWorld(); debug.end();
     */
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int p1,int p2) {
    u=UtilMath.min(p1,p2)/8f;
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    for(GameObject go:gobject) {
      go.dispose();
    }

    collisionConfig.dispose();
    dispatcher.dispose();
    broadphase.dispose();
    constraintSolver.dispose();
    world.dispose();
  }

}
