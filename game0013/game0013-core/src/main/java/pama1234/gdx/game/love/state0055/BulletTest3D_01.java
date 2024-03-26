package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.OrientedEntity3D;
import pama1234.gdx.game.element.Telescope;
//import pama1234.gdx.game.element.depth.SpriteBatch3D_03;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.UtilMath;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.math.vec.Vec3f;
import pama1234.util.wrapper.Center;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BulletTest3D_01 extends StateEntity0055{
  public BulletTest3D_01(Screen0055 p) {
    super(p);
  }

  public Center<BulletTest3DModel.BulletTestEntity> testC=new Center<>();

  public Telescope telescope;

//  public static SpriteBatch3D_03 spriteBatch3D02;
  public static ShapeDrawer sd;

  @Override
  public void from(StateEntity0055 in) {

    p.doFill();
    p.noStroke();

    p.fill(ColorUtil.clas);
    p.stroke(ColorUtil.keyword);

    telescope=new Telescope(p);

    //    p.cam3d.point.des.set(0,0,-1024);

//    spriteBatch3D02=new SpriteBatch3D_03();
//    sd=new ShapeDrawer(spriteBatch3D02);
  }

  @Override
  public void update() {
    if(p.frameCount%5==0) if(testC.list.size()<500) {
      ReversedPathPoint3D e=new ReversedPathPoint3D(0,0,0,0,0,0);
      resetPoint(e);
      testC.add.add(new BulletTest3DModel.BulletTestEntity(p,e,"point"+testC.list.size()));
    }

    testC.refresh();
    testC.list.forEach(e->e.update());

    testC.list.forEach(test-> {
      if(test.point.stop) {
        resetPoint(test.point);
      }
    });
    telescope.update();
  }

  public void resetPoint(ReversedPathPoint3D test) {
    int range=300;
    test.set(
      p.random(-20,20),p.random(-20,20),p.random(-20,20),
      p.random(-range,range),p.random(-range,range),p.random(-range,range));
    test.reset();
  }

  @Override
  public void displayCam() {
    telescope.display();

    ////    Gdx.gl.glClearColor(0f,0f,0f,0f);
    //    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    //    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    //    Gdx.gl.glClearDepthf(1f);
    //    Gdx.gl.glDepthFunc(GL20.GL_LESS);

    testC.list.forEach(test-> {
      test.displayCam();
    });

  }

  @Override
  public void display() {

  }

  public static class BulletTestEntity extends OrientedEntity3D{
    public ReversedPathPoint3D point;

    public String text;

    public BulletTestEntity(Screen0055 p,ReversedPathPoint3D point,String text) {
      super(p);
      this.point=point;
      this.text=text;
    }

    public Vec3f yAixs=new Vec3f(0,1,0);
    @Override
    public void update() {
      super.update();
      point.update(0.001f);
      pose.pos.set(point.px,point.py,point.pz);
      up.set(
        (point.des.x-point.pos.x),
        (point.des.y-point.pos.y),
        (point.des.z-point.pos.z));
      up.nor();
      rotateToCam();
    }

    Vec3f tv=new Vec3f();

    @Override
    public void applyPose() {
      //      p.translate(pose.pos);
      //      p.rotate(pose.rotate);
      //      p.scale(pose.scale);
      super.applyPose();
      p.rotateZ(-UtilMath.HALF_PI);
    }

    @Override
    public void displayPose() {

      //      Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
      //      Gdx.gl.glScissor(
      //        p.cam3d.camera.position.x,
      //        p.cam3d.camera.position.y,
      //        p.cam3d.camera.viewportWidth,
      //        p.cam3d.camera.viewportHeight);

      p.rendererEnd();

      var tb=p.imageBatch;
      var ts=p.shapeDrawer;

//      p.imageBatch=spriteBatch3D02;
//      p.shapeDrawer=sd;

      p.doStroke();
      p.sstrokeWeight(2);
      p.stroke(ColorUtil.keyword);
      float lineLen=point.pos.dist(point.px,point.py,point.pz);
      p.sline(0,0,-lineLen*2,0);

      p.noStroke();

      p.fill(ColorUtil.clas);
      p.scircle(0,0,1);

      p.textColor(ColorUtil.clas);
      p.textScale(1/8f);
      p.text(getClass().getSimpleName(),lineLen*2+2,-1);

      p.fill(ColorUtil.data);

      p.pushMatrix();

      p.copyMatrix(beforeMatrix);
      p.translate(
        point.des.x,
        point.des.y,
        point.des.z);
      p.rotate(GdxMath.rotateToFace(point.des,p.cam3d.point.pos));

      p.scircle(0,0,1);

      p.textColor(ColorUtil.data);
      p.textScale(1/8f);
      p.text(text,2,-1);

      p.popMatrix();

      p.imageBatch=tb;
      p.shapeDrawer=ts;
    }
  }
}
