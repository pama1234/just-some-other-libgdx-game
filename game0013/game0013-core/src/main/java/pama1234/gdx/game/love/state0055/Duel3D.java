package pama1234.gdx.game.love.state0055;

import static pama1234.math.UtilMath.abs;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.GameCenter;
import pama1234.gdx.game.element.GameScript;
import pama1234.gdx.game.element.Telescope;
import pama1234.gdx.game.element.bullet.BulletEntity;
import pama1234.gdx.game.element.bullet.BulletPool;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.p3d.SpriteBatch3D;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.math.vec.Vec3f;
import pama1234.util.wrapper.Center;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Duel3D extends StateEntity0055{


  public Duel3D(Screen0055 p) {
    super(p);
  }

  Center<BulletEntity> testC=new Center<>();

  Telescope telescope;

  public Vec3f bulletTarget;
  public GameCenter gameCenter;
  public GameScript gameScript;
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

    bulletTarget=p.cam3d.point.pos;
    gameCenter=new GameCenter();
    gameCenter.camPos=p.cam3d.point.pos;

    bulletPool=new BulletPool(p,gameCenter);

    gameScript=new GameScript(p);
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
  }

  public void reset(BulletEntity bullet) {
    bullet.contentType=BulletEntity.valuesContentType[p.randomInt(BulletEntity.valuesContentType.length)];
    bullet.noseType=BulletEntity.valuesNoseType[p.randomInt(BulletEntity.valuesNoseType.length)];
  }

  public void resetPoint(ReversedPathPoint3D test) {
    int range=500;
    int startRange=50;
    int shiftRange=50;
    int desRange=50+abs((p.frameCount/10)%(shiftRange*2)-shiftRange);
    test.set(
      p.random(-startRange,startRange),p.random(-startRange,startRange),p.random(-startRange,startRange),
      p.random(-desRange,desRange)-2000,p.random(-desRange,desRange),p.random(-desRange,desRange));
    test.reset();
  }

  @Override
  public void displayCam() {
    p.depth(true);

    testC.list.forEach(test-> {
      test.displayCam();
    });

    p.depth(false);
  }

  @Override
  public void display() {

  }

}
