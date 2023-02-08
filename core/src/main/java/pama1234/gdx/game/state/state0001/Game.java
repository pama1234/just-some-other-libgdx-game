package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMillisString;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter.LoopThread;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldCenter;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.listener.EntityListener;

public class Game extends StateEntity0001{
  public Button<?>[] menuButtons;
  public TextButton<?>[] ctrlButtons;
  public float time;
  //---
  public World0001 world_0001;
  public WorldCenter<Screen0011,Game,World<Screen0011,Game>> worldCenter;
  public boolean debug,debugGraphics;
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
  public float debugTextX,debugTextY,debugTextH,debugTextCountY;
  public Game(Screen0011 p) {
    super(p);
    menuButtons=UiGenerator.genButtons_0005(p);
    if(p.isAndroid) ctrlButtons=UiGenerator.genButtons_0007(p,this);
    worldCenter=new WorldCenter<Screen0011,Game,World<Screen0011,Game>>(p);
    worldCenter.list.add(world_0001=new World0001(p,this));
    worldCenter.pointer=0;
    if(debug) createDebugDisplay();
  }
  public World0001 world() {
    return world_0001;
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=new EntityListener() {
      @Override
      public void display() {
        p.beginBlend();
        World0001 tw=world();
        MainPlayer tp=tw.yourself;
        drawLimitBox(tw,tp,tp.ctrl.limitBox);
        // for(EntityCenter<Screen0011,? extends GamePointEntity<?>> i:tw.entities.list) for(GamePointEntity<?> e:i.list) drawLimitBox(tw,e,e.limitBox);
        // for(MobEntity e:tw.entities.mobEntities.list) drawLimitBox(tw,e,e.limitBox);
        for(DroppedItem e:tw.entities.items.list) drawLimitBox(tw,e,e.limitBox);
        for(MobEntity e:tw.entities.mobEntities.list) if(e instanceof Fly a) drawLimitBox(tw,e,a.limitBox);
        p.endBlend();
      }
    };
  }
  public void drawLimitBox(World0001 tw,LivingEntity in,MovementLimitBox limitBox) {
    int bx1=limitBox.x1,
      by1=limitBox.y1,
      bx2=limitBox.x2,
      by2=limitBox.y2;
    int bw=tw.settings.blockWidth,
      bh=tw.settings.blockHeight;
    int ta=191;
    p.fill(255,127,191,ta);
    rectStroke(1,limitBox.leftWall,limitBox.ceiling,limitBox.rightWall,limitBox.floor);
    p.fill(127,255,191,ta);
    boxStroke(1,in.x()+in.type.dx,in.y()+in.type.dy,in.type.w,in.type.h);
    p.fill(94,203,234,ta);
    rectStroke(1,bx1*bw,by1*bh,(bx2+1)*bw,(by2+1)*bh);
  }
  public void rectStroke(float r,float tx1,float ty1,float tx2,float ty2) {
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
  public void boxStroke(float r,float tx1,float ty1,float tw1,float th1) {
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
  @Override
  public void from(State0001 in) {
    World0001 tw=world();
    p.backgroundColor(tw.sky.backgroundColor);
    MainPlayer tp=tw.yourself;
    p.cam.point.des.set(tp.cx(),tp.cy(),0);
    p.cam.point.pos.set(p.cam.point.des);
    // p.cam2d.activeDrag=false;
    for(Button<?> e:menuButtons) p.centerScreen.add.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.add.add(e);
    if(firstInit) {
      firstInit=false;
      tw.init();
    }
    tw.from(in);//TODO
    worldCenter.resume();
    if(debugGraphics) p.centerCam.add.add(displayCamTop);
    p.centerCam.add.add(worldCenter);
  }
  @Override
  public void to(State0001 in) {
    // p.cam2d.activeDrag=true;
    for(Button<?> e:menuButtons) p.centerScreen.remove.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.remove.add(e);
    p.centerCam.remove.add(worldCenter);
    worldCenter.pause();
    world().to(in);//TODO
    if(debugGraphics) p.centerCam.remove.add(displayCamTop);
  }
  @Override
  public void displayCam() {
    worldCenter.displayCam();
  }
  @Override
  public void display() {
    if(debugGraphics) {
      p.beginBlend();
      p.fill(94,203,234,127);
      RectF[] cullRects=world().yourself.ctrl.cullRects;
      for(RectF e:cullRects) p.rect(e.x(),e.y(),e.w(),e.h());
      p.endBlend();
    }
    if(debug) {
      World0001 tw=world();
      Block tb=tw.getBlock(p.mouse.x,p.mouse.y);
      p.textColor(255,191);
      p.textScale(p.pus/2f);
      initDebugText();
      debugText("Lighting  block="+(tb!=null?tb.light.toString():"null")+" player="+tw.yourself.light.toString());
      debugText("Player    pos="+getFloatString(tw.yourself.point.pos.x,8)+" "+getFloatString(tw.yourself.point.pos.y,8)+" vel="+getFloatString(tw.yourself.point.vel.x,5)+" "+getFloatString(tw.yourself.point.vel.y,5));
      debugText("---- asynchronous ----");//以下是那三个刷新线程的调试信息，格式如下之类的：“执行所消耗的时间ms 和上一次执行相距的时间差ms”
      debugText("Regions         Update "+timeString(tw.regions.updateLoop));
      debugText("Regions Display Update "+timeString(tw.regions.updateDisplayLoop));
      debugText("FullMap Display Update "+secondTimeString(tw.regions.fullMapUpdateDisplayLoop));
      p.textScale(p.pus);
    }
  }
  public String secondTimeString(LoopThread loop) {
    long tm=loop.millis,tm_2=loop.stepMillis;
    return ("spent= "+getMillisString(tm,5)+"ms "+getFloatString(tm/1000f)+"s step= ")+
      (getMillisString(tm_2,5)+"ms "+getFloatString(tm_2/1000f)+"s");
  }
  public String timeString(LoopThread loop) {
    return ("spent= "+getMillisString(loop.millis)+"ms step= ")+
      (getMillisString(loop.stepMillis)+"ms");
  }
  public void initDebugText() {
    debugTextH=p.pu/2f;
    debugTextX=debugTextH;
    debugTextY=p.bu*1.5f+debugTextH*6;
    debugTextCountY=0;
  }
  public void debugText(String in) {
    p.text(in,debugTextX,debugTextY+debugTextH*debugTextCountY);
    debugTextCountY+=1;
  }
  @Override
  public void update() {
    time+=p.frameRate;
  }
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
  @Override
  public void dispose() {
    // world().dispose();
    for(World<?,?> e:worldCenter.list) e.dispose();
  }
}