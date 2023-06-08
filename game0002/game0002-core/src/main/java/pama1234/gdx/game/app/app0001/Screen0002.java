package pama1234.gdx.game.app.app0001;

import pama1234.game.app.server.server0001.particle.with2d.CellGroup2D;
import pama1234.game.app.server.server0001.particle.with2d.CellGroupGenerator2D;
import pama1234.gdx.game.util.ControlBindUtil;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.math.vec.Vec2f;

/**
 * 2D 粒子系统 警告：未维护
 */
@Deprecated
public class Screen0002 extends UtilScreen2D{
  public ControlBindUtil controlBind;
  public CellGroup2D group;
  public boolean doUpdate;
  public Thread cellUpdate;
  // ServerPlayerCenter<Player2D> playerCenter;
  public float[][] tesselatedMat= {
    {0,0},{1,0},
    {0,1},{1,1},
  };
  public Vec2f size;
  @Override
  public void setup() {
    cam.point.f=0.1f;//TODO
    cam2d.minScale=1/4f;
    backgroundColor(0);
    controlBind=new ControlBindUtil();
    CellGroupGenerator2D gen=new CellGroupGenerator2D(0,0);
    group=gen.GenerateFromMiniCore();
    size=new Vec2f(
      group.updater.w,
      group.updater.h);
    // playerCenter=new ServerPlayerCenter<Player2D>();
    noStroke();
    cellUpdate=createUpdateThread();
    cellUpdate.start();
  }
  public Thread createUpdateThread() {
    return new Thread("CellGroupUpdateThread") {
      @Override
      public void run() {
        while(!stop) {
          if(doUpdate) group.update();
          else try {
            sleep(1000);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
  }
  @Override
  public void update() {
    // group.update();
    // playerCenter.update();
  }
  @Override
  public void displayWithCam() {
    int circleSeg=circleSeg(CellGroup2D.SIZE*cam2d.scale.pos*(1/cam.frameScale));
    for(int j=0;j<tesselatedMat.length;j++) {
      float[] tfa=tesselatedMat[j];
      for(int i=0;i<group.size;i++) {
        float tx=group.x(i)+tfa[0]*size.x,
          ty=group.y(i)+tfa[1]*size.y;
        if(!cam2d.inbox(tx,ty)) continue;
        fillHex(group.color(i));
        circle(tx,ty,CellGroup2D.SIZE,circleSeg);
      }
    }
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void keyPressed(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.doUpdate,keyCode)) doUpdate=!doUpdate;
  }
}