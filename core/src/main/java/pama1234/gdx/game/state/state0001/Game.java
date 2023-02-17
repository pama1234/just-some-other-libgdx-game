package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.state.state0001.game.GameDisplayUtil.debugText;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.net.SocketWrapperGDX;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.GameDisplayUtil;
import pama1234.gdx.game.state.state0001.game.net.ClientCore;
import pama1234.gdx.game.state.state0001.game.net.NetMode;
import pama1234.gdx.game.state.state0001.game.net.ServerCore;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.world.World;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.state.state0001.game.world.WorldCenter;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.net.NetAddressInfo;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketData;

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
  public NetMode netMode=NetMode.singlePlayer;
  //---
  public NetAddressInfo serverAddr;
  public NetAddressInfo selfAddr;
  public ServerCore server;
  public ClientCore client;
  public Game(Screen0011 p) {
    super(p);
    menuButtons=UiGenerator.genButtons_0005(p);
    if(p.isAndroid) ctrlButtons=UiGenerator.genButtons_0007(p,this);
    worldCenter=new WorldCenter<Screen0011,Game,World<Screen0011,Game>>(p);
    worldCenter.list.add(world_0001=new World0001(p,this));
    worldCenter.pointer=0;
    if(debug) createDebugDisplay();
    selfAddr=new NetAddressInfo("127.0.0.1",12347);
  }
  public World0001 world() {
    return world_0001;
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=GameDisplayUtil.createDebugDisplay(p,this);
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
    if(netMode==NetMode.client) {
      SocketHints socketHints=new SocketHints();
      socketHints.connectTimeout=10000;
      socketHints.socketTimeout=5000;
      socketHints.keepAlive=true;
      socketHints.performancePrefConnectionTime=0;
      socketHints.performancePrefLatency=2;
      socketHints.performancePrefBandwidth=1;
      SocketData socketData=new SocketData(new SocketWrapperGDX(Gdx.net.newClientSocket(Protocol.TCP,serverAddr.addr,serverAddr.port,socketHints)));
      client=new ClientCore(this,world(),socketData);
      client.start();
    }else if(netMode==NetMode.integratedServer) {
      server=new ServerCore(this,tw,new ServerSocketData(selfAddr));
      server.start();
    }
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
    if(debug) debugText(p,this);
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