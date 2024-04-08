package hhs.game.ball.balance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.game.lost.games.Constant;
import hhs.game.lost.games.GameObject;
import hhs.game.lost.games.MyInputListener;

import static hhs.game.lost.games.TextureTools.*;
import static com.badlogic.gdx.math.MathUtils.*;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;

public class TestScreen extends GameScreen{

  AssetManager asset;
  CameraInputController cam;
  ModelInstance sky;
  private GameObject my;
  DebugDrawer draw;
  Vector3 tmp=new Vector3();

  private ModelBuilder mb;
  private Touchpad pad;

  boolean jump=false;

  Vector3 speed=new Vector3(0,0,0),camRotate;

  float rotate=0,yr=0;
  float radiu=40,kx=0,ky=0;

  private DirectionalShadowLight sl;

  public TestScreen() {
    //environment.add(new PointLight().set(Color.WHITE,0,50,0,1000));

    mb=FallGuys.res.modelBuilder;

    Model m=mb.createBox(40,1,40,
      new Material(TextureAttribute.createDiffuse(FallGuys.res.assets.get("texture/rock.png",Texture.class))),
      Usage.Normal|Usage.Position|Usage.TextureCoordinates);
    GameObject tmp=new GameObject(m,new btBoxShape(new Vector3(20,.5f,20)),500);
    tmp.body.setCollisionFlags(btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
    tmp.body.setActivationState(Collision.DISABLE_DEACTIVATION);
    addGameObject(tmp);

    asset=new AssetManager();
    asset.load("model/spacesphere.obj",Model.class);
    asset.load("model/ground.obj",Model.class);
    asset.load("texture/touchpad0.png",Texture.class);
    asset.load("texture/touchpad1.png",Texture.class);
    asset.load("texture/wood.png",Texture.class);
    asset.load("texture/rn.png",Texture.class);
    asset.load("texture/wn.png",Texture.class);
    FallGuys.clearColor.set(.4f,.4f,.4f,1);

    FallGuys.res.game.setLoader(asset,new Runnable() {

      @Override
      public void run() {
        sky=new ModelInstance(asset.get("model/spacesphere.obj",Model.class));
        sky.transform.scale(5,5,5);

        my=new GameObject(mb.createSphere(4,4,4,10,10,new Material(TextureAttribute.createDiffuse(asset.get("texture/wood.png",Texture.class)),ColorAttribute.createSpecular(Color.WHITE),TextureAttribute.createNormal(asset.get("texture/wn.png",Texture.class))),Usage.Normal|Usage.Position|Usage.TextureCoordinates),new btSphereShape(2),10);
        my.transform.setToTranslation(0,20,0);
        my.body.proceedToTransform(my.transform);
        my.body.setCollisionFlags(my.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
        addGameObject(my);

        Model ground=asset.get("model/ground.obj",Model.class);
        for(Mesh n:ground.meshes) {
          n.scale(30,30,30);
        }
        ground.calculateTransforms();
        GameObject g=new GameObject(ground,Bullet.obtainStaticNodeShape(ground.nodes),500);
        g.body.setCollisionFlags(g.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
        g.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        g.transform.trn(5,5,5);
        g.body.proceedToTransform(g.transform);
        for(Material m:g.materials) m.set((new Material(TextureAttribute.createDiffuse(asset.get("texture/wood.png",Texture.class)),TextureAttribute.createNormal(asset.get("texture/wn.png",Texture.class)),ColorAttribute.createSpecular(Color.WHITE))));
        addGameObject(g);

        camRotate=new Vector3(0,0,0);

        robject.add(sky);

        pad=new Touchpad(10,new Touchpad.TouchpadStyle(ttd(asset.get("texture/touchpad0.png",Texture.class)),
          ttd(asset.get("texture/touchpad1.png",Texture.class))));
        pad.setPosition(100,100);
        pad.setSize(200,200);

        stage.addActor(pad);

        TextButton bu=new TextButton("跳",FallGuys.res.textButtonStyle);
        bu.addListener(new MyInputListener() {

          @Override
          public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
            jump=true;
            return super.touchDown(event,x,y,pointer,button);
          }

          @Override
          public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
            jump=false;
          }

        });
        bu.setPosition(Constant.width-Constant.width/10,200);
        stage.addActor(bu);

        FallGuys.res.game.setScreen(TestScreen.this);
      }
    });
  }

  @Override
  public void act(float delta) {
    camera.up.set(Vector3.Y);
    camera.lookAt(my.transform.getTranslation(tmp));
    setCameraPos(camRotate.set(radiu*cosDeg(rotate)-radiu*sinDeg(rotate),20,radiu*sinDeg(rotate)+radiu*cosDeg(rotate)).add(my.transform.getTranslation(tmp)));
    if(pad.isTouched()) {
      rotate+=rotate>360?-360:Gdx.input.getDeltaX(1)*.4f;
      kx=-pad.getKnobPercentX()*16;
      ky=pad.getKnobPercentY()*16;
      my.body.setLinearVelocity(speed.set(kx*cosDeg(rotate+135)-ky*sinDeg(rotate+135),my.body.getLinearVelocity().y,kx*sinDeg(rotate+135)+ky*cosDeg(rotate+135)));
    }else {
      rotate+=rotate>360?-360:Gdx.input.getDeltaX()*.4f;
    }
    if(jump) {
      my.body.translate(Vector3.Y);
    }
  }

}
