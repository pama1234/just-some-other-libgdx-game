package pama1234.gdx.game.app.app0001;

import pama1234.game.app.server.server0001.particle.with2d.CellGroup2D;
import pama1234.game.app.server.server0001.particle.with2d.CellGroupGenerator2D;
import pama1234.gdx.game.util.ControlBindUtil;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;

/**
 * 2D 粒子系统 警告：未维护
 */
@Deprecated
public class Screen0002 extends UtilScreen2D{
  public int maxParticle;
  public int particleCount;
  public ControlBindUtil controlBind;
  public CellGroup2D group;
  public boolean doUpdate;
  public Thread cellUpdate;
  // ServerPlayerCenter<Player2D> playerCenter;
  public float zoomSpeed=0.0625f;
  public boolean zoomIn,zoomOut;
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
    maxParticle=UtilMath.floor(group.size*1.1f);
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
    if(zoomIn!=zoomOut) {
      if(zoomIn) cam2d.scale.des+=zoomSpeed;
      else cam2d.scale.des-=zoomSpeed;
      cam2d.testScale();
    }
  }
  @Override
  public void displayWithCam() {
    int circleSeg=circleSeg(CellGroup2D.SIZE*cam2d.scale.pos*(1/cam.frameScale));
    particleCount=0;
    outerLoop:
    for(int j=0;j<tesselatedMat.length;j++) {
      float[] tfa=tesselatedMat[j];
      float lx=(tfa[0]+UtilMath.floor((cam2d.point.pos.x)/size.x))*size.x;//TODO
      float ly=(tfa[1]+UtilMath.floor((cam2d.point.pos.y)/size.y))*size.y;
      for(int i=0;i<group.size;i++) {
        if(particleCount>maxParticle) break outerLoop;
        float tx=group.x(i)+lx,
          ty=group.y(i)+ly;
        if(!cam2d.inbox(tx,ty)) continue;
        fillHex(group.color(i));
        circle(tx,ty,CellGroup2D.SIZE,circleSeg);
        particleCount++;
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
    else if(controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=true;
    else if(controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=true;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=false;
    else if(controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=false;
  }
}