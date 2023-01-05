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
  public boolean debug;
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
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
    p.fill(255,127,191,191);
    p.rect(limitBox.leftWall,limitBox.ceiling,limitBox.rightWall-limitBox.leftWall,limitBox.floor-limitBox.ceiling);
    p.fill(127,255,191,191);
    p.rect(in.x()+in.type.dx,in.y()+in.type.dy,in.type.w,in.type.h);
    p.fill(94,203,234,191);
    p.rect((bx1)*world.blockWidth,by1*world.blockHeight,bw,bh);
    p.rect((bx1)*world.blockWidth,by2*world.blockHeight,bw,bh);
    p.rect((bx2)*world.blockWidth,by2*world.blockHeight,bw,bh);
    p.rect((bx2)*world.blockWidth,by1*world.blockHeight,bw,bh);
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
    worldCenter.resume();
    if(debug) p.centerCam.add.add(displayCamTop);
    p.centerCam.add.add(worldCenter);
  }
  @Override
  public void to(State0001 in) {
    // p.cam2d.activeDrag=true;
    for(Button<?> e:menuButtons) p.centerScreen.remove.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.remove.add(e);
    p.centerCam.remove.add(worldCenter);
    worldCenter.pause();
    if(debug) p.centerCam.remove.add(displayCamTop);
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
      float th=p.pu/2f;
      float tx=th;
      float ty=p.bu*1.5f+th*6;
      p.text("Block   Lighting= "+(tb!=null?tb.lighting:"null"),tx,ty);
      p.text("Player  Lighting= "+Tools.cutToLastDigit(world.yourself.lighting.pos),tx,ty+th*1);
      p.text("Player  Position= "+p.getFloatString(world.yourself.point.pos.x)+" "+p.getFloatString(world.yourself.point.pos.y),tx,ty+th*2);
      p.text("Player  Velocity= "+p.getFloatString(world.yourself.point.vel.x)+" "+p.getFloatString(world.yourself.point.vel.y),tx,ty+th*3);
      p.text("Regions         Update= "+p.getMillisString(world.regions.updateMilis)+"ms",tx,ty+th*4);
      p.text("Regions Display Update= "+p.getFloatString(world.regions.updateDisplayMilis/1000f)+"s",tx,ty+th*5);
      p.textScale(p.pus);
    }
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