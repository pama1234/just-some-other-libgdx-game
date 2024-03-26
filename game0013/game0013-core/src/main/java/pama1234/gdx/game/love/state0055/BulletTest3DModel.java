package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.OrientedEntity3D;
import pama1234.gdx.game.element.Telescope;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.p3d.SpriteBatch3D;
import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.UtilMath;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.math.vec.Vec3f;
import pama1234.util.wrapper.Center;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BulletTest3DModel extends StateEntity0055{
  public static ShapeDrawer customShapeDrawer;
  public static SpriteBatch3D batch3d;

  public BulletTest3DModel(Screen0055 p) {
    super(p);
  }

  Center<BulletTestEntity> testC=new Center<>();

  Telescope telescope;

  @Override
  public void from(StateEntity0055 in) {

    p.doFill();
    p.noStroke();

    p.fill(ColorUtil.clas);
    p.stroke(ColorUtil.keyword);

    telescope=new Telescope(p);

    //    p.cam3d.point.des.set(0,0,-1024);
    customShapeDrawer=new ShapeDrawer(batch3d=new SpriteBatch3D(p.cam3d.camera));
  }

  @Override
  public void update() {
    if(p.frameCount%5==0) if(testC.list.size()<100) {
      ReversedPathPoint3D e=new ReversedPathPoint3D(0,0,0,0,0,0);
      resetPoint(e);
      testC.add.add(new BulletTestEntity(p,e,"point"+testC.list.size()));
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
    int range=500;
    test.set(
      p.random(-20,20),p.random(-20,20),p.random(-20,20),
      p.random(-range,range),p.random(-range,range),p.random(-range,range));
    test.reset();
  }

  @Override
  public void displayCam() {
    telescope.display();

    var tsd=p.shapeDrawer;
    var tb=p.imageBatch;
    p.shapeDrawer=customShapeDrawer;
    p.imageBatch=batch3d;
    p.font.fontBatch=batch3d;
    p.rendererEnd();

        testC.list.forEach(test-> {
          test.displayCam();
        });

//    p.fill(ColorUtil.function);
//    p.srect(-10,-10,20,20);
//
//    p.rotateX(UtilMath.HALF_PI);
//
//    p.fill(ColorUtil.data);
//    p.srect(-10,-10,20,20);

    //    p.translate(0,0,1);

    //    p.textColor(ColorUtil.function);
    //    p.textScale(1/8f);
    //    p.text("getClass().getSimpleName()",0,0);

    //    p.rotateX(UtilMath.HALF_PI);
    //
    //    p.textColor(ColorUtil.interfase);
    //    p.textScale(1/8f);
    //    p.text("p.rotateX(UtilMath.HALF_PI);",0,0);

    p.shapeDrawer=tsd;
    p.imageBatch=tb;
    p.font.fontBatch=tb;

    //    p.fill(ColorUtil.function);
    //    p.rect(-10,-30,20,20);

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
      point.update(0.02f);
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
      super.applyPose();
      p.rotateZ(-UtilMath.HALF_PI);
    }

    @Override
    public void displayPose() {

      p.doStroke();
      p.sstrokeWeight(2);
      p.stroke(ColorUtil.keyword);
      float lineLen=point.pos.dist(point.px,point.py,point.pz);
      p.translate(0,0,1/16f);
      p.sline(0,0,-lineLen*2,0);
      p.translate(0,0,-1/16f);

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
    }
  }
}
