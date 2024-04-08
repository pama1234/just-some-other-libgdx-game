package hhs.game.lost.games.RandomScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.game.lost.games.Constant;
import hhs.game.lost.games.GameObject;
import hhs.game.lost.games.FallGuys;
import hhs.game.lost.games.MyInputListener;
import hhs.game.lost.games.Screen.GameScreen;
import hhs.hhshaohao.mygame2.Tools.MsgConsole;
import java.util.HashMap;

import static hhs.game.lost.games.TextureTools.*;
import static com.badlogic.gdx.math.MathUtils.*;

public class TestMapScreen extends GameScreen{

  PerlinNoise map;
  CameraInputController control;

  /** 球球 */
  private GameObject my;

  private Vector3 tmp=new Vector3(0,0,0),speed=new Vector3(),camRotate=new Vector3();

  /** 游戏左侧的摇杆 */
  private Touchpad pad;

  private boolean jump=false;

  float rotate,kx,ky,radiu=20;

  HashMap<Float,Byte> hash;

  boolean showShadow=false;
  TextButton ss;

  public TestMapScreen() {

    //map = new TestMap(new Vector2(50, 50), 30, false, new Vector2(5, 5));
    //map = new RandomMap(0,0,50,50,30,3);
    map=new PerlinNoise(6,70,8);

    hash=new HashMap<>();
    camera.near=1f;
    camera.far=300f;

    ModelBuilder mb= FallGuys.res.modelBuilder;
    Material material=new Material(TextureAttribute.createDiffuse(new Texture("texture/rock.png")),TextureAttribute.createNormal(new Texture("texture/rn.png")),ColorAttribute.createDiffuse(Color.WHITE));
    //		ByteBuffer bb = ByteBuffer.allocateDirect(map.map.length * map.map[0].length * 4);
    //		int index=0;
    //		for (int i = 0; i < map.map.length; i++) {
    //			for (int j = 0; j < map.map[i].length; j++) {
    //				bb.putFloat(index, map.map[i][j]);
    //				index++;
    //			}
    //		}
    //		btHeightfieldTerrainShape shape = new btHeightfieldTerrainShape(map.map.length, map.map[0].length, bb.asFloatBuffer(), 1, 0, 1, 1, false);
    //		btRigidBody body = new btRigidBody(50, new btMotionState(){@Override public void getWorldTransform(Matrix4 m) {} @Override public void setWorldTransform(Matrix4 m) {}}, shape);
    //		body.setCollisionFlags(btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
    //		body.setActivationState(Collision.DISABLE_DEACTIVATION);
    //		world.addRigidBody(body);

    mb.begin();
    for(int i=0;i<map.map.length;i++) {
      for(int j=0;j<map.map[i].length;j++) {
        if(!hash.containsKey(map.map[i][j])) {
          mb.node().id="id"+i+j;
          mb.part("box",GL20.GL_TRIANGLES,Usage.Normal|Usage.Position|Usage.TextureCoordinates,material).box(5,map.map[i][j],5);
          hash.put(map.map[i][j],(byte)1);
          //m = mb.createBox(5, map.map[i][j], 5, material, Usage.Normal | Usage.Position | Usage.TextureCoordinates);
        }
      }
    }
    Model m=mb.end();
    MsgConsole.add(m.nodes.size+"");
    for(int i=0;i<map.map.length;i++) {
      for(int j=0;j<map.map[i].length;j++) {
        GameObject go=new GameObject(m,"id"+i+j,new btBoxShape(new Vector3(2.5f,map.map[i][j]/2,2.5f)),50);
        go.body.setCollisionFlags(go.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
        go.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        go.transform.trn(j*5-map.map.length*5*0.5f,map.map[i][j]/2,i*5-map.map.length*5*0.5f);
        go.body.proceedToTransform(go.transform);
        world.addRigidBody(go.body);
        addGameObject(go);
      }
    }

    my=new GameObject(mb.createSphere(4,4,4,10,10,new Material(TextureAttribute.createDiffuse(new Texture("texture/wood.png")),ColorAttribute.createSpecular(Color.WHITE)),Usage.Normal|Usage.Position|Usage.TextureCoordinates),new btSphereShape(2),10);
    my.transform.setToTranslation(0,80,0);
    my.body.proceedToTransform(my.transform);
    my.body.setCollisionFlags(my.body.getCollisionFlags()|btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
    addGameObject(my);

    pad=new Touchpad(10,new Touchpad.TouchpadStyle(ttd(new Texture("texture/touchpad0.png")),
      ttd(new Texture("texture/touchpad1.png"))));
    pad.setPosition(u,u);
    pad.setSize(u*2,u*2);

    stage.addActor(pad);

    TextButton bu=new TextButton("跳", FallGuys.res.textButtonStyle);
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

    ss=new TextButton("阴影渲染", FallGuys.res.textButtonStyle);
    ss.addListener(new MyInputListener() {
      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        showShadow=!showShadow;
      }
    });
    ss.setPosition(Constant.width/2-100,Constant.height-100);
    stage.addActor(ss);

    FallGuys.res.game.setScreen(this);
  }

  @Override
  public void act(float delta) {
    if(pad.isTouched()) {
      rotate+=rotate>360?-360:Gdx.input.getDeltaX(1)*.4f;
      kx=-pad.getKnobPercentX()*16;
      ky=pad.getKnobPercentY()*16;
      my.body.setLinearVelocity(speed.set(kx*cosDeg(rotate+135)-ky*sinDeg(rotate+135),my.body.getLinearVelocity().y,kx*sinDeg(rotate+135)+ky*cosDeg(rotate+135)));
    }else {
      rotate+=rotate>360?-360:Gdx.input.getDeltaX()*.4f;
    }
    camera.lookAt(my.transform.getTranslation(tmp));
    setCameraPos(camRotate.set(radiu*cosDeg(rotate)-radiu*sinDeg(rotate),20,radiu*sinDeg(rotate)+radiu*cosDeg(rotate)).add(my.transform.getTranslation(tmp)));
    camera.up.set(Vector3.Y);
    if(jump) {
      my.body.translate(Vector3.Y);
    }
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

    if(showShadow) {
      light.begin(Vector3.Zero,camera.direction);
      shadow.begin(light.getCamera());
      for(GameObject go:gobject) {
        if(isVisible(camera,go)) shadow.render(go,environment);
      }
      shadow.render(robject,environment);
      shadow.end();
      light.end();
    }else {
      Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    /*
     * debug.begin(camera); world.debugDrawWorld(); debug.end();
     */
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int p1,int p2) {
    super.resize(p1,p2);

    pad.setPosition(u,u);
    pad.setSize(u*2,u*2);
  }
}
