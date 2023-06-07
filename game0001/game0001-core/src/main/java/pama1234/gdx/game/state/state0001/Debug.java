package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.GameDisplayUtil;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.player.PlayerControllerFull;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.math.UtilMath;

public class Debug extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public World0001 world;
  public MainPlayer player;
  public PlayerControllerFull controller;
  public boolean firstInit;
  public Debug(Screen0011 p,int id) {
    super(p,id);
  }
  public void innerInit(Screen0011 p) {
    buttons=UiGenerator.genButtons_0004(p);
    world=new World0001(p,null,null);
    world.data.dir="data/saved/debug-world";
    world.settings.g=0;
    // player=new MainPlayer(p,world,0,0);
    player=world.yourself;
    player.inventory=new Inventory(player,3,1);
    // controller=new PlayerController(p,player);
    controller=player.ctrl;
  }
  @Override
  public void from(StateEntity0001 in) {
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    if(firstInit) innerInit(p);
  }
  @Override
  public void to(StateEntity0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==Keys.X) p.state(p.stateCenter.settings);
    else if(keyCode==Keys.Z) {
      player.update();
      p.cam2d.point.des.set(player.blockX1()*world.blockWidth(),player.blockY1()*world.blockHeight(),0);
      // p.cam2d.point.des.set((player.blockX1()+player.blockX2()+1)/2f*world.blockWidth(),(player.blockY1()+player.blockY2())/2f*world.blockHeight(),0);
      p.cam2d.point.pos.set(p.cam2d.point.des);
    }
  }
  @Override
  public void update() {
    if(p.keyPressedArray.contains(Keys.LEFT)) player.point.vel.x-=controller.speed;
    if(p.keyPressedArray.contains(Keys.RIGHT)) player.point.vel.x+=controller.speed;
    if(p.keyPressedArray.contains(Keys.UP)) player.point.vel.y-=controller.speed;
    if(p.keyPressedArray.contains(Keys.DOWN)) player.point.vel.y+=controller.speed;
    if(p.keyPressedArray.contains(Keys.C)) player.update();
    p.cam2d.point.des.set(player.blockX1()*world.blockWidth(),player.blockY1()*world.blockHeight(),0);
    // p.cam2d.point.des.set((player.blockX1()+player.blockX2()+1)/2f*world.blockWidth(),(player.blockY1()+player.blockY2())/2f*world.blockHeight(),0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    // p.println(player.blockX1());
    // p.println(controller.limitBox.p.blockX1());
    // System.out.println(world.xToBlockCord(player.x1()));
    // p.println(controller.limitBox.x1);
  }
  @Override
  public void display() {}
  @Override
  public void displayCam() {
    p.beginBlend();
    GameDisplayUtil.drawLimitBox(p,world.settings.blockWidth,world.settings.blockHeight,controller.limitBox);
    float tx,ty;
    p.doStroke();
    p.stroke(255,127);
    tx=player.x1();
    ty=player.y1();
    float dx=player.point.dx();
    float dy=player.point.dy();
    float mag=UtilMath.mag(dx,dy);
    float mx=dx*18/mag;
    float my=dy*18/mag;
    p.line(tx,ty,tx+dx,ty+dy);
    p.line(tx,ty,tx+mx,ty+my);
    tx=player.x1();
    ty=player.y2();
    p.line(tx,ty,tx+dx,ty+dy);
    p.line(tx,ty,tx+mx,ty+my);
    tx=player.x2();
    ty=player.y1();
    p.line(tx,ty,tx+dx,ty+dy);
    p.line(tx,ty,tx+mx,ty+my);
    tx=player.x2();
    ty=player.y2();
    p.line(tx,ty,tx+dx,ty+dy);
    p.line(tx,ty,tx+mx,ty+my);
    p.noStroke();
    // System.out.println(controller.limitBox.x1);
    p.endBlend();
  }
}