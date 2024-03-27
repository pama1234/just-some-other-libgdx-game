package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.Telescope;
import pama1234.gdx.game.element.bullet.BulletEntity;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.p3d.SpriteBatch3D;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.util.wrapper.Center;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BulletTest3DModel extends StateEntity0055{
  public static ShapeDrawer customShapeDrawer;
  public static SpriteBatch3D batch3d;

  public BulletTest3DModel(Screen0055 p) {
    super(p);
  }

  Center<BulletEntity> testC=new Center<>();

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
    if(p.frameCount%5==0) if(testC.list.size()<200) {
      ReversedPathPoint3D e=new ReversedPathPoint3D(0,0,0,0,0,0);
      resetPoint(e);
      BulletEntity bullet=new BulletEntity(p,e,"point"+testC.list.size());
      reset(bullet);
      testC.add.add(bullet);
    }

    testC.refresh();
    testC.list.forEach(e->e.update());

    testC.list.forEach(test-> {
      if(test.point.stop) {
        resetPoint(test.point);
        reset(test);
      }
    });
    telescope.update();
  }

  public void reset(BulletEntity bullet) {
    bullet.contentType=BulletEntity.valuesContentType[p.randomInt(BulletEntity.valuesContentType.length)];
    bullet.noseType=BulletEntity.valuesNoseType[p.randomInt(BulletEntity.valuesNoseType.length)];
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

    p.shapeDrawer=tsd;
    p.imageBatch=tb;
    p.font.fontBatch=tb;

  }

  @Override
  public void display() {

  }

}