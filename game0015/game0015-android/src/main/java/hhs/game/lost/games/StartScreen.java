package hhs.game.lost.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.math.Vector2;
import hhs.game.lost.games.Screen.TestScreen;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import hhs.game.lost.games.RandomScreen.TestMapScreen;

public class StartScreen implements Screen{

  Stage st;
  InputMultiplexer input=new InputMultiplexer();
  Vector2 lerp=new Vector2();

  ModelBatch batch;
  ArrayList<GameObject> list=new ArrayList<>();
  PerspectiveCamera cam;
  CameraInputController control;
  Environment environment;

  btDynamicsWorld world;
  MyContactListencer contact;
  private btDefaultCollisionConfiguration collisionConfig;
  private btCollisionDispatcher dispatcher;
  private btDbvtBroadphase broadphase;
  private btSequentialImpulseConstraintSolver constraintSolver;

  AssetManager manager;

  private ModelBuilder mb;
  final short g=1<<8,b=1<<9;

  private Table table;

  public StartScreen() {
    batch= FallGuys.modelBatch;

    cam=new PerspectiveCamera(67,Constant.width,Constant.height);
    cam.near=1;
    cam.far=300;
    cam.position.set(10,7,-5);
    cam.lookAt(0,0,0);

    control=new CameraInputController(cam);

    environment=new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,.4f,.4f,1));
    environment.add(new DirectionalLight().set(1f,1f,1f,40.0f,-35f,-35f));

    collisionConfig=new btDefaultCollisionConfiguration();
    dispatcher=new btCollisionDispatcher(collisionConfig);
    broadphase=new btDbvtBroadphase();
    constraintSolver=new btSequentialImpulseConstraintSolver();
    world=new btDiscreteDynamicsWorld(dispatcher,broadphase,constraintSolver,collisionConfig);//Discrete离散
    world.setGravity(new Vector3(0,-10f,0));
    contact=new MyContactListencer();

    manager= FallGuys.res.assets;
    manager.load("texture/rock.png",Texture.class);
    manager.load("texture/wood.png",Texture.class);

    StaticRes.game.setLoader(manager,new Runnable() {

      @Override
      public void run() {
        mb=new ModelBuilder();
        GameObject go=new GameObject(mb.createBox(20,1,20,
          new Material(TextureAttribute.createDiffuse(manager.get("texture/rock.png",Texture.class))),
          VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates),
          new btBoxShape(new Vector3(10,0.5f,10)),20);
        go.body.setCollisionFlags(go.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
        go.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        go.body.setContactCallbackFlag(g);
        go.body.setContactCallbackFilter(0);

        GameObject ball=new GameObject(mb.createBox(5,5,5,new Material(TextureAttribute.createDiffuse(manager.get("texture/wood.png",Texture.class)),ColorAttribute.createSpecular(Color.WHITE)),VertexAttributes.Usage.Normal|VertexAttributes.Usage.Position|VertexAttributes.Usage.TextureCoordinates),
          new btBoxShape(new Vector3(2.5f,2.5f,2.5f)),5);
        ball.transform.setFromEulerAngles(MathUtils.random(360f),MathUtils.random(360f),MathUtils.random(360f));
        ball.transform.trn(0,10,0);
        ball.body.proceedToTransform(ball.transform);
        ball.body.setCollisionFlags(ball.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
        ball.body.setContactCallbackFlag(b);
        ball.body.setContactCallbackFilter(g);
        list.add(go);
        list.add(ball);

        world.addRigidBody(go.body);
        world.addRigidBody(ball.body);
        StaticRes.game.setScreen(StartScreen.this);
      }

    });

    st=new Stage();
    input.addProcessor(st);
    input.addProcessor(control);

    table=new Table();
    TextButton s,o,n;
    s=new TextButton("开始游戏",StaticRes.textButtonStyle);
    o=new TextButton("退出",StaticRes.textButtonStyle);
    n=new TextButton("场景测试",StaticRes.textButtonStyle);

    s.addListener(new MyInputListener() {

      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        FallGuys.screens.add(new TestMapScreen());
      }

    });
    o.addListener(new MyInputListener() {

      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        Gdx.app.exit();
      }

    });
    n.addListener(new MyInputListener() {

      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        FallGuys.screens.add(new TestScreen());
      }

    });
    table.bottom().left();
    table.setFillParent(true);
    table.setPosition(Constant.width,Constant.height);
    table.add(s);
    table.add(n).padLeft(50);
    table.row().padTop(50);
    table.add(o);
    st.addActor(table);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(input);
  }

  @Override
  public void render(float p1) {

    cam.update();
    world.stepSimulation(p1,5,1/60f);

    batch.begin(cam);
    batch.render(list,environment);
    batch.end();

    st.act();
    st.draw();

    if(contact.isTouch) {
      table.setPosition(lerp.x,lerp.y);
      lerp.lerp(new Vector2(Constant.width/2-table.getMinWidth()/2,Constant.height/2-table.getMinHeight()/2),p1*2);
    }
  }

  @Override
  public void resize(int p1,int p2) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    for(GameObject g:list) {
      g.dispose();
    }
    collisionConfig.dispose();
    dispatcher.dispose();
    broadphase.dispose();
    constraintSolver.dispose();
    world.dispose();
    contact.dispose();
    manager.dispose();
  }

  class MyContactListencer extends ContactListener{

    boolean isTouch=false;

    @Override
    public boolean onContactAdded(int userValue0,int partId0,int index0,boolean match0,int userValue1,int partId1,int index1,boolean match1) {
      if(match0|match1) isTouch=true;
      return true;
    }

  }

}
