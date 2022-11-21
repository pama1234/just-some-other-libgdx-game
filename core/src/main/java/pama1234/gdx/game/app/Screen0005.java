package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.TouchInfo;

public class Screen0005 extends UtilScreen3D{
  Decal decal;
  Graphics tg;
  TextureRegion tr;
  DecalBatch batch;
  Array<Decal> decals=new Array<Decal>();
  PerspectiveCamera camera;
  @Override
  public void setup() {
    // decalBatch=new DecalBatch(new CameraGroupStrategy(cam.camera));
    cam.point.set(0,0,-320);
    backgroundColor(0);
    // background=false;
    textColor(255);
    tg=new Graphics(this,64,64);
    tg.beginDraw();
    fill(255,0,0);
    circle(32,32,32);
    tg.endDraw();
    // decal=Decal.newDecal(tr=new TextureRegion(tg.texture),true);
    decal=Decal.newDecal(tr=new TextureRegion(new Texture(Gdx.files.internal("sys.png"))),true);
    // decal.lookAt(cam.camera.position,cam.camera.up);
    camera=new PerspectiveCamera(45,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    camera.near=1;
    camera.far=300;
    camera.position.set(0,0,5);
    batch=new DecalBatch(new CameraGroupStrategy(cam.camera));
    TextureRegion[] textures= {new TextureRegion(new Texture(Gdx.files.internal("egg.png"))),
      new TextureRegion(new Texture(Gdx.files.internal("sys.png"))),
      new TextureRegion(new Texture(Gdx.files.internal("badlogic.png")))};
    Decal decal=Decal.newDecal(1,1,textures[1]);
    decal.setPosition(0,0,0);
    decals.add(decal);
    decal=Decal.newDecal(1,1,textures[0],true);
    decal.setPosition(0.5f,0.5f,1);
    decals.add(decal);
    decal=Decal.newDecal(1,1,textures[0],true);
    decal.setPosition(1,1,-1);
    decals.add(decal);
    decal=Decal.newDecal(1,1,textures[2]);
    decal.setPosition(1.5f,1.5f,-2);
    decals.add(decal);
    decal=Decal.newDecal(1,1,textures[1]);
    decal.setPosition(2,2,-1.5f);
    decals.add(decal);
  }
  @Override
  public void update() {}
  @Override
  public void render(float delta) {
    frameRate=delta;
    mouse.update(this);
    for(TouchInfo i:touches) i.update(this);
    inputProcessor.update();
    center.update();
    serverCenter.update();
    update();
    beginDraw();
    // if(background) background(backgroundColor);
    withCam();
    ScreenUtils.clear(Color.DARK_GRAY,true);
    camera.update();
    boolean billboard=true;
    for(int i=0;i<decals.size;i++) {
      Decal decal=decals.get(i);
      if(billboard) {
        // billboarding for ortho cam :)
        // dir.set(-camera.direction.x, -camera.direction.y, -camera.direction.z);
        // decal.setRotation(dir, Vector3.Y);
        // billboarding for perspective cam
        decal.lookAt(camera.position,camera.up);
      }
      batch.add(decal);
    }
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    batch.flush();
    // logger.log();
    endDraw();
    frameCount++;
  }
  @Override
  public void frameResized() {}
  @Override
  public void display() {}
}