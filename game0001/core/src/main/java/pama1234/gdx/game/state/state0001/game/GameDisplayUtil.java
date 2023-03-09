package pama1234.gdx.game.state.state0001.game;

import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMillisString;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.region.LoopThread;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.math.UtilMath;

public class GameDisplayUtil{
  public static float debugTextX,debugTextY,debugTextH,debugTextCountY;
  public static void debugText(Screen0011 p,Game pg) {
    WorldBase2D<?> tw=pg.world();
    Block tb=tw.getBlock(p.mouse.x,p.mouse.y);
    p.textColor(255,191);
    p.textScale(UtilMath.max((int)(p.pus/3f),1));
    // p.textScale(UtilMath.ceil(p.pus/3f));
    initDebugText(p);
    debugText(p,"Lighting  block="+(tb!=null?tb.light.toString():"null")+" player="+tw.yourself.light.toString());
    debugText(p,"Player    pos="+getFloatString(tw.yourself.point.pos.x,8)+" "+getFloatString(tw.yourself.point.pos.y,8)+" vel="+getFloatString(tw.yourself.point.vel.x,5)+" "+getFloatString(tw.yourself.point.vel.y,5));
    debugText(p,"Null Block Reference "+getMillisString(tw.metaBlocks.nullBlock.count,6));
    debugText(p,"---- asynchronous ----");//以下是那三个刷新线程的调试信息，格式如下之类的：“执行所消耗的时间ms 和上一次执行相距的时间差ms”
    debugText(p,"Regions          Update "+timeString(tw.regions.updateLoop));
    debugText(p,"Regions  Display Update "+timeString(tw.regions.updateDisplayLoop));
    debugText(p,"Priority Display Update "+secondTimeString(tw.regions.priorityUpdateDisplayLoop));
    debugTextCountY+=1;
    // Settings.drawLogText(p,p.logText,debugTextX,debugTextY);
    if(p.settings.showLog) {
      p.font.setColor(p.textColor);
      p.font.getData().setScale(p.font.scale);
      p.drawText(p.logText,debugTextX,debugTextY+debugTextH*debugTextCountY);
      p.font.getData().setScale(1);
    }
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
    // float ts=p.pus*UtilMath.max((int)(p.font.defaultSize/2f),1);
    float ts=p.debugTextH;
    debugTextCountY=1;
    debugTextH=p.font.scale*p.font.defaultSize;
    debugTextX=ts;
    debugTextY=p.debugTextY+ts*p.debugTextCountY;
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
        WorldBase2D<?> tw=pg.world();
        int bw=tw.settings.blockWidth,
          bh=tw.settings.blockHeight;
        MainPlayer tp=tw.yourself;
        drawLimitBox(p,bw,bh,tp.ctrl.limitBox);
        for(DroppedItem e:tw.entities.items.list) drawLimitBox(p,bw,bh,e.limitBox);
        for(MobEntity e:tw.entities.mobEntities.list) drawLimitBox(p,bw,bh,e.limitBox);
        p.endBlend();
      }
    };
  }
}
