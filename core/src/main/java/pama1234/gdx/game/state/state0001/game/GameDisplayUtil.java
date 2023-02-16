package pama1234.gdx.game.state.state0001.game;

import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMillisString;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter.LoopThread;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.listener.EntityListener;

public class GameDisplayUtil{
  public static float debugTextX,debugTextY,debugTextH,debugTextCountY;
  public static void debugText(Screen0011 p,Game pg) {
    World0001 tw=pg.world();
    Block tb=tw.getBlock(p.mouse.x,p.mouse.y);
    p.textColor(255,191);
    p.textScale(p.pus/2f);
    initDebugText(p);
    debugText(p,"Lighting  block="+(tb!=null?tb.light.toString():"null")+" player="+tw.yourself.light.toString());
    debugText(p,"Player    pos="+getFloatString(tw.yourself.point.pos.x,8)+" "+getFloatString(tw.yourself.point.pos.y,8)+" vel="+getFloatString(tw.yourself.point.vel.x,5)+" "+getFloatString(tw.yourself.point.vel.y,5));
    debugText(p,"---- asynchronous ----");//以下是那三个刷新线程的调试信息，格式如下之类的：“执行所消耗的时间ms 和上一次执行相距的时间差ms”
    debugText(p,"Regions         Update "+timeString(tw.regions.updateLoop));
    debugText(p,"Regions Display Update "+timeString(tw.regions.updateDisplayLoop));
    debugText(p,"FullMap Display Update "+secondTimeString(tw.regions.fullMapUpdateDisplayLoop));
    p.textScale(p.pus);
  }
  public static String secondTimeString(LoopThread loop) {
    long tm=loop.millis,tm_2=loop.stepMillis;
    return ("spent= "+getMillisString(tm,5)+"ms "+getFloatString(tm/1000f)+"s step= ")+
      (getMillisString(tm_2,5)+"ms "+getFloatString(tm_2/1000f)+"s");
  }
  public static String timeString(LoopThread loop) {
    return ("spent= "+getMillisString(loop.millis)+"ms step= ")+
      (getMillisString(loop.stepMillis)+"ms");
  }
  public static void initDebugText(Screen0011 p) {
    debugTextH=p.pu/2f;
    debugTextX=debugTextH;
    debugTextY=p.bu*1.5f+debugTextH*6;
    debugTextCountY=0;
  }
  public static void debugText(Screen0011 p,String in) {
    p.text(in,debugTextX,debugTextY+debugTextH*debugTextCountY);
    debugTextCountY+=1;
  }
  public static void drawLimitBox(Screen0011 p,int bw,int bh,MovementLimitBox limitBox) {
    int bx1=limitBox.x1,
      by1=limitBox.y1,
      bx2=limitBox.x2,
      by2=limitBox.y2;
    LivingEntity in=limitBox.p;
    int alpha=191;
    p.fill(255,127,191,alpha);
    rectStroke(p,1,limitBox.leftWall,limitBox.ceiling,limitBox.rightWall,limitBox.floor);
    p.fill(127,255,191,alpha);
    boxStroke(p,1,in.x()+in.type.dx,in.y()+in.type.dy,in.type.w,in.type.h);
    p.fill(94,203,234,alpha);
    rectStroke(p,1,bx1*bw,by1*bh,(bx2+1)*bw,(by2+1)*bh);
  }
  public static void rectStroke(Screen0011 p,float r,float tx1,float ty1,float tx2,float ty2) {
    float tw1=tx2-tx1+r*2;
    float th1=ty2-ty1+r*2;
    tx1-=r;
    ty1-=r;
    tx2+=r;
    ty2+=r;
    p.rect(tx1,ty1,r,th1);
    p.rect(tx1,ty1,tw1,r);
    p.rect(tx2-r,ty1,r,th1);
    p.rect(tx1,ty2-r,tw1,r);
  }
  public static void boxStroke(Screen0011 p,float r,float tx1,float ty1,float tw1,float th1) {
    float tx2=tx1+tw1+r;
    float ty2=ty1+th1+r;
    tx1-=r;
    ty1-=r;
    tw1+=r*2;
    th1+=r*2;
    p.rect(tx1,ty1,r,th1);
    p.rect(tx1,ty1,tw1,r);
    p.rect(tx2-r,ty1,r,th1);
    p.rect(tx1,ty2-r,tw1,r);
  }
  public static EntityListener createDebugDisplay(Screen0011 p,Game pg) {
    return new EntityListener() {
      @Override
      public void display() {
        p.beginBlend();
        World0001 tw=pg.world();
        int bw=tw.settings.blockWidth,
          bh=tw.settings.blockHeight;
        MainPlayer tp=tw.yourself;
        drawLimitBox(p,bw,bh,tp.ctrl.limitBox);
        // for(EntityCenter<Screen0011,? extends GamePointEntity<?>> i:tw.entities.list) for(GamePointEntity<?> e:i.list) drawLimitBox(tw,e,e.limitBox);
        // for(MobEntity e:tw.entities.mobEntities.list) drawLimitBox(tw,e,e.limitBox);
        for(DroppedItem e:tw.entities.items.list) drawLimitBox(p,bw,bh,e.limitBox);
        for(MobEntity e:tw.entities.mobEntities.list) if(e instanceof Fly a) drawLimitBox(p,bw,bh,a.limitBox);
        p.endBlend();
      }
    };
  }
}
