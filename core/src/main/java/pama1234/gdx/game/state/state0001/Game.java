package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldCenter;
import pama1234.gdx.game.ui.generator.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class Game extends StateEntity0001{
  public Button<?>[] menuButtons;
  public TextButton<?>[] ctrlButtons;
  public float time;
  //---
  public World0001 world;
  public WorldCenter<Screen0011,Game,World<Screen0011,Game>> worldCenter;
  public boolean debug,debugGraphics;
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
  public float debugTextX,debugTextY,debugTextH,debugTextCountY;
  public Game(Screen0011 p) {
    super(p);
    menuButtons=ButtonGenerator.genButtons_0005(p);
    if(p.isAndroid) ctrlButtons=ButtonGenerator.genButtons_0007(p,this);
    worldCenter=new WorldCenter<Screen0011,Game,World<Screen0011,Game>>(p);
    worldCenter.list.add(world=new World0001(p,this));
    worldCenter.pointer=0;
    if(debug) createDebugDisplay();
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=new EntityListener() {
      @Override
      public void display() {
        p.beginBlend();
        drawLimitBox(world.yourself,world.yourself.ctrl.limitBox);
        for(DroppedItem e:world.entities.items.list) drawLimitBox(e,e.limitBox);
        p.endBlend();
      }
    };
  }
  public void drawLimitBox(LivingEntity in,MovementLimitBox limitBox) {
    int bx1=limitBox.x1,
      by1=limitBox.y1,
      bx2=limitBox.x2,
      by2=limitBox.y2;
    int bw=world.blockWidth,
      bh=world.blockHeight;
    int ta=191;
    p.fill(255,127,191,ta);
    // p.rect(limitBox.leftWall,limitBox.ceiling,limitBox.rightWall-limitBox.leftWall,limitBox.floor-limitBox.ceiling);
    rectStroke(1,limitBox.leftWall,limitBox.ceiling,limitBox.rightWall,limitBox.floor);
    p.fill(127,255,191,ta);
    // p.rect(in.x()+in.type.dx,in.y()+in.type.dy,in.type.w,in.type.h);
    boxStroke(1,in.x()+in.type.dx,in.y()+in.type.dy,in.type.w,in.type.h);
    p.fill(94,203,234,ta);
    // p.rect(bx1*bw,by1*bh,bw,bh);
    // p.rect(bx1*bw,by2*bh,bw,bh);
    // p.rect(bx2*bw,by2*bh,bw,bh);
    // p.rect(bx2*bw,by1*bh,bw,bh);
    //---
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
    p.backgroundColor(world.backgroundColor);
    Vec2f tpos=world.yourself.point.pos;
    p.cam.point.pos.set(tpos.x,tpos.y,0);
    p.cam.point.des.set(tpos.x,tpos.y,0);
    // p.cam2d.activeDrag=false;
    // p.cam.noGrab();
    // tvgRefresh();
    for(Button<?> e:menuButtons) p.centerScreen.add.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.add.add(e);
    if(firstInit) {
      firstInit=false;
      world.init();
    }
    world.from(in);//TODO
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
    world.to(in);//TODO
    if(debugGraphics) p.centerCam.remove.add(displayCamTop);
  }
  @Override
  public void displayCam() {
    worldCenter.displayCam();
  }
  @Override
  public void display() {
    if(debug) {
      p.beginBlend();
      p.fill(94,203,234,127);
      for(RectF e:world.yourself.ctrl.cullRects) p.rect(e.x(),e.y(),e.w(),e.h());
      p.endBlend();
      Block tb=world.getBlock(p.mouse.x,p.mouse.y);
      p.textScale(p.pus/2f);
      initDebugText();
      debugText("Lighting  block="+(tb!=null?tb.lighting:"null")+" player="+Tools.cutToLastDigit(world.yourself.lighting.pos));
      debugText("Player    pos="+p.getFloatString(world.yourself.point.pos.x,8)+" "+p.getFloatString(world.yourself.point.pos.y,7)+" vel="+p.getFloatString(world.yourself.point.vel.x,5)+" "+p.getFloatString(world.yourself.point.vel.y,5));
      debugText("---- asynchronous ----");
      debugText("Regions         Update= "+p.getMillisString(world.regions.updateLoop.millis)+"ms");
      debugText("Regions Display Update= "+p.getMillisString(world.regions.updateDisplayLoop.millis)+"ms");
      long tm=world.regions.fullMapUpdateDisplayLoop.millis;
      debugText("FullMap Display Update= "+p.getMillisString(tm,5)+"ms "+p.getFloatString(tm/1000f)+"s");
      p.textScale(p.pus);
    }
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
    world.dispose();
  }
}