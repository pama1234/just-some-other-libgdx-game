package pama1234.gdx.game.love.state0055;

import static pama1234.math.UtilMath.abs;

import com.badlogic.gdx.utils.Pool;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.GameCenter;
import pama1234.gdx.game.element.Telescope;
import pama1234.gdx.game.element.bullet.BulletEntity;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.p3d.SpriteBatch3D;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.math.vec.Vec3f;
import pama1234.util.wrapper.Center;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BulletTest3DModel extends StateEntity0055{
  public static class BulletPool extends Pool<BulletEntity>{
    public Screen0055 p;
    public GameCenter pg;

    public BulletPool(Screen0055 p,GameCenter pg) {
      this.p=p;
      this.pg=pg;
    }

    @Override
    protected BulletEntity newObject() {
      return new BulletEntity(p,pg,new ReversedPathPoint3D(),"null");
    }

    @Override
    public BulletEntity obtain() {
      BulletEntity o=super.obtain();
      o.reset();
      return o;
    }
  }

  public static ShapeDrawer customShapeDrawer;
  public static SpriteBatch3D batch3d;

  public BulletTest3DModel(Screen0055 p) {
    super(p);
  }

  Center<BulletEntity> testC=new Center<>();

  Telescope telescope;

  public Vec3f bulletTarget;
  public GameCenter gameCenter;
  public BulletPool bulletPool;

  @Override
  public void from(StateEntity0055 in) {
    p.cam3d.moveSpeedMax=128;
    p.cam3d.moveSpeed=128;

    p.doFill();
    p.noStroke();

    p.fill(ColorUtil.clas);
    p.stroke(ColorUtil.keyword);

    telescope=new Telescope(p);
    telescope.min=1/32f;

    p.centerCamAddAll(telescope);

    //    p.cam3d.point.des.set(0,0,-1024);
    customShapeDrawer=new ShapeDrawer(batch3d=new SpriteBatch3D(p.cam3d.camera));

    bulletTarget=p.cam3d.point.pos;
    gameCenter=new GameCenter();
    gameCenter.camPos=p.cam3d.point.pos;

    bulletPool=new BulletPool(p,gameCenter);
  }

  @Override
  public void update() {
    if(p.frameCount%5==0) if(testC.list.size()<100) {
      ReversedPathPoint3D e=new ReversedPathPoint3D(0,0,0,0,0,0);
      resetPoint(e);
      BulletEntity bullet=new BulletEntity(p,gameCenter,e,"point"+testC.list.size());
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
    //    telescope.update();
  }

  public void reset(BulletEntity bullet) {
    bullet.contentType=BulletEntity.valuesContentType[p.randomInt(BulletEntity.valuesContentType.length)];
    bullet.noseType=BulletEntity.valuesNoseType[p.randomInt(BulletEntity.valuesNoseType.length)];
  }

  public void resetPoint(ReversedPathPoint3D test) {
    int range=500;
    //    test.set(
    //      p.random(-20,20),p.random(-20,20),p.random(-20,20),
    //      p.random(-range,range),p.random(-range,range),p.random(-range,range));
    int startRange=50;
    int shiftRange=50;
    int desRange=50+abs((p.frameCount/10)%(shiftRange*2)-shiftRange);
    //    int desRange=10;
    test.set(
      p.random(-startRange,startRange),p.random(-startRange,startRange),p.random(-startRange,startRange),
      //      bulletTarget.x,bulletTarget.y+40,bulletTarget.z);
      p.random(-desRange,desRange)-2000,p.random(-desRange,desRange),p.random(-desRange,desRange));
    //      bulletTarget.x+p.random(-10,10),bulletTarget.y+p.random(-10,10),bulletTarget.z+p.random(-10,10));
    test.reset();
  }

  @Override
  public void displayCam() {
    //    telescope.display();

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
