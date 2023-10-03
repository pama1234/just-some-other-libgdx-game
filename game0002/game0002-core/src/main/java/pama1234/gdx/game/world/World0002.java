package pama1234.gdx.game.world;

import pama1234.Tools;
import pama1234.game.app.server.server0001.particle.with2d.CellGroup2D;
import pama1234.game.app.server.server0001.particle.with2d.CellGroupGenerator2D;
import pama1234.gdx.game.util.ControlBindUtil;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.app.UtilScreenRender;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.DisplayEntityListener;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;
import pama1234.util.function.ExecuteFunction;

public class World0002 extends Entity<UtilScreen2D> implements DisplayEntityListener{
  ControlBindUtil controlBind;
  
  public int maxParticle;
  public int particleCount;
  public CellGroup2D group;
  public boolean doUpdate;
  public Thread cellUpdate;
  // ServerPlayerCenter<Player2D> playerCenter;
  public ExecuteFunction[] plugins=new ExecuteFunction[0];
  public float[][] tesselatedMat= {
    {0,0},{1,0},
    {0,1},{1,1},
  };
  public Vec2f size;
  public World0002(UtilScreen2D p,ControlBindUtil controlBind) {
    super(p);
    this.controlBind=controlBind;
  }
  @Override
  public void init() {
    CellGroupGenerator2D gen=new CellGroupGenerator2D(0,0);
    group=gen.generateFromMiniCore(480,p.isAndroid?256:1024);
    maxParticle=UtilMath.floor(group.size*1.1f);
    size=new Vec2f(
      group.updater.w,
      group.updater.h);
    // playerCenter=new ServerPlayerCenter<Player2D>();
    cellUpdate=createUpdateThread();
    cellUpdate.start();
  }
  public Thread createUpdateThread() {
    return new Thread("CellGroupUpdateThread") {
      @Override
      public void run() {
        while(!p.stop) {
          if(doUpdate) {
            Tools.time();
            group.update();
            for(ExecuteFunction i:plugins) i.execute();
            long period=Tools.period();
            if(period<50) sleepF(50-period);
          }else sleepF(1000);
        }
      }
      public void sleepF(long in) {
        try {
          sleep(in);
        }catch(InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
  }
  @Override
  public void displayCam() {
    // System.out.println("World0002.displayCam()");
    int circleSeg=UtilScreenRender.circleSeg(CellGroup2D.SIZE*p.cam2d.scale.pos*(1/p.cam.frameScale));
    particleCount=0;
    outerLoop:
    for(int j=0;j<tesselatedMat.length;j++) {
      float[] tfa=tesselatedMat[j];
      float lx=(tfa[0]+UtilMath.floor((p.cam2d.point.pos.x)/size.x))*size.x;//TODO
      float ly=(tfa[1]+UtilMath.floor((p.cam2d.point.pos.y)/size.y))*size.y;
      for(int i=0;i<group.size;i++) {
        if(particleCount>maxParticle) break outerLoop;
        float tx=group.x(i)+lx,
          ty=group.y(i)+ly;
        if(!p.cam2d.inbox(tx,ty)) continue;
        p.fillHex(group.color(i));
        p.circle(tx,ty,CellGroup2D.SIZE*0.8f,circleSeg);
        particleCount++;
      }
    }
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.doUpdate,keyCode)) doUpdate=!doUpdate;
  }
  @Override
  public void keyReleased(char key,int keyCode) {}
}
